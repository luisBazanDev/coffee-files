package xyz.cupscoffee.files.api.domain;

import xyz.cupscoffee.files.api.File;
import xyz.cupscoffee.files.api.Folder;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FolderImp implements Folder {
    private String name;
    private List<File> files = new ArrayList<>();
    private List<Folder> folders = new ArrayList<>();
    private LocalDateTime created;
    private LocalDateTime lastModified;
    private long size;
    private Path path;
    private Map<String, String> metaData;

    public FolderImp(String name,
            List<File> files,
            LocalDateTime created,
            LocalDateTime lastModified,
            long size,
            Path path,
            Map<String, String> metaData) {
        this.name = name;
        this.files = files;
        this.created = created;
        this.lastModified = lastModified;
        this.size = size;
        this.path = path;
        this.metaData = metaData;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<File> getFiles() {
        return files;
    }

    @Override
    public LocalDateTime getCreatedDateTime() {
        return this.created;
    }

    @Override
    public LocalDateTime getLastModifiedDateTime() {
        return this.lastModified;
    }

    @Override
    public long getSize() {
        return this.size;
    }

    @Override
    public Path getPath() {
        return this.path;
    }

    @Override
    public Map<String, String> getOtherMeta() {
        return this.metaData;
    }

    @Override
    public List<Folder> getFolders() {
        return this.folders;
    }
}
