package xyz.cupscoffee.files.api.implementation;

import java.util.Map;
import java.util.Objects;

import xyz.cupscoffee.files.api.Disk;
import xyz.cupscoffee.files.api.SavFile;

/**
 * Basic implementation of the {@link SavFile} interface.
 */
public class BasicSavFile implements SavFile {
    private String name;
    private String header;
    private Disk[] disks;
    private Map<String, String> metadata;

    public BasicSavFile(String name, String header, Disk[] disks, Map<String, String> metadata) {
        Objects.requireNonNull(header, "Header cannot be null");
        Objects.requireNonNull(name, "Name cannot be null");
        Objects.requireNonNull(disks, "Disks cannot be null");
        Objects.requireNonNull(metadata, "Metadata cannot be null");

        this.header = header;
        this.disks = disks;
        this.metadata = metadata;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getHeader() {
        return this.header;
    }

    @Override
    public Disk[] getDisks() {
        return this.disks;
    }

    @Override
    public Map<String, String> getMetadata() {
        return this.metadata;
    }

    public void setName(String name) {
        this.name = name;
    }
}
