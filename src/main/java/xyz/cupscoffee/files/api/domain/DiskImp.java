package xyz.cupscoffee.files.api.domain;

import xyz.cupscoffee.files.api.Disk;
import xyz.cupscoffee.files.api.Folder;

import java.util.Map;

public class DiskImp implements Disk {
    private String name;
    private Folder root;
    private long limitSize;
    private long occupied;
    private Map<String, String> meta;

    public DiskImp(String name,
            Folder root,
            long limitSize,
            long occupied,
            Map<String, String> meta) {
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

    public Folder getRoot() {
        return this.root;
    }

    @Override
    public long getOccupiedSize() {
        return this.occupied;
    }

    @Override
    public Map<String, String> getMeta() {
        return this.meta;
    }
}
