package xyz.cupscoffee.files.driver;

import xyz.cupscoffee.files.api.interfaces.Disk;

import java.io.FileInputStream;

public class CupsOfCoffeeDriver implements SavDriver {
    @Override
    public Disk[] readSavFile(FileInputStream fileInputStream) {
        // TODO: Unimplemented method
        throw new UnsupportedOperationException("Unimplemented method 'readSavFile'");
    }
}
