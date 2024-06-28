package xyz.cupscoffee.files.api.driver.teams;

import xyz.cupscoffee.files.api.driver.SavDriver;
import xyz.cupscoffee.files.api.exception.InvalidFormatFileException;
import xyz.cupscoffee.files.api.implementation.SimpleSavStructure;
import xyz.cupscoffee.files.api.Disk;
import xyz.cupscoffee.files.api.File;
import xyz.cupscoffee.files.api.Folder;
import xyz.cupscoffee.files.api.SavStructure;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;

/**
 * CupsOfCoffee implementation of the SavDriver interface.
 * 
 * The CupsOfCoffeeDriver format has the following structure:
 */
public class CupsOfCoffeeDriver implements SavDriver {
    @Override
    public SavStructure readSavFile(InputStream inputStream) throws InvalidFormatFileException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (Exception e) {
            throw new InvalidFormatFileException("Error reading file");
        }

        String header = getClass().getSimpleName();
        header = header.substring(0, header.length() - 6);

        String[] rawData = sb.toString().split("\n");

        return new SimpleSavStructure(header, getDisks(Arrays.copyOfRange(rawData, 1, rawData.length - 1)),
                getMetadata(rawData[rawData.length - 1]));

    }

    private Disk[] getDisks(String[] rawDisksInfo) throws InvalidFormatFileException {
        Disk[] disks = new Disk[rawDisksInfo.length];

        for (int i = 0; i < rawDisksInfo.length; i++) {
            Disk disk = getDisk(rawDisksInfo[i]);
        }

        return disks;
    }

    private Disk getDisk(String rawDiskInfo) throws InvalidFormatFileException {
        int indexOfOpenBracket = rawDiskInfo.indexOf("[");

        if (indexOfOpenBracket == -1) {
            throw new InvalidFormatFileException("Invalid disk format");
        }

        String name = rawDiskInfo.substring(0, indexOfOpenBracket);
        

        return null;
    }

    private File getFile(String data) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private Folder getFolder(String data) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private HashMap<String, String> getMetadata(String rawMetadata) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private String decodeData(String encodedData) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedData);

        return new String(decodedBytes, StandardCharsets.UTF_8);
    }
}
