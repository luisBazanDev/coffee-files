package xyz.cupscoffee.files.api;

import java.util.Map;

/**
 * Represents a disk.
 */
public interface Disk {
    /**
     * Returns the name of the disk.
     * 
     * @return The name of the disk.
     */
    String getName();

    /**
     * Returns the limit size of the disk in bytes.
     * 
     * @return The limit size of the disk in bytes
     */
    long getLimitSize();

    /**
     * Returns the occupied size of the disk in bytes.
     * 
     * @return The occupied size of the disk in bytes
     */
    long getOccupiedSize();

    /**
     * Returns the root folder of the disk.
     * 
     * @return The root folder of the disk.
     */
    Folder getRootFolder();

    /**
     * Returns the metadata of the disk.
     * 
     * @return The metadata of the disk.
     */
    Map<String, String> getMetadata();
}
