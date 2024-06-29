package xyz.cupscoffee.files.api.implementation;

import xyz.cupscoffee.files.api.Disk;
import xyz.cupscoffee.files.api.Folder;

import java.util.Map;
import java.util.Objects;

/**
 * Simple implementation of the {@link Disk} interface.
 *
 * @see Disk
 */
public class SimpleDisk implements Disk {
    private String name;
    private Folder root;
    private long limitSize;
    private long occupied;
    private Map<String, String> metadata;

    public SimpleDisk(String name,
            Folder root,
            long limitSize,
            Map<String, String> metadata) {
        Objects.requireNonNull(name, "Name cannot be null");
        Objects.requireNonNull(root, "Root folder cannot be null");
        Objects.requireNonNull(metadata, "Metadata cannot be null");

        if (limitSize < 0) {
            throw new IllegalArgumentException("Limit size cannot be negative");
        }

        if (limitSize < root.getSize()) {
            throw new IllegalArgumentException("Limit size cannot be less than the size of the root folder");
        }

        this.name = name;
        this.root = root;
        this.limitSize = limitSize;
        this.occupied = root.getSize();
        this.metadata = metadata;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Folder getRootFolder() {
        return this.root;
    }

    @Override
    public long getLimitSize() {
        return this.limitSize;
    }

    @Override
    public long getOccupiedSize() {
        return this.occupied;
    }

    @Override
    public Map<String, String> getMetadata() {
        return this.metadata;
    }
}
