package xyz.cupscoffee.files.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileInputStream;
import java.nio.file.Path;

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

        SavStructure actualSavFileStructure = null;
        try {
            actualSavFileStructure = savDriver.readSavFile(new FileInputStream(file));
        } catch (Exception e) {
            fail("Error reading file " + e.getMessage());
        }

        assertEquals(DataSupplier.getSavStructure(), actualSavFileStructure);
    }
}
