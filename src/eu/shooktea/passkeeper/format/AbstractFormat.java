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
}
