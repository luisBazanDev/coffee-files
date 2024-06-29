package xyz.cupscoffee.files.api;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Metadata interface for file or folder system objects.
 */
public interface Metadata {
    /**
     * Returns the creation date and time of the file or folder.
     * 
     * @return the creation date and time
     */
    LocalDateTime getCreatedDateTime();

    /**
     * Returns the last modified date and time of the file or folder.
     * 
     * @return the last modified date and time
     */
    LocalDateTime getLastModifiedDateTime();

    /**
     * Returns the size of the file or folder in bytes.
     * 
     * @return the size of the file or folder in bytes
     */
    long getSize();

    /**
     * Returns the path of the file or folder.
     * 
     * @return the path of the file or folder
     */
    Path getPath();

    /**
     * Returns the meta data of the file or folder.
     * 
     * @return the meta data of the file or folder
     */
    Map<String, String> getOtherMetadata();
}
