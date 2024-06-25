package xyz.cupscoffee.files.driver.teams;

import xyz.cupscoffee.files.api.interfaces.Disk;
import xyz.cupscoffee.files.driver.SavDriver;
import xyz.cupscoffee.files.exception.InvalidFormatFile;

import java.io.FileInputStream;

/**
 * CupsOfCoffee implementation of the SavDriver interface.
 */
public class CupsOfCoffeeDriver implements SavDriver {
    @Override
    public Disk[] readSavFile(FileInputStream fileInputStream) throws InvalidFormatFile {
        // TODO: Unimplemented method
        throw new UnsupportedOperationException("Unimplemented method 'readSavFile'");
    }
}
