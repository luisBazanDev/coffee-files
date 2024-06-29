package xyz.cupscoffee.files.api.driver.teams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import xyz.cupscoffee.files.api.Disk;
import xyz.cupscoffee.files.api.driver.SavDriver;
import xyz.cupscoffee.files.api.exception.InvalidFormatFileException;
import xyz.cupscoffee.files.api.File;
import xyz.cupscoffee.files.api.Folder;
import xyz.cupscoffee.files.api.implementation.SimpleDisk;
import xyz.cupscoffee.files.api.implementation.SimpleFile;
import xyz.cupscoffee.files.api.implementation.SimpleFolder;
import xyz.cupscoffee.files.api.implementation.SimpleMetadata;
import xyz.cupscoffee.files.api.implementation.SimpleSavStructure;
import xyz.cupscoffee.files.api.Metadata;
import xyz.cupscoffee.files.api.SavStructure;

/**
 * CupsOfCoffee implementation of the SavDriver interface.
 * 
 * The CupsOfCoffeeDriver format has the following structure:
 * -Header-
 * -N Disk name-(-Capacity-)[-Metadata-]:|-Root-&lt-Root content as files and folders-&gt
 * -Metadata of the file-
 * 
 * File format:
 * *-File name-{-File content-}[-Metadata-]
 * 
 * Folder format:
 * |-Folder name-<Folder content as files and folders>-[-Metadata-]
 */
public class CupsOfCoffeeDriver implements SavDriver {
    @Override
    public SavStructure readSavFile(InputStream inputStream) throws InvalidFormatFileException {
        // Read all content of the file
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

        // Get the header
        String header = getClass().getSimpleName();
        header = header.substring(0, header.length() - 6);

        // All the raw data of the sav file
        String[] rawData = sb.toString().split("\n");

        // Get the simple metadata of the sav file like configurations, etc
        String simpleMetadata = rawData[rawData.length - 1];
        simpleMetadata = simpleMetadata.substring(1, simpleMetadata.length() - 1);

        return new SimpleSavStructure(
                header,
                getDisks(Arrays.copyOfRange(rawData, 1, rawData.length - 1)),
                getSimpleMetadata(simpleMetadata));
    }

    /**
     * Load each disk from the raw data of the sav file
     * 
     * @param rawDisksInfo raw data of the disks
     * @return an array of disks
     * @throws InvalidFormatFileException if the format of the disk is invalid
     */
    private Disk[] getDisks(String[] rawDisksInfo) throws InvalidFormatFileException {
        Disk[] disks = new Disk[rawDisksInfo.length];

        for (int i = 0; i < rawDisksInfo.length; i++) {
            Disk disk = getDisk(rawDisksInfo[i]);
            disks[i] = disk;
        }

        return disks;
    }

    /**
     * Load a disk from the raw data of the sav file
     * 
     * @param rawDiskInfo raw data of the disk
     * @return a disk
     * @throws InvalidFormatFileException if the format of the disk is invalid
     */
    private Disk getDisk(String rawDiskInfo) throws InvalidFormatFileException {
        // Load the name of the disk
        int indexOfOpenParenthesis = rawDiskInfo.indexOf("(");
        checkIndexOf(indexOfOpenParenthesis);

        String name = rawDiskInfo.substring(0, indexOfOpenParenthesis);

        // Load the limit size of the disk
        int indexOfCloseParenthesis = rawDiskInfo.indexOf(")");
        checkIndexOf(indexOfCloseParenthesis);

        int size = Integer.valueOf(rawDiskInfo.substring(indexOfOpenParenthesis + 1, indexOfCloseParenthesis));

        // Load the metadata of the disk
        int indexOfOpenBracket = rawDiskInfo.indexOf("[");
        checkIndexOf(indexOfOpenBracket);

        int indexOfCloseBracket = rawDiskInfo.indexOf("]");
        checkIndexOf(indexOfCloseBracket);

        String rawMetadata = rawDiskInfo.substring(indexOfOpenBracket + 2, indexOfCloseBracket - 1);
        Map<String, String> metadata = getSimpleMetadata(rawMetadata);

        if (rawDiskInfo.charAt(indexOfCloseBracket + 1) != ':')
            throw new InvalidFormatFileException("Invalid disk format");

        // Load the root folder of the disk
        String rawData = rawDiskInfo.substring(indexOfCloseBracket + 2);
        List<Folder> siblingFolders = new LinkedList<>();

        loadFilesAndFolders(siblingFolders, null, rawData);
        if (siblingFolders.isEmpty() || siblingFolders.size() > 1)
            throw new InvalidFormatFileException("Invalid disk format");

        Folder root = siblingFolders.getFirst();

        return new SimpleDisk(name, root, size, metadata);
    }

