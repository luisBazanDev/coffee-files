package xyz.cupscoffee.files.api.driver.teams;

import xyz.cupscoffee.files.api.Disk;
import xyz.cupscoffee.files.api.driver.SavDriver;
import xyz.cupscoffee.files.api.exception.InvalidFormatFileException;

import java.io.FileInputStream;

/**
 * CupsOfCoffee implementation of the SavDriver interface.
 */
public class CupsOfCoffeeDriver implements SavDriver {
    @Override
    public Disk[] readSavFile(FileInputStream fileInputStream) throws InvalidFormatFileException {
        // TODO: Unimplemented method
        throw new UnsupportedOperationException("Unimplemented method 'readSavFile'");
    }
}
