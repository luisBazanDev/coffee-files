package xyz.cupscoffee.files.api.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Utility class to compress and decompress strings using GZIP.
 */
public class StringCompresor {
    /**
     * Compress a string using GZIP.
     * 
     * @param text The string to compress.
     * @return The compressed string.
     * @throws UnsupportedEncodingException If the encoding is not supported.
     */
    public static String compress(String text) throws UnsupportedEncodingException {
        if (text == null || text.length() == 0) {
            return text;
        }

        // Compress the string
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try (GZIPOutputStream gzip = new GZIPOutputStream(out)) {
            gzip.write(text.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out.toString("ISO-8859-1");
    }

    /**
     * Decompress a string using GZIP.
     * 
     * @param compressedText
     * @return The decompressed string.
     */
    public static String decompress(String compressedText) {
        try {
            byte[] compressedBytes = compressedText.getBytes("ISO-8859-1");

            ByteArrayInputStream in = new ByteArrayInputStream(compressedBytes);
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            try (GZIPInputStream gzip = new GZIPInputStream(in)) {
                byte[] buffer = new byte[1024];
                int bytesRead;

                // Decompress the string
                while ((bytesRead = gzip.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }

            return out.toString();
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }
}
