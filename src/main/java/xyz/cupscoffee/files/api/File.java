package xyz.cupscoffee.files.api;

import java.nio.ByteBuffer;

/**
 * File interface for file system objects.
 */
public interface File extends Metadata {
    /**
     * Returns the name of the file.
     * 
     * @return the name of the file
     */
    String getName();

    /**
     * Returns the content of the file.
     * 
     * @return the content of the file
     */
    ByteBuffer getContent();
}
