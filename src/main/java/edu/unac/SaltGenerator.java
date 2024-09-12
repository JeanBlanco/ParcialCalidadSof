package edu.unac;

import edu.unac.exception.ConsecutiveCharacterException;
import edu.unac.exception.DuplicateSaltException;
import edu.unac.exception.InvalidLengthException;
import edu.unac.exception.RepeatedCharacterException;

import java.util.HashSet;
import java.util.Set;

public class SaltGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; // 62 characters
    private SimpleRandomProvider random;
    private Set<String> generatedSalts;

    public SaltGenerator(SimpleRandomProvider random) {
        this.random = random;
        this.generatedSalts = new HashSet<>();
    }

    public String generateSalt(int length) throws RepeatedCharacterException, ConsecutiveCharacterException, InvalidLengthException, DuplicateSaltException {
        if (length <= 2) {
            throw new InvalidLengthException("The length must be greater than 2.");
        }

        String salt = generateRandomSalt(length);

        validateSalt(salt);

        if (!generatedSalts.add(salt)) {
            throw new DuplicateSaltException("The salt has already been generated previously: " + salt);
        }

        return salt;
    }

    private String generateRandomSalt(int length) {
        StringBuilder salt = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            salt.append(CHARACTERS.charAt(index));
        }
        return salt.toString();
    }

    private void validateSalt(String salt) throws RepeatedCharacterException, ConsecutiveCharacterException {
        for (int i = 0; i < salt.length() - 2; i++) {
            char current = salt.charAt(i);
            if (salt.charAt(i + 1) == current && salt.charAt(i + 2) == current) {
                throw new RepeatedCharacterException("The salt contains 3 consecutively repeated characters: " + current);
            }
            if (salt.charAt(i + 1) == current + 1 && // Checks if the character at position i + 1 of salt is equal to the next character in the Unicode sequence of the current character
                    salt.charAt(i + 2) == current + 2) {
                throw new ConsecutiveCharacterException("The salt contains 3 consecutive characters: " + current + ", " + (char)(current + 1) + ", " + (char)(current + 2));
            }
        }
    }
}
