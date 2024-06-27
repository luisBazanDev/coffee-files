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
    private Map<String, String> meta;

    public SimpleDisk(String name,
            Folder root,
            long limitSize,
            long occupied,
            Map<String, String> meta) {
        Objects.requireNonNull(name, "Name cannot be null");
        Objects.requireNonNull(root, "Root folder cannot be null");
        Objects.requireNonNull(meta, "Metadata cannot be null");

        this.name = name;
        this.root = root;
        this.limitSize = limitSize;
        this.occupied = occupied;
        this.meta = meta;
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

    public void setOccupiedSize(long occupied) {
        this.occupied = occupied;
    }

    @Override
    public Map<String, String> getMetadata() {
        return this.meta;
    }
}
