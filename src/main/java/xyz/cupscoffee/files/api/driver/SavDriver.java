package xyz.cupscoffee.files.api.driver;

import xyz.cupscoffee.files.api.SavFile;
import xyz.cupscoffee.files.api.exception.InvalidFormatFileException;

import java.io.FileInputStream;

/**
 * Interface for reading {@code .sav} files.
 */
public interface SavDriver {
    /**
     * Reads a {@code .sav} file and returns a SavFile object.
     * 
     * @see xyz.cupscoffee.files.api.SavFile
     *
     * @param fileInputStream The FileInputStream of the {@code .sav} file.
     * @return A SavFile object.
     * @throws InvalidFormatFileException If the file format is invalid.
     */
    SavFile readSavFile(FileInputStream fileInputStream) throws InvalidFormatFileException;
}