package com.epam.hostel.service.util;

import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * Provides caching and comparing with a cached password.
 */
public class PasswordHelper {
    private static final Logger logger = Logger.getLogger(PasswordHelper.class);
    private static final PasswordHelper INSTANCE = new PasswordHelper();
    private static final String ENCRYPT_ALGORITHM = "SHA-512";
    private static final String SALT_ALGORITHM = "SHA1PRNG";
    private static final int SALT_SIZE = 16;

    private PasswordHelper() {
    }

    /**
     * Returns {@link PasswordHelper} instance
     *
     * @return PasswordHelper instance
     */
    public static PasswordHelper getInstance() {
        return INSTANCE;
    }

    /**
     * Encrypts a given password.
     * After encrypting given password will be filled by zero bytes.
     *
     * @param password a byte array for encrypting
     * @return a byte array of encrypted password
     */
    public byte[] encryptPassword(byte[] password) {
        return encryptPassword(password, getSalt());
    }

    /**
     * Encrypts a given password with salt.
     * After encrypting given password will be filled by zero bytes.
     *
     * @param password byte array for encrypting
     * @param salt     a salt byte array for making cached password stronger
     * @return a byte array of encrypted password
     */
    private byte[] encryptPassword(byte[] password, byte[] salt) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ENCRYPT_ALGORITHM);
            messageDigest.update(salt);
            byte[] bytes = messageDigest.digest(password);
            byte[] generatedPassword = new byte[bytes.length + salt.length];
            System.arraycopy(salt, 0, generatedPassword, 0, salt.length);
            for (int i = 0; i < bytes.length; i++) {
                generatedPassword[salt.length + i] = (byte) ((bytes[i] & 0xFF) + 0x100);
            }
            PasswordHelper.dispose(bytes, password, salt);
            return generatedPassword;

        } catch (NoSuchAlgorithmException e) {
            logger.error(e);
        }

        return null;
    }

    /**
     * Generates a salt for making password stronger.
     *
     * @return massive of random bytes
     */
    private byte[] getSalt() {
        try {
            SecureRandom secureRandom = SecureRandom.getInstance(SALT_ALGORITHM);
            byte[] salt = new byte[SALT_SIZE];
            secureRandom.nextBytes(salt);
            return salt;
        } catch (NoSuchAlgorithmException e) {
            logger.error(e);
        }

        return null;
    }

    /**
     * Compares real password byte array with given cached password.
     * After encrypting given password and hashed password will be filled by zero bytes.
     *
     * @param password       not cashed password
     * @param hashedPassword cached password
     * @return {@code true} if this passwords compare and {@code false} otherwise
     */
    public boolean match(byte[] password, byte[] hashedPassword) {
        byte[] salt = new byte[SALT_SIZE];
        System.arraycopy(hashedPassword, 0, salt, 0, SALT_SIZE);

        byte[] generatedPassword = encryptPassword(password, salt);
        boolean match = Arrays.equals(generatedPassword, hashedPassword);
        PasswordHelper.dispose(generatedPassword, hashedPassword);

        return match;
    }

    /**
     * Disposes of the data arrays
     *
     * @param arrays arrays for disposing
     */
    public static void dispose(byte[]... arrays) {
        for (byte[] password : arrays) {
            if (password != null) {
                Arrays.fill(password, (byte) 0);
            }
        }
    }

}
