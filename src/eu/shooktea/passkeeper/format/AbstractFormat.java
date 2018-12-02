package eu.shooktea.passkeeper.format;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.Charset;
import java.security.*;
import java.util.Arrays;

/**
 * The First Abstract Format is a base for many formats.
 *
 * Format is built on three blocks: password block, key block and data block. All of them have exact same structure:
 * SIZE (INT) - size of DATA in bytes
 * DATA (BYTE[]) - bytes of data in block
 *
 * First block, the Password block, keeps user password, double hashed in SHA-256.
 *
 * Second block, the Key block, keeps encoded AES key. That key is randomly generated during every saving. It is encoded
 * with another AES key, made of SHA-256-hashed user password.
 *
 * Third block, the Data block, is a data that is encoded by AES key hidden in second block. Format of this data is not
 * defined in First Abstract Format, but in all implementations of that format.
 *
 * If function {@code AES(DATA <- KEY)} represents a result of encoding DATA by AES algorithm with KEY key and function
 * {@code SHA(DATA)} represents a result of hashing DATA with SHA-256 algorithm, then structure of First Abstract Format
 * file is:
 *
 * * SHA(SHA(PASSWORD))
 * * AES(KEY <- SHA(PASSWORD))
 * * AES(DATA <- KEY)
 *
 * Where:
 * * DATA is data to be hidden in file,
 * * KEY is a randomly generated key,
 * * PASSWORD is a password choosen by user.
 */
public abstract class AbstractFormat implements Format {

    abstract void loadFromInputStream(InputStream is) throws IOException;
    abstract void storeToOutputStream(OutputStream os) throws IOException;

    private static SecureRandom random = new SecureRandom();
    private static KeyGenerator generator = null;
    private static Cipher cipher = null;

    private static Cipher getCipher() {
        if (cipher == null) {
            try {
                cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return cipher;
    }

    private static String getUserPassword() {
        return "Temporary password";
    }

    private static Key generateKey() {
        try {
            if (generator == null) {
                int keyBitSize = 256;
                generator = KeyGenerator.getInstance("AES");
                generator.init(keyBitSize, random);
            }
            return generator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void loadFromStream(InputStream is) {
        try {
            DataInputStream dis = new DataInputStream(is);
            String password = getUserPassword();
            byte[] passwordHashed = Hasher.hash(password.getBytes(Charset.forName("UTF-8")));

            byte[] passwordBytes = new byte[dis.readInt()];
            dis.read(passwordBytes);
            if (!Hasher.validateHash(passwordBytes, passwordHashed)) {
                throw new Exception("Incorrect password");
            }

            byte[] keyBytes = new byte[dis.readInt()];
            dis.read(keyBytes);
            Key key = decodeKey(keyBytes, passwordHashed);

            byte[] dataBytes = new byte[dis.readInt()];
            dis.read(dataBytes);
            dis.close();

            decodeData(key, dataBytes);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Key decodeKey(byte[] dataBytes, byte[] passwordHash) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(dataBytes);
        byte[] iv = new byte[16];
        bais.read(iv);
        byte[] keyBytes = new byte[bais.read()];
        bais.read(keyBytes);

        byte[] keyToUse = new byte[32];
        for (int i = 0; i < keyToUse.length && i < passwordHash.length; i++) {
            keyToUse[i] = passwordHash[i];
        }
        Key passwordKey = new SecretKeySpec(keyToUse, 0, 32, "AES");
        Cipher cipher = getCipher();
        cipher.init(Cipher.DECRYPT_MODE, passwordKey, new IvParameterSpec(iv));
        byte[] decodedKey = cipher.doFinal(keyBytes);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    private void decodeData(Key key, byte[] codedData) throws Exception {
        byte[] iv = Arrays.copyOfRange(codedData, 0, 16);
        byte[] data = Arrays.copyOfRange(codedData, 16, codedData.length);
        Cipher cipher = getCipher();
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        ByteArrayInputStream bais = new ByteArrayInputStream(cipher.doFinal(data));
        this.loadFromInputStream(bais);
        bais.close();
    }

    @Override
    public void writeToStream(OutputStream os) {
        try {
            Key key = generateKey();
            String password = getUserPassword();
            byte[] passwordHash = Hasher.hash(password.getBytes(Charset.forName("UTF-8")));

            byte[] passwordBytes = Hasher.hash(passwordHash);
            byte[] keyBytes = encodeKey(key, passwordHash);
            byte[] dataBytes = encodeData(key);

            DataOutputStream dos = new DataOutputStream(os);

            dos.writeInt(passwordBytes.length);
            dos.write(passwordBytes);
            dos.writeInt(keyBytes.length);
            dos.write(keyBytes);
            dos.writeInt(dataBytes.length);
            dos.write(dataBytes);

            dos.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] encodeData(Key key) throws Exception {
        Cipher cipher = getCipher();
        byte[] iv = new byte[16];
        random.nextBytes(iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
        ByteArrayOutputStream dataFromFormat = new ByteArrayOutputStream();
        dataFromFormat.write(iv);
        this.storeToOutputStream(dataFromFormat);
        dataFromFormat.close();
        return cipher.doFinal(dataFromFormat.toByteArray());
    }

    private byte[] encodeKey(Key key, byte[] passwordHash) throws Exception {
        byte[] keyToUse = new byte[32];
        byte[] iv = new byte[16];
        random.nextBytes(iv);
        for (int i = 0; i < keyToUse.length && i < passwordHash.length; i++) {
            keyToUse[i] = passwordHash[i];
        }
        Key newKey = new SecretKeySpec(keyToUse, 0, 32, "AES");
        Cipher cipher = getCipher();
        cipher.init(Cipher.ENCRYPT_MODE, newKey, new IvParameterSpec(iv));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        byte[] encodedKey = cipher.doFinal(key.getEncoded());
        baos.write(iv);
        baos.write(encodedKey.length);
        baos.write(encodedKey);

        return baos.toByteArray();
    }
}
