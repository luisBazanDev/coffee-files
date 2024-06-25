package xyz.cupscoffee.files.api.driver;

import xyz.cupscoffee.files.api.Disk;
import xyz.cupscoffee.files.api.SavFile;
import xyz.cupscoffee.files.api.exception.InvalidFormatFileException;

import java.io.FileInputStream;

/**
 * Interface for reading .sav files.
 */
public interface SavDriver {
    /**
     * Reads a .sav file and returns a SavFile object.
     * 
     * @see Disk
     *
     * @param fileInputStream The FileInputStream of the .sav file.
     * @return A SavFile object.
     * @throws InvalidFormatFileException If the file format is invalid.
     */
    SavFile readSavFile(FileInputStream fileInputStream) throws InvalidFormatFileException;
}
