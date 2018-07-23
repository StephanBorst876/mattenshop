package nl.workshop1.utility;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * A utility class to hash passwords and check passwords vs hashed values. It
 * uses a combination of hashing and unique salt. The algorithm used is
 * PBKDF2WithHmacSHA1 which, although not the best for hashing password (vs.
 * bcrypt) is still considered robust and
 * <a href="https://security.stackexchange.com/a/6415/12614"> recommended by
 * NIST </a>. The hashed value has 256 bits.
 */
public class Password {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int ITERATIONS = 1000;
    private static final int KEY_LENGTH = 256;

    /**
     * Returns a random salt to be used to hash a password.
     *
     * @return a 16 bytes random salt
     */
    public static byte[] getNextSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return salt;
    }

    /**
     * Returns a salted and hashed password using the provided hash.<br>
     * Note - side effect: the password is destroyed (the char[] is filled with
     * zeros)
     *
     * @param password the password to be hashed
     * @param salt a 16 bytes salt, ideally obtained with the getNextSalt method
     *
     * @return the hashed password with a pinch of salt
     */
    public static byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }

    }

    /**
     * Returns true if the given password and salt match the hashed value, false
     * otherwise.<br>
     *
     * @param password the password to check
     * @param salt the salt used to hash the password
     * @param expectedHash the expected hashed value of the password
     *
     * @return true if the given password and salt match the hashed value, false
     * otherwise
     */
    public static boolean isExpectedPassword(String password, String expectedPwd) {

        // Compose the current hashed and salted password
        byte[] salt = Arrays.copyOfRange(bytesFromHex(expectedPwd), 0, 16);
        byte[] currentHash = hash(password.toCharArray(), salt);
        String currentPwd = hexFromBytes(add2ByteArrays(salt, currentHash));
        
        // Compare current and expected for equality
        boolean retValue = true;
        if (currentPwd.length() != expectedPwd.length()) {
            retValue = false;
        }
        for (int i = 0; i < currentPwd.length() && i < expectedPwd.length(); i++) {
            if (currentPwd.charAt(i) != expectedPwd.charAt(i)) {
                retValue = false;
            }
        }
        return retValue;

    }

    private static byte[] add2ByteArrays(byte[] one, byte[] two) {
        byte[] combined = new byte[one.length + two.length];
        for (int i = 0; i < combined.length; ++i) {
            combined[i] = i < one.length ? one[i] : two[i - one.length];
        }
        return combined;
    }

    /**
     * Make een hashed/salted wachtwoord van een user wachtwoord
     *
     * @param wachtwoord
     * @return hashed and salted wachtwoord
     */
    public static String hashedAndSalted(String wachtwoord) {
        byte[] salt = getNextSalt();
        byte[] pwdHash = hash(wachtwoord.toCharArray(), salt);
        return hexFromBytes(add2ByteArrays(salt, pwdHash));
    }

    private static String hexFromBytes(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    private static byte[] bytesFromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
}
