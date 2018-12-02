package eu.shooktea.passkeeper;

import static org.junit.Assert.*;
import org.junit.Test;

public class PasswordGeneratorTest {

    @Test
    public void generateRandomPassword() {
        int passwordLength = 15;
        String password = PasswordGenerator.generate(passwordLength);
        assertEquals(passwordLength, password.length());
    }
}
