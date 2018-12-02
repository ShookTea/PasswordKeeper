package eu.shooktea.passkeeper.format;

import eu.shooktea.passkeeper.PasswordGenerator;
import org.junit.Test;

import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static org.junit.Assert.*;

public class HasherTest {

    @Test
    public void generatingHash() throws NoSuchAlgorithmException {
        String password = PasswordGenerator.generate(15);
        byte[] bytes = password.getBytes(Charset.forName("UTF-8"));
        byte[] hash = Hasher.hash(bytes);
        assertFalse(Arrays.equals(bytes, hash));
        assertTrue(hash.length > 0);
    }

    @Test
    public void validPassword() throws NoSuchAlgorithmException {
        String password = PasswordGenerator.generate(15);
        byte[] array = password.getBytes(Charset.forName("UTF-8"));
        byte[] hash = Hasher.hash(array);
        assertFalse(Arrays.equals(hash, array));
        assertTrue(Hasher.validateHash(hash, array));
    }

    @Test
    public void invalidPassword() throws NoSuchAlgorithmException {
        String passwordA = PasswordGenerator.generate(15);
        String passwordB = PasswordGenerator.generate(14);
        assertNotEquals(passwordA, passwordB); // there is no possibility of passwordA == passwordB - different length

        byte[] arrayA = passwordA.getBytes(Charset.forName("UTF-8"));
        byte[] arrayB = passwordB.getBytes(Charset.forName("UTF-8"));
        byte[] hash = Hasher.hash(arrayA);

        assertFalse(Arrays.equals(hash, arrayA));
        assertTrue(Hasher.validateHash(hash, arrayA));
        assertFalse(Hasher.validateHash(hash, arrayB));
    }
}
