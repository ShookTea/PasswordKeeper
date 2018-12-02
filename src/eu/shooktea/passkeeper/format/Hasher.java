package eu.shooktea.passkeeper.format;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class Hasher {
    private Hasher() {}

    public static byte[] hash(byte[] input) throws NoSuchAlgorithmException {
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        byte[] sum = mergeArrays(salt, input);
        byte[] hash = getDigest().digest(sum);
        return mergeArrays(salt, hash);
    }

    public static boolean validateHash(byte[] hashed, byte[] input) throws NoSuchAlgorithmException {
        byte[] salt = Arrays.copyOfRange(hashed, 0, SALT_LENGTH);
        byte[] sum = mergeArrays(salt, input);
        byte[] hash = mergeArrays(salt, getDigest().digest(sum));
        return Arrays.equals(hash, hashed);
    }

    private static MessageDigest getDigest() throws NoSuchAlgorithmException {
        if (digest == null) {
            digest = MessageDigest.getInstance("SHA-256");
        }
        return digest;
    }

    public static byte[] mergeArrays(byte[] a, byte[] b) {
        byte[] ret = new byte[a.length + b.length];
        int index = 0;
        for (int i = 0; i < a.length; i++, index++) {
            ret[index] = a[i];
        }
        for (int i = 0; i < b.length; i++, index++) {
            ret[index] = b[i];
        }
        return ret;
    }

    private static MessageDigest digest = null;
    private static int SALT_LENGTH = 64;
    private static SecureRandom random = new SecureRandom();
}
