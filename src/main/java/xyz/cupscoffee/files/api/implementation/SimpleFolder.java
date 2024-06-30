package xyz.cupscoffee.files.api.implementation;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import xyz.cupscoffee.files.api.File;
import xyz.cupscoffee.files.api.Folder;

/**
 * Simple implementation of the {@link Folder} interface.
 */
public class SimpleFolder implements Folder {
    private String name;
    private List<File> files = new ArrayList<>();
    private List<Folder> folders = new ArrayList<>();
    private LocalDateTime created;
    private LocalDateTime lastModified;
    private long size;
    private Path path;
    private Map<String, String> otherMetadata;

    public SimpleFolder(String name,
            List<File> files,
            List<Folder> folders,
            LocalDateTime created,
            LocalDateTime lastModified,
            Path path,
            Map<String, String> otherMetadata) {
        Objects.requireNonNull(name, "Name cannot be null");
        Objects.requireNonNull(files, "Files cannot be null");
        Objects.requireNonNull(folders, "Folders cannot be null");
        Objects.requireNonNull(created, "Created cannot be null");
        Objects.requireNonNull(lastModified, "Last modified cannot be null");
        Objects.requireNonNull(path, "Path cannot be null");
        Objects.requireNonNull(otherMetadata, "Other metadata cannot be null");

        this.name = name;
        this.files = files;
        this.folders = folders;
        this.created = created;
        this.lastModified = lastModified;
        this.size = files.stream().mapToLong(File::getSize).sum() + folders.stream().mapToLong(Folder::getSize).sum();
        this.path = path;
        this.otherMetadata = otherMetadata;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setLastModifiedDateTime(LocalDateTime lastModified) {
        this.lastModified = lastModified;
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
    public Map<String, String> getOtherMetadata() {
        return this.otherMetadata;
    }

    @Override
    public List<Folder> getFolders() {
        return this.folders;
    }
}