    /**
     * This function will load all the files and folders of a disk and return the
     * index of the metadata of the last folder in the stack because the function
     * uses recursivity.
     * 
     * @param siblingFolders list of sibling folders
     * @param siblingFiles   list of sibling files
     * @param rawData        raw data of the disk
     * @return the index of the metadata of the last folder in the stack
     * @throws InvalidFormatFileException if the format of the disk is invalid
     */
    private int loadFilesAndFolders(List<Folder> siblingFolders, List<File> siblingFiles, String rawData)
            throws InvalidFormatFileException {
        int index = 0;

        while (index < rawData.length()) {
            if (rawData.charAt(index) == '|') {
                index = loadFolder(siblingFolders, rawData, index);
            } else if (rawData.charAt(index) == '*') {
                index = loadFile(siblingFiles, rawData, index);
            } else if (rawData.charAt(index) == '>') { // End of the folder content
                break;
            } else {
                throw new InvalidFormatFileException("Invalid disk format");
            }
        }

        return index;
    }

    /**
     * Load a folder (from the raw data of the sav file) in the list of sibling
     * folders. This function will return the index of the next sibling or the end
     * of the folder parent content.
     * 
     * @param siblingFolders list of sibling folders
     * @param rawData        raw data of the disk
     * @param prevIndex      previous index of the start or previous sibling
     * @return the index of the next sibling or the end of the folder parent content
     * @throws InvalidFormatFileException if the format of the disk is invalid
     */
    private int loadFolder(List<Folder> siblingFolders, String rawData, int prevIndex)
            throws InvalidFormatFileException {
        rawData = rawData.substring(prevIndex); // It's greater than 0 when the folder has siblings as folders or files
        int indexOfLessThan = rawData.indexOf("<");
        checkIndexOf(indexOfLessThan);

        // Get the name of the folder
        String name = rawData.substring(1, indexOfLessThan);

        // Get the content of the folder like files and folders
        List<Folder> folders = new LinkedList<>();
        List<File> files = new LinkedList<>();
        int indexOfGreaterThan = loadFilesAndFolders( // Relative index from the start to the end of the folder content
                folders,
                files,
                rawData.substring(indexOfLessThan + 1))
                + indexOfLessThan
                + 1; // With this sum we obtain the absolute index of the end of folder content

        // Get the metadata of the folder
        String rawMetada = rawData.substring(indexOfGreaterThan + 1);
        int indexOfCloseBracket = rawMetada.indexOf("]");
        checkIndexOf(indexOfCloseBracket);

        rawMetada = rawMetada.substring(1, indexOfCloseBracket);
        Metadata metadata = getMetadata(rawMetada);

        // Get the folder and add it to the folder list of its parent
        Folder folder = new SimpleFolder(
                name,
                files,
                folders,
                metadata.getCreatedDateTime(),
                metadata.getLastModifiedDateTime(),
                metadata.getPath(),
                metadata.getOtherMetadata());

        siblingFolders.add(folder);

        return prevIndex // With the previous index, we ensure that we will move the index to the next
                         // sibling or the end
                + indexOfGreaterThan // Size of the content of the folder
                + indexOfCloseBracket // Size of the metadata of the folder
                + 2; // The two characters (the last character of the metadata and close bracket) of
                     // the folder format
    }

