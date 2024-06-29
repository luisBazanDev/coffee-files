package xyz.cupscoffee.files.api;

import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import xyz.cupscoffee.files.api.implementation.SimpleDisk;
import xyz.cupscoffee.files.api.implementation.SimpleFile;
import xyz.cupscoffee.files.api.implementation.SimpleFolder;
import xyz.cupscoffee.files.api.implementation.SimpleSavStructure;

/**
 * A class that provides data for testing purposes.
 */
public class DataSupplier {
    /**
     * Returns a SavStructure object with the given header. Check it to see the
     * structure.
     * 
     * @param header the type of format the SavStructure is in
     * @return a SavStructure object with the given header
     */
    public static SavStructure getSavStructure(String header) {
        String fileContent = "console.log('Hello, World!');\n";
        Base64.Encoder encoder = Base64.getEncoder();
        String encodedFileContent = encoder.encodeToString(fileContent.getBytes());
        ByteBuffer fileContentBuffer = ByteBuffer.wrap(encodedFileContent.getBytes());
        Map<String, String> metadata = new java.util.HashMap<>();
        metadata.put("author", "Elder");

        Long createdEpochs = 1719577849L;
        Long lastModifiedEpochs = 1719577849L;
        LocalDateTime created = LocalDateTime.ofEpochSecond(createdEpochs, 0, ZoneOffset.of("Z"));
        LocalDateTime lastModified = LocalDateTime.ofEpochSecond(lastModifiedEpochs, 0, ZoneOffset.of("Z"));

        SimpleFile simpleFile = new SimpleFile("file.js",
                fileContentBuffer,
                created,
                lastModified,
                Path.of("A:\\directory\\other_directory\\file.js"),
                metadata);
        SimpleFile simpleFile2 = new SimpleFile("file2.js",
                fileContentBuffer,
                created,
                lastModified,
                Path.of("A:\\directory\\other_directory\\file2.js"),
                metadata);
        SimpleFile simpleFile3 = new SimpleFile("file3.js",
                fileContentBuffer,
                created,
                lastModified,
                Path.of("A:\\directory\\other_directory\\file3.js"),
                metadata);

        SimpleFolder simpleFolder1 = new SimpleFolder("other_directory",
                List.of(simpleFile, simpleFile2, simpleFile3),
                new LinkedList<>(),
                created,
                lastModified,
                Path.of("A:\\directory\\other_directory"),
                metadata);
        SimpleFolder simpleFolder2 = new SimpleFolder("directory",
                new java.util.LinkedList<>(),
                new LinkedList<>(),
                created,
                lastModified,
                Path.of("A:\\directory\\directory"),
                metadata);
        SimpleFolder simpleFolder3 = new SimpleFolder("directory",
                new java.util.LinkedList<>(),
                List.of(simpleFolder1, simpleFolder2),
                created,
                lastModified,
                Path.of("A:\\directory"),
                metadata);

        SimpleFolder root = new SimpleFolder("",
                new java.util.LinkedList<>(),
                List.of(simpleFolder3),
                created,
                lastModified,
                Path.of("A:\\"),
                metadata);

        SimpleDisk simpleDisk = new SimpleDisk("A",
                root,
                20000,
                new java.util.HashMap<>());

        SimpleSavStructure expectedSavFileStructure = new SimpleSavStructure(
                header,
                new SimpleDisk[] { simpleDisk },
                new java.util.HashMap<>());

        return expectedSavFileStructure;
    }
}
