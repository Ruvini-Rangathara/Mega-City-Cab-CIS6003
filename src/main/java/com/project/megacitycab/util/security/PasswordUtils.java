package com.project.megacitycab.util.security;

import java.security.SecureRandom;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.util.Base64;

public class PasswordUtils {

    private static final int SALT_LENGTH = 16; // Salt length (in bytes)
    private static final int ITERATIONS = 10000; // Number of PBKDF2 iterations
    private static final int KEY_LENGTH = 512; // Length of the generated hash (in bits)

    // Generate a random salt
    public static byte[] generateSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        secureRandom.nextBytes(salt);
        return salt;
    }

    // Hash the password with PBKDF2
    public static String hashPassword(String password, byte[] salt) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = skf.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Error while hashing password", e);
        }
    }

    // Verify if the entered password matches the stored hash
    public static boolean verifyPassword(String plainPassword, String storedHash, String storedSalt) {
        byte[] salt = Base64.getDecoder().decode(storedSalt);
        String hashedPassword = hashPassword(plainPassword, salt);
        return hashedPassword.equals(storedHash);
    }
}
