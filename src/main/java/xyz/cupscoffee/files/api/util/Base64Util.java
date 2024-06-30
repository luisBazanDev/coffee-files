package xyz.cupscoffee.files.api.util;

import java.util.Base64;

/**
 * Utility class for Base64 encoding and decoding.
 */
public class Base64Util {
    /**
     * Decodes the given data using Base64 decoding.
     * 
     * @param data the data to encode
     * @return the encoded data
     */
    public static String decode(String encodedData) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedData);

        return new String(decodedBytes);
    }

    /**
     * Encodes the given data using Base64 encoding.
     * 
     * @param data the data to encode
     * @return the encoded data
     */
    public static String encode(String data) {
        byte[] encodedBytes = Base64.getEncoder().encode(data.getBytes());

        return new String(encodedBytes);
    }
}
