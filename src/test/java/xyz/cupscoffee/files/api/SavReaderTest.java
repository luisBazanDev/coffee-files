package xyz.cupscoffee.files.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import xyz.cupscoffee.files.api.driver.SavDriver;
import xyz.cupscoffee.files.api.driver.teams.CupsOfCoffeeDriver;

public class SavReaderTest {
    private SavDriver savDriver;
    private Path resourcesPath = Path.of("src", "test", "resources");

    @Test
    void testCupsOfCoffeeDriver() {
        savDriver = new CupsOfCoffeeDriver();

        java.io.File file = new java.io.File(resourcesPath.toFile(), "tcoc.sav");

        ByteBuffer savFileContent = null;
        try {
            savFileContent = ByteBuffer.wrap(toBytes(DataSupplier.getSavStructure()));

        } catch (Exception e) {
            fail("Error compressing file " + e.getMessage());
        }

        SavStructure actualSavFileStructure = null;
        try {
            java.nio.file.Files.write(file.toPath(), savFileContent.array());
            actualSavFileStructure = savDriver.readSavFile(new FileInputStream(file));
        } catch (Exception e) {
            fail("Error reading file " + e.getMessage());
        }

        assertEquals(DataSupplier.getSavStructure(), actualSavFileStructure);
    }

    public byte[] toBytes(SavStructure savFile) throws Exception {
        StringBuilder sb = new StringBuilder();

        // Get the header
        sb.append(savFile.getHeader() + "\n");

        // Write the disks
        Disk[] disks = savFile.getDisks();
        for (int i = 0; i < disks.length; i++) {
            Disk disk = disks[i];
            sb.append(String.format("%s[%s]:", disk.getName(), disk.getMetadata()));
            loadFolderAsString(disks[i].getRootFolder(), sb);

            sb.append("\n");
        }

        sb.append(savFile.getMetadata());

        String savFileContent = sb.toString();

        return savFileContent.getBytes();
    }

    private void loadFolderAsString(Folder folder, StringBuilder sb) {
        sb.append("|" + folder.getName() + "<");

        // Load folders
        List<Folder> subFolders = folder.getFolders();
        for (Folder subFolder : subFolders) {
            loadFolderAsString(subFolder, sb);
        }

        // Load files
        List<File> files = folder.getFiles();
        for (File file : files) {
            loadFileAsString(file, sb);
        }

        sb.append(">");

        // Load metadata
        loadMetadataAsString(folder, sb);
    }

    private void loadFileAsString(File file, StringBuilder sb) {
        sb.append("*" + file.getName() + "{");

        String content = new String(file.getContent().array(), StandardCharsets.UTF_8);
        ;
        sb.append(content);

        sb.append("}");

        // Load metadata
        loadMetadataAsString(file, sb);
    }

    private void loadMetadataAsString(Metadata metadata, StringBuilder sb) {
        sb.append("[");

        Arrays.asList(Metadata.class.getMethods()).forEach(method -> {
            if (method.getName().startsWith("get") && !method.getName().equals("getClass")) {
                try {
                    sb.append(method.getName().substring(3) + ":");

                    if (method.getReturnType().equals(LocalDateTime.class)) {
                        LocalDateTime localDateTime = (LocalDateTime) method.invoke(metadata);
                        sb.append(localDateTime.toEpochSecond(ZoneOffset.of("Z")) + ",");
                        return;
                    }

                    sb.append(method.invoke(metadata) + ",");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        sb.deleteCharAt(sb.length() - 1);

        sb.append("]");
    }
}
