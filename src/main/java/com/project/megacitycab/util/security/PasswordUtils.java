package com.project.megacitycab.util.security;

import com.project.megacitycab.util.exception.MegaCityCabException;
import com.project.megacitycab.util.exception.MegaCityCabExceptionType;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PasswordUtils {
    private static final Logger logger = Logger.getLogger(PasswordUtils.class.getName());

    /**
     * Generates a simple random salt string
     * @return random salt string
     */
    public static String generateSalt() {
        return "randomSalt" + System.currentTimeMillis() % 1000;
    }

    /**
     * Hash password with salt using SHA-256 to match MySQL's SHA2 function
     * @param plainPassword the password to hash
     * @param salt the salt to use
     * @return hashed password
     */
    public static String hashPassword(String plainPassword, String salt) {
        try {
            // Simple concatenation to match MySQL's CONCAT function
            String saltedPassword = plainPassword + salt;

            // Create SHA-256 hash (equivalent to MySQL's SHA2 with 256)
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(saltedPassword.getBytes(StandardCharsets.UTF_8));

            // Convert to lowercase hex string
            return bytesToHex(hashBytes);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while hashing password", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.PASSWORD_UTIL_EXCEPTION);
        }
    }

    /**
     * Verify if the entered password matches the stored hash
     * @param plainPassword the password to verify
     * @param storedHash the hash stored in database
     * @param storedSalt the salt stored in database
     * @return true if password matches, false otherwise
     */
    public static boolean verifyPassword(String plainPassword, String storedHash, String storedSalt) {
        try {
            // Hash the input password with the stored salt
            String hashedPassword = hashPassword(plainPassword, storedSalt);

            // Compare hashed password with stored hash
            boolean isVerified = hashedPassword.equalsIgnoreCase(storedHash);
            logger.log(Level.INFO, "Password verification result: {0}", isVerified);
            return isVerified;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while verifying password", e);
            throw new MegaCityCabException(MegaCityCabExceptionType.PASSWORD_UTIL_EXCEPTION);
        }
    }

    /**
     * Convert bytes to hex string
     * @param bytes byte array to convert
     * @return hex string representation
     */
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
}
