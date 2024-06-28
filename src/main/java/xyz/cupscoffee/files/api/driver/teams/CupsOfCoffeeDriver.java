package xyz.cupscoffee.files.api.driver.teams;

import xyz.cupscoffee.files.api.driver.SavDriver;
import xyz.cupscoffee.files.api.exception.InvalidFormatFileException;
import xyz.cupscoffee.files.api.implementation.SimpleDisk;
import xyz.cupscoffee.files.api.implementation.SimpleSavStructure;
import xyz.cupscoffee.files.api.Disk;
import xyz.cupscoffee.files.api.File;
import xyz.cupscoffee.files.api.Folder;
import xyz.cupscoffee.files.api.Metadata;
import xyz.cupscoffee.files.api.SavStructure;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

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

        return new SimpleSavStructure(
                header,
                getDisks(Arrays.copyOfRange(rawData, 1, rawData.length - 1)),
                getSimpleMetadata(rawData[rawData.length - 1]));

    }

    private Disk[] getDisks(String[] rawDisksInfo) throws InvalidFormatFileException {
        Disk[] disks = new Disk[rawDisksInfo.length];

        for (int i = 0; i < rawDisksInfo.length; i++) {
            Disk disk = getDisk(rawDisksInfo[i]);
            disks[i] = disk;
        }

        return disks;
    }

    private Disk getDisk(String rawDiskInfo) throws InvalidFormatFileException {
        int indexOfOpenParenthesis = rawDiskInfo.indexOf("(");
        checkIndexOf(indexOfOpenParenthesis);

        String name = rawDiskInfo.substring(0, indexOfOpenParenthesis);

        int indexOfCloseParenthesis = rawDiskInfo.indexOf(")");
        checkIndexOf(indexOfCloseParenthesis);

        int size = Integer.valueOf(rawDiskInfo.substring(indexOfOpenParenthesis + 1, indexOfCloseParenthesis));

        int indexOfOpenBracket = rawDiskInfo.indexOf("[");
        checkIndexOf(indexOfOpenBracket);

        int indexOfCloseBracket = rawDiskInfo.indexOf("]");
        checkIndexOf(indexOfCloseBracket);

        String rawMetadata = rawDiskInfo.substring(indexOfOpenBracket + 1, indexOfCloseBracket);
        Map<String, String> metadata = getSimpleMetadata(rawMetadata);

        if (rawDiskInfo.charAt(indexOfCloseBracket + 1) != ':')
            throw new InvalidFormatFileException("Invalid disk format");

        String rawFolder = rawDiskInfo.substring(indexOfCloseBracket + 2);
        Folder root = getFolder(rawFolder);

        return new SimpleDisk(name, root, size, metadata);
    }

    private Folder getFolder(String rawFolder) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private File getFile(String rawFile) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private Metadata getMetadata(String rawMetada) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private Map<String, String> getSimpleMetadata(String rawSimpleMetadata) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private String decodeData(String encodedData) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedData);

        return new String(decodedBytes, StandardCharsets.UTF_8);
    }

    private void checkIndexOf(int index) throws InvalidFormatFileException {
        if (index == -1)
            throw new InvalidFormatFileException("Invalid disk format");
    }
}
