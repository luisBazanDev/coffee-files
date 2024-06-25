package xyz.cupscoffee.files.api.implementation;

import java.util.Map;

import xyz.cupscoffee.files.api.Disk;
import xyz.cupscoffee.files.api.SavFile;

public class BasicSavFile implements SavFile {
    private String header;
    private Disk[] disks;
    private Map<String, String> metadata;

    public BasicSavFile(String header, Disk[] disks, Map<String, String> metadata) {
        this.header = header;
        this.disks = disks;
        this.metadata = metadata;
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
}
