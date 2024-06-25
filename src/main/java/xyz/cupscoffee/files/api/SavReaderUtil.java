package xyz.cupscoffee.files.api;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import xyz.cupscoffee.files.api.driver.SavDriver;
import xyz.cupscoffee.files.api.exception.InvalidFormatFileException;

/**
 * Utility class for reading .sav files.
 */
public class SavReaderUtil {
    private final int HEADER_BYTES = 16;

    public SavReaderUtil() {
    }

    /**
     * Reads a .sav file and returns a SavFile object. It uses the header of the
     * file to identify which driver to use. In this case the bytes of the header
     * are the first 16 bytes of the file. According to the header, it will be
     * established which driver will be used to read the file; the driver must
     * implement the {@code SavDriver} interface.
     * 
     * @see Disk
     * @see SavDriver
     * 
     * @param fileInputStream The FileInputStream of the .sav file.
     * @return A SavFile object.
     * @throws InvalidFormatFileException If the file format is invalid or does not
     *                                    have a header.
     */
    public SavFile readSavFile(FileInputStream fileInputStream) throws InvalidFormatFileException {
        BufferedInputStream bf = new BufferedInputStream(fileInputStream);

        byte[] headerBytes;
        try {
            headerBytes = bf.readNBytes(HEADER_BYTES);
        } catch (IOException e) {
            throw new InvalidFormatFileException("The file does not have a header to identify it.");
        }

        String header = new String(headerBytes, StandardCharsets.UTF_8);
        List<SavDriver> drivers = getDrivers("xyz.cupscoffee.files.api.driver.teams");

        // Verify which driver to use
        SavDriver driver = null;
        for (SavDriver savDriver : drivers) {
            String driverName = savDriver.getClass().getSimpleName();

            if (driverName.contains(header)) {
                driver = savDriver;
                break;
            }
        }

        if (driver == null)
            throw new InvalidFormatFileException("The file format is not supported.");

        try {
            fileInputStream.skip(HEADER_BYTES);
        } catch (IOException e) {
            throw new InvalidFormatFileException("The file does not have a header to identify it.");
        }

        return driver.readSavFile(fileInputStream);
    }

    /**
     * Get all drivers in the package {@code packageName}.
     * 
     * @param packageName The package name.
     * @return A list of {@code SavDriver}.
     */
    private List<SavDriver> getDrivers(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replace("[.]", "/"));
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream, StandardCharsets.UTF_8));

        return reader.lines()
                .filter(line -> line.endsWith(".java"))
                .map(line -> {
                    try {
                        return Class.forName(line);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .map(class_ -> {
                    try {
                        return (SavDriver) class_.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(class_ -> class_ != null)
                .toList();
    }
}
