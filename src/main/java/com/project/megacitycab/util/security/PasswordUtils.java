package com.project.megacitycab.util.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;

public class PasswordUtils {
    private static final int SALT_LENGTH = 16;

    // Generate a random salt
    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    // Convert bytes to hex string
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    // Hash password with salt using SHA-256 (byte[] version)
    public static String hashPassword(String plainPassword, byte[] salt) {
        return hashPassword(plainPassword, bytesToHex(salt));
    }

    // Hash password with salt using SHA-256 (String version)
    public static String hashPassword(String plainPassword, String salt) {
        try {
            // Concatenate the password and salt exactly as MySQL does
            String saltedPassword = plainPassword + salt;

            // Create SHA-256 hash
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(saltedPassword.getBytes(StandardCharsets.UTF_8));

            // Convert to lowercase hex string to match MySQL's SHA2 output
            return bytesToHex(hashBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error while hashing password", e);
        }
    }

    // Verify if the entered password matches the stored hash
    public static boolean verifyPassword(String plainPassword, String storedHash, String storedSalt) {
        try {
            // Hash the input password with the stored salt
            String hashedPassword = hashPassword(plainPassword, storedSalt);

            // Compare the hashed password with the stored hash (case-insensitive)
            return hashedPassword.equalsIgnoreCase(storedHash);
        } catch (Exception e) {
            throw new RuntimeException("Error while verifying password", e);
        }
    }
}
