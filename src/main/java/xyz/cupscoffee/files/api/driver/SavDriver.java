package xyz.cupscoffee.files.api.driver;

import xyz.cupscoffee.files.api.Disk;
import xyz.cupscoffee.files.api.exception.InvalidFormatFileException;

import java.io.FileInputStream;

/**
 * Interface for reading .sav files.
 */
public interface SavDriver {
    /**
     * Reads a .sav file and returns an array of Disk objects.
     * 
     * @see Disk
     *
     * @param fileInputStream The FileInputStream of the .sav file.
     * @return An array of Disk.
     * @throws InvalidFormatFileException If the file format is invalid.
     */
    Disk[] readSavFile(FileInputStream fileInputStream) throws InvalidFormatFileException;
}
