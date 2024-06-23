package xyz.cupscoffee.coffeefiles.exception;

import java.io.IOException;

public class InvalidFormatFile extends IOException {
    public InvalidFormatFile(String message) {
        super(message);
    }
}
