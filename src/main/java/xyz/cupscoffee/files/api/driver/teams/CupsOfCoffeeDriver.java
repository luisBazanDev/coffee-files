package xyz.cupscoffee.files.api.driver.teams;

import xyz.cupscoffee.files.api.driver.SavDriver;
import xyz.cupscoffee.files.api.exception.InvalidFormatFileException;
import xyz.cupscoffee.files.api.SavFile;

import java.io.FileInputStream;

/**
 * CupsOfCoffee implementation of the SavDriver interface.
 */
public class CupsOfCoffeeDriver implements SavDriver {
    @Override
    public SavFile readSavFile(FileInputStream fileInputStream) throws InvalidFormatFileException {
        // TODO: Unimplemented method
        throw new UnsupportedOperationException("Unimplemented method 'readSavFile'");
    }
}
