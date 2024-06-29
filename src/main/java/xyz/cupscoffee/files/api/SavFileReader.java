package xyz.cupscoffee.files.api;

import java.io.InputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

        return driver.readSavFile(inputStream);
    }

    /**
     * Get all drivers in the package {@code packageName}.
     * 
     * @param packageName The package name.
     * @return A list of {@code SavDriver}.
     */
    private static List<SavDriver> getDrivers(String packageName) {
        try {
            Path path = Paths.get("src", "main", "java", packageName.replace(".", java.io.File.separator));
            return Files.walk(path)
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".java"))
                    .map(p -> {
                        String className = packageName + "." + p.getFileName().toString().replace(".java", "");
                        try {
                            return Class.forName(className);
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
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
