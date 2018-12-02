package eu.shooktea.passkeeper;

import java.security.SecureRandom;

public class PasswordGenerator {
    private PasswordGenerator() {}

    public static String generate(int passwordLength, char[] allowedCharacters) {
        SecureRandom random = new SecureRandom();
        StringBuilder builder = new StringBuilder();
        int limit = allowedCharacters.length;
        for (int i = 0; i < passwordLength; i++) {
            int pos = random.nextInt(limit);
            builder.append(allowedCharacters[pos]);
        }
        return builder.toString();
    }

    public static String generate(int passwordLength) {
        return generate(passwordLength, allCharacters);
    }

    private static char[] fromRange(char start, char end) {
        int a = (int)start;
        int b = (int)end;
        char[] elements = new char[b - a + 1];
        for (int i = a, index = 0; i <= b && index < elements.length; i++, index++) {
            elements[index] = (char)i;
        }
        return elements;
    };

    private static char[] mergeArrays(char[]... a) {
        int length = 0;
        for (char[] c : a) length += c.length;
        char[] ret = new char[length];
        int retIndex = 0;
        for (char[] array : a) {
            for (int i = 0; i < array.length; i++, retIndex++) {
                ret[retIndex] = array[i];
            }
        }
        return ret;
    }

    private static char[] smallLetters = fromRange('a', 'z');
    private static char[] largeLetters = fromRange('A', 'Z');
    private static char[] digits = fromRange('0', '9');
    private static char[] specialCharacters = mergeArrays(fromRange('!', '/'), fromRange(':', '@'));
    private static char[] allCharacters = mergeArrays(smallLetters, largeLetters, digits, specialCharacters);
}
