package xyz.cupscoffee.files.api;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import xyz.cupscoffee.files.api.interfaces.Disk;
import xyz.cupscoffee.files.driver.CupsOfCoffeeDriver;
import xyz.cupscoffee.files.exception.InvalidFormatFile;

public class SavReaderUtil {
    private final int HEADER_BYTES = 16;

    public SavReaderUtil() {
    }

    public Disk[] readSavFile(FileInputStream fileInputStream) throws InvalidFormatFile {
        BufferedInputStream bf = new BufferedInputStream(fileInputStream);

        byte[] headerBytes;
        try {
            headerBytes = bf.readNBytes(HEADER_BYTES);
        } catch (IOException e) {
            throw new InvalidFormatFile("The file does not have a header to identify it.");
        }

        String header = new String(headerBytes, StandardCharsets.UTF_8);

        switch (header) {
            case "CupsOfCoffee":
                return new CupsOfCoffeeDriver().readSavFile(fileInputStream);
            default:
                throw new InvalidFormatFile("The file is not compatible");
        }
    }
}
