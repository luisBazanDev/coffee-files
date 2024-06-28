package xyz.cupscoffee.files.api;

import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import xyz.cupscoffee.files.api.implementation.SimpleDisk;
import xyz.cupscoffee.files.api.implementation.SimpleFile;
import xyz.cupscoffee.files.api.implementation.SimpleFolder;
import xyz.cupscoffee.files.api.implementation.SimpleSavStructure;

public class DataSupplier {
    public static SavStructure getSavStructure() {
        String fileContent = "console.log('Hello, World!');\n";
        Base64.Encoder encoder = Base64.getEncoder();
        String encodedFileContent = encoder.encodeToString(fileContent.getBytes());
        ByteBuffer fileContentBuffer = ByteBuffer.wrap(encodedFileContent.getBytes());
        Map<String, String> metadata = new java.util.HashMap<>();
        metadata.put("author", "Elder");

        SimpleFile simpleFile = new SimpleFile("file.js",
                fileContentBuffer,
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("A:\\directory\\other_directory\\file.js"),
                metadata);
        SimpleFolder simpleFolder1 = new SimpleFolder("other_directory",
                List.of(simpleFile),
                new LinkedList<>(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("A:\\directory\\other_directory"),
                metadata);
        SimpleFolder simpleFolder2 = new SimpleFolder("directory",
                new java.util.LinkedList<>(),
                new LinkedList<>(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("A:\\directory\\directory"),
                metadata);
        SimpleFolder simpleFolder3 = new SimpleFolder("directory",
                new java.util.LinkedList<>(),
                List.of(simpleFolder1, simpleFolder2),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("A:\\directory"),
                metadata);
        SimpleFolder root = new SimpleFolder("",
                new java.util.LinkedList<>(),
                List.of(simpleFolder3),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Path.of("A:\\"),
                metadata);

        SimpleDisk simpleDisk = new SimpleDisk("A",
                root,
                root.getSize() * 3,
                new java.util.HashMap<>());

        SimpleSavStructure expectedSavFileStructure = new SimpleSavStructure(
                "CupsOfCoffee",
                new SimpleDisk[] { simpleDisk },
                new java.util.HashMap<>());

        return expectedSavFileStructure;
    }
}
