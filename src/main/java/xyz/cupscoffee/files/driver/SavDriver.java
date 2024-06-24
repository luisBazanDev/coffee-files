package xyz.cupscoffee.files.driver;

import xyz.cupscoffee.files.api.interfaces.Disk;
import xyz.cupscoffee.files.exception.InvalidFormatFile;

import java.io.FileInputStream;

public interface SavDriver {
    Disk[] readSavFile(FileInputStream fileInputStream) throws
            InvalidFormatFile;
}
