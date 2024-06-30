package xyz.cupscoffee.files.api;

import java.io.InputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import xyz.cupscoffee.files.api.driver.SavDriver;
import xyz.cupscoffee.files.api.exception.InvalidFormatFileException;

/**
 * Utility class for reading {@code .sav} files.
 */
public class SavFileReader {
    private static final int HEADER_BYTES = 16;

    public SavFileReader() {
    }

    /**
     * Reads a {@code .sav} file and returns a SavFile object. It uses the header of
     * the file to identify which driver to use. In this case the bytes of the
     * header are the first 16 bytes of the file. According to the header, it will
     * be established which driver will be used to read the file; the driver must
     * implement the {@code SavDriver} interface.
     * 
     * @see Disk
     * @see SavDriver
     * 
     * @param inputStream The InputStream of the {@code .sav} file.
     * @return A SavFile object.
     * @throws InvalidFormatFileException If the file format is invalid or does not
     *                                    have a valid header.
     */
    public static SavStructure readSavFile(InputStream inputStream) throws InvalidFormatFileException {
        String header;
        try {
            header = new String(inputStream.readNBytes(HEADER_BYTES), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new InvalidFormatFileException("The file format is not supported.");
        }

        header = header.trim();

        // Verify which driver to use
        SavDriver driver = getDriver(header);

        return driver.readSavFile(inputStream);
    }

    /**
     * Get the driver necessary for the file format.
     * 
     * @param header The header of the file.
     * @return A SavFile object.
     * @throws InvalidFormatFileException If the file format is invalid or does not
     *                                    have a valid header.
     */
    private static SavDriver getDriver(String header) throws InvalidFormatFileException {
        try {
            return (SavDriver) Class.forName("xyz.cupscoffee.files.api.driver.teams." + header + "Driver")
                    .getConstructor()
                    .newInstance();
        } catch (Exception e) {
            throw new InvalidFormatFileException("The file format is not supported.");
        }
    }
}
