package eu.shooktea.passkeeper.format;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Hasher {
    private Hasher() {}

    public static byte[] hash(byte[] input) throws NoSuchAlgorithmException {
        return getDigest().digest(input);
    }

    public static boolean validateHash(byte[] hashed, byte[] input) throws NoSuchAlgorithmException {
        return Arrays.equals(getDigest().digest(input), hashed);
    }

    private static MessageDigest getDigest() throws NoSuchAlgorithmException {
        if (digest == null) {
            digest = MessageDigest.getInstance("SHA-256");
        }
        return digest;
    }

    private static MessageDigest digest = null;
}
