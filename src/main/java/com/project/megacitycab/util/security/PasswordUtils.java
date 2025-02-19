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
    public static String bytesToHex(byte[] bytes) {
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

    // Convert HEX string back to byte array
    private static byte[] hexToBytes(String hexString) {
        int len = hexString.length();
        byte[] bytes = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return bytes;
    }

    // Hash password with salt using SHA-256 (byte[] version)
    public static String hashPassword(String plainPassword, byte[] salt) {
        return hashPassword(plainPassword, bytesToHex(salt));  // Store salt in HEX format
    }

    // Hash password with salt using SHA-256 (String version)
    public static String hashPassword(String plainPassword, String salt) {
        try {
            // Concatenate the password and salt
            String saltedPassword = plainPassword + salt;

            // Create SHA-256 hash
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(saltedPassword.getBytes(StandardCharsets.UTF_8));

            // Convert to lowercase hex string
            return bytesToHex(hashBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error while hashing password", e);
        }
    }

    // Verify if the entered password matches the stored hash
    public static boolean verifyPassword(String plainPassword, String storedHash, String storedSalt) {
        try {
            // Convert HEX salt back to bytes
            byte[] saltBytes = hexToBytes(storedSalt);

            // Hash the input password with the stored salt
            String hashedPassword = hashPassword(plainPassword, saltBytes);

            // Compare hashed password with stored hash
            return hashedPassword.equalsIgnoreCase(storedHash);
        } catch (Exception e) {
            throw new RuntimeException("Error while verifying password", e);
        }
    }
}
