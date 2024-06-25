package xyz.cupscoffee.files.api;

import java.util.Map;

/**
 * Represents a {@code .sav} file.
 */
public interface SavFile {
    String getHeader();

    Disk[] getDisks();

    Map<String, String> getMetadata();
}
