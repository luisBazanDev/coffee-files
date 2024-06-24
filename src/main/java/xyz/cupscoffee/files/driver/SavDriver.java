package xyz.cupscoffee.files.driver;

import xyz.cupscoffee.files.api.interfaces.Disk;

import java.io.FileInputStream;

public interface SavDriver {
    Disk[] readSavFile(FileInputStream fileInputStream);
}
