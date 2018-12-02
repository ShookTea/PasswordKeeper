package eu.shooktea.passkeeper.format;

import javax.crypto.KeyGenerator;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;

public abstract class AbstractFormat implements Format {

    public abstract void loadFromInputStream(DataInputStream dis);
    public abstract void storeToOutputStream(DataOutputStream dos);

    private static SecureRandom random = new SecureRandom();
    private static KeyGenerator generator = null;
    private static MessageDigest digest = null;

    private static int SALT_LENGTH = 64;

    private static byte[] mergeArrays(byte[]... a) {
        int length = 0;
        for (byte[] c : a) length += c.length;
        byte[] ret = new byte[length];
        int retIndex = 0;
        for (byte[] array : a) {
            for (int i = 0; i < array.length; i++, retIndex++) {
                ret[retIndex] = array[i];
            }
        }
        return ret;
    }

    public static String getUserPassword() {
        return "Temporary password";
    }

    public static Key generateKey() {
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

    public static byte[] hash(byte[] input) {
        try {
            if (digest == null) {
                digest = MessageDigest.getInstance("SHA-256");
            }
            byte[] salt = new byte[SALT_LENGTH];
            random.nextBytes(salt);
            byte[] sum = mergeArrays(salt, input);
            byte[] hash = digest.digest(sum);
            return mergeArrays(salt, hash);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean validateHash(byte[] hashed, byte[] input) {
        try {
            if (digest == null) {
                digest = MessageDigest.getInstance("SHA-256");
            }
            byte[] salt = Arrays.copyOfRange(hashed, 0, SALT_LENGTH);
            byte[] sum = mergeArrays(salt, input);
            byte[] hash = digest.digest(sum);
            return Arrays.equals(hash, hashed);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
