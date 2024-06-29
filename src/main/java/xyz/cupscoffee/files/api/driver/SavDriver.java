package xyz.cupscoffee.files.api.driver;

import xyz.cupscoffee.files.api.exception.InvalidFormatFileException;
import xyz.cupscoffee.files.api.SavStructure;

import java.io.InputStream;

/**
 * Interface for reading {@code .sav} files.
 */
public interface SavDriver {
    /**
     * Reads a {@code .sav} file and returns a SavFile object.
     * 
     * @see xyz.cupscoffee.files.api.SavStructure
     *
     * @param inputStream The InputStream of the {@code .sav} file.
     * @return A SavFile object.
     * @throws InvalidFormatFileException If the file format is invalid.
     */
    SavStructure readSavFile(InputStream inputStream) throws InvalidFormatFileException;
}
