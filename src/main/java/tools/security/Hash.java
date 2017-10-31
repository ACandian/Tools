package tools.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class to hash things.
 * 
 * @author A. CANDIAN
 *
 */
public final class Hash {

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    /**
     * Private constructor for a utility class.
     */
    private Hash() {

    }

    /**
     * Convert an array of bytes to an hexadecimal {@link String}.<br>
     * This method is quicker than the build in Java one.
     * 
     * @param bytes
     *            The byte array to convert.
     * @return A {@link String} representing the byte array.
     */
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * Hash some bytes and return the digested value.
     * 
     * @param bytesToDigest
     *            Bytes to hash.
     * @param digestAlgo
     *            The algorithm to use for digestion.
     * @return The digested bytes.
     * @throws NoSuchAlgorithmException
     *             If you try to use an unavailable digest algorithm.
     */
    public static final byte[] hash(byte[] bytesToDigest, String digestAlgo) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(digestAlgo);

        return md.digest(bytesToDigest);
    }

}
