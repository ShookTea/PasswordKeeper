package eu.shooktea.passkeeper.format;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.nio.charset.Charset;
import java.security.*;

public abstract class AbstractFormat implements Format {

    abstract void loadFromInputStream(DataInputStream dis);
    abstract void storeToOutputStream(DataOutputStream dos);

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
    public void loadFromData(byte[] data) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            DataInputStream dis = new DataInputStream(bais);

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

    private Key decodeKey(byte[] keyBytes, byte[] passwordHash) throws Exception {
        byte[] keyToUse = new byte[32];
        for (int i = 0; i < keyToUse.length && i < passwordHash.length; i++) {
            keyToUse[i] = passwordHash[i];
        }
        Key passwordKey = new SecretKeySpec(keyToUse, 0, 32, "AES");
        Cipher cipher = getCipher();
        cipher.init(Cipher.DECRYPT_MODE, passwordKey);
        byte[] decodedKey = cipher.doFinal(keyBytes);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    private void decodeData(Key key, byte[] codedData) throws InvalidKeyException {
        Cipher cipher = getCipher();
        cipher.init(Cipher.DECRYPT_MODE, key);
        ByteArrayInputStream bais = new ByteArrayInputStream(codedData);
        CipherInputStream cis = new CipherInputStream(bais, cipher);
        DataInputStream dis = new DataInputStream(cis);
        this.loadFromInputStream(dis);
    }

    @Override
    public byte[] storeData() {
        try {
            Key key = generateKey();
            String password = getUserPassword();
            byte[] passwordHash = Hasher.hash(password.getBytes(Charset.forName("UTF-8")));

            byte[] passwordBytes = Hasher.hash(passwordHash);
            byte[] keyBytes = encodeKey(key, passwordHash);
            byte[] dataBytes = encodeData(key);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);

            dos.writeInt(passwordBytes.length);
            dos.write(passwordBytes);
            dos.writeInt(keyBytes.length);
            dos.write(keyBytes);
            dos.writeInt(dataBytes.length);
            dos.write(dataBytes);

            dos.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] encodeData(Key key) throws InvalidKeyException {
        Cipher cipher = getCipher();
        cipher.init(Cipher.ENCRYPT_MODE, key);
        ByteArrayOutputStream dataFromFormat = new ByteArrayOutputStream();
        CipherOutputStream cos = new CipherOutputStream(dataFromFormat, cipher);
        DataOutputStream dos = new DataOutputStream(cos);
        this.storeToOutputStream(dos);
        return dataFromFormat.toByteArray();
    }

    private byte[] encodeKey(Key key, byte[] passwordHash) throws Exception {
        byte[] keyToUse = new byte[32];
        for (int i = 0; i < keyToUse.length && i < passwordHash.length; i++) {
            keyToUse[i] = passwordHash[i];
        }
        Key newKey = new SecretKeySpec(keyToUse, 0, 32, "AES");
        Cipher cipher = getCipher();
        cipher.init(Cipher.ENCRYPT_MODE, newKey);

        byte[] encodedKey = key.getEncoded();
        return cipher.doFinal(encodedKey);
    }
}
