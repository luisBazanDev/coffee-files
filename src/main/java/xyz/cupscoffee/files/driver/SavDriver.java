package xyz.cupscoffee.files.driver;

import xyz.cupscoffee.files.api.Disk;

public interface SavDriver {
    Disk[] readSavFile();
}
