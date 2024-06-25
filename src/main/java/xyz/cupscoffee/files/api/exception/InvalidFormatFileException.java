package xyz.cupscoffee.files.api.exception;

import java.io.IOException;

/**
 * Exception thrown when the file format is invalid.
 */
public class InvalidFormatFileException extends IOException {
    public InvalidFormatFileException(String message) {
        super(message);
    }
}