    /**
     * Load the file (from the raw data of the sav file) in the list of sibling
     * files. This function will return the index of the next sibling or the end of
     * the folder parent content.
     * 
     * @param siblingFiles list of sibling files
     * @param rawData      raw data of the disk
     * @param prevIndex    previous index of the start or previous sibling
     * @return the index of the next sibling or the end of the folder parent content
     * @throws InvalidFormatFileException if the format of the disk is invalid
     */
    private int loadFile(List<File> siblingFiles, String rawData, int prevIndex) throws InvalidFormatFileException {
        rawData = rawData.substring(prevIndex); // It's greater than 0 when the file has siblings
        int indexOfOpenBrace = rawData.indexOf("{");
        checkIndexOf(indexOfOpenBrace);

        // Get the name of the file
        String name = rawData.substring(1, indexOfOpenBrace);

        int indexOfCloseBrace = rawData.indexOf("}");
        checkIndexOf(indexOfCloseBrace);

        // Get the content of the file
        String content = rawData.substring(indexOfOpenBrace + 1, indexOfCloseBrace);

        int indexOfOpenBracket = rawData.indexOf("[");
        checkIndexOf(indexOfOpenBracket);

        int indexOfCloseBracket = rawData.indexOf("]");
        checkIndexOf(indexOfCloseBracket);

        // Get the metadata of the file
        String rawMetada = rawData.substring(indexOfOpenBracket + 1, indexOfCloseBracket);
        Metadata metadata = getMetadata(rawMetada);

        // Get the file and add it to the file list of its parent
        File file = new SimpleFile(
                name,
                ByteBuffer.wrap(content.getBytes()),
                metadata.getCreatedDateTime(),
                metadata.getLastModifiedDateTime(),
                metadata.getPath(),
                metadata.getOtherMetadata());

        siblingFiles.add(file);

        return prevIndex // With the previous index, we ensure that we will move the index to the next
                         // sibling or the end
                + indexOfCloseBracket // Size of the content and metadata characters
                + 1; // The character of close bracket (end of the metadata format)
    }

    /**
     * Load the metadata of a file or folder
     * 
     * @param rawMetada raw metadata of the file or folder
     * @return the metadata of the file or folder
     * @throws InvalidFormatFileException if the format of the disk is invalid
     */
    private Metadata getMetadata(String rawMetada) throws InvalidFormatFileException {
        String[] rawMetadatas = rawMetada.split(",");

        HashMap<String, String> metadata = new HashMap<>();

        for (int i = 0; i < rawMetadatas.length; i++) {
            int firstColon = rawMetadatas[i].indexOf(":"); // Use `indexOf` instead of `split`, because the paths have
                                                           // colons
            metadata.put(rawMetadatas[i].substring(0, firstColon), rawMetadatas[i].substring(firstColon + 1));
        }

        Long createdEpochs = Long.valueOf(metadata.get("CreatedDateTime"));
        Long lastModifiedEpochs = Long.valueOf(metadata.get("LastModifiedDateTime"));
        LocalDateTime created = LocalDateTime.ofEpochSecond(createdEpochs, 0, ZoneOffset.of("Z"));
        LocalDateTime lastModified = LocalDateTime.ofEpochSecond(lastModifiedEpochs, 0, ZoneOffset.of("Z"));
        String otherMetadata = metadata.get("OtherMetadata");
        otherMetadata = otherMetadata.substring(1, otherMetadata.length() - 1);

        return new SimpleMetadata(
                created,
                lastModified,
                Long.valueOf(metadata.get("Size")),
                Path.of(metadata.get("Path")),
                getSimpleMetadata(otherMetadata));
    }

    /**
     * Load the simple metadata (a map) of the sav file, disk, folder or file
     * 
     * @param rawSimpleMetadata raw simple metadata of the sav file, disk, folder or
     *                          file
     * @return a map with simple metadata
     * @throws InvalidFormatFileException if the format of the disk is invalid
     */
    private Map<String, String> getSimpleMetadata(String rawSimpleMetadata) throws InvalidFormatFileException {
        Map<String, String> simpleMetadata = new HashMap<>();

        if (rawSimpleMetadata == null || rawSimpleMetadata.isEmpty())
            return simpleMetadata;

        String[] rawSimpleMetadatas = rawSimpleMetadata.split(", ");

        for (int i = 0; i < rawSimpleMetadatas.length; i++) {
            String[] keyAndValues = rawSimpleMetadatas[i].split("=");
            simpleMetadata.put(keyAndValues[0], keyAndValues[1]);
        }

        return simpleMetadata;
    }

    /**
     * Check if the index is valid
     * 
     * @param index index to check
     * @throws InvalidFormatFileException if the index is -1 means that the format
     *                                    is invalid
     */
    private void checkIndexOf(int index) throws InvalidFormatFileException {
        if (index == -1)
            throw new InvalidFormatFileException("Invalid disk format");
    }
}
