package xyz.cupscoffee.files.exception;

import java.io.IOException;

/**
 * Exception thrown when the file format is invalid.
 */
public class InvalidFormatFile extends IOException {
    public InvalidFormatFile(String message) {
        super(message);
    }
}
