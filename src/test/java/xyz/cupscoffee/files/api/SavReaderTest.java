package xyz.cupscoffee.files.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class SavReaderTest {
    private Path resourcesPath = Path.of("src", "test", "resources");

    @Test
    void testCupsOfCoffeeDriver() {
        SavStructure actualSavFileStructure = getSavStructure("tcoc.sav");

        SavStructure expectedSavFileStructure = DataSupplier.getSavStructure("CupsOfCoffee");
        testSavStructure(expectedSavFileStructure, actualSavFileStructure);
    }

    private SavStructure getSavStructure(String file) {
        java.io.File savFile = new java.io.File(resourcesPath.toFile(), file);

        SavStructure savStructure = null;
        try {
            savStructure = SavFileReader.readSavFile(new FileInputStream(savFile));
        } catch (Exception e) {
            fail("Error reading file " + e.getMessage());
        }

        return savStructure;
    }

    private void testSavStructure(SavStructure expectedSavStructure, SavStructure actualSavStructure) {
        assertEquals(expectedSavStructure.getDisks().length, actualSavStructure.getDisks().length);

        for (int i = 0; i < expectedSavStructure.getDisks().length; i++) {
            testDisk(expectedSavStructure.getDisks()[i], actualSavStructure.getDisks()[i]);
        }
    }

    private void testDisk(Disk expectedDisk, Disk actualDisk) {
        assertEquals(expectedDisk.getName(), actualDisk.getName());
        assertEquals(expectedDisk.getLimitSize(), actualDisk.getLimitSize());
        assertEquals(expectedDisk.getOccupiedSize(), actualDisk.getOccupiedSize());
        testFolder(expectedDisk.getRootFolder(), actualDisk.getRootFolder());
        testSimpleMetadata(expectedDisk.getMetadata(), actualDisk.getMetadata());
    }

    private void testFolder(Folder expectedFolder, Folder actualFolder) {
        assertEquals(expectedFolder.getName(), actualFolder.getName());
        assertEquals(expectedFolder.getFiles().size(), actualFolder.getFiles().size());
        assertEquals(expectedFolder.getFolders().size(), actualFolder.getFolders().size());

        for (int i = 0; i < expectedFolder.getFiles().size(); i++) {
            testFile(expectedFolder.getFiles().get(i), actualFolder.getFiles().get(i));
        }

        for (int i = 0; i < expectedFolder.getFolders().size(); i++) {
            testFolder(expectedFolder.getFolders().get(i), actualFolder.getFolders().get(i));
        }

        testMetadata(expectedFolder, actualFolder);
    }

    private void testFile(File expectedFile, File actualFile) {
        assertEquals(expectedFile.getName(), actualFile.getName());
        assertEquals(expectedFile.getContent(), actualFile.getContent());
        testMetadata(expectedFile, actualFile);
    }

    private void testMetadata(Metadata expectedMetadata, Metadata actualMetadata) {
        assertEquals(expectedMetadata.getCreatedDateTime(), actualMetadata.getCreatedDateTime());
        assertEquals(expectedMetadata.getLastModifiedDateTime(), actualMetadata.getLastModifiedDateTime());
        assertEquals(expectedMetadata.getSize(), actualMetadata.getSize());
        assertEquals(expectedMetadata.getPath(), actualMetadata.getPath());
        testSimpleMetadata(expectedMetadata.getOtherMetadata(), actualMetadata.getOtherMetadata());
    }

    private void testSimpleMetadata(Map<String, String> expectedSimpleMetadata,
            Map<String, String> actualSimpleMetadata) {
        assertEquals(expectedSimpleMetadata.size(), actualSimpleMetadata.size());
    }
}
