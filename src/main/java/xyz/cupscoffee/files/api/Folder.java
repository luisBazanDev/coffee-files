package xyz.cupscoffee.files.api;

import java.util.List;

/**
 * Folder interface for file system objects.
 */
public interface Folder extends Metadata {
    /**
     * Returns the name of the folder.
     * 
     * @return the name of the folder
     */
    String getName();

    /**
     * Returns the files in the folder.
     * 
     * @return the files in the folder
     */
    List<File> getFiles();

    /**
     * Returns the folders in the folder.
     * 
     * @return the folders in the folder
     */
    List<Folder> getFolders();
}
