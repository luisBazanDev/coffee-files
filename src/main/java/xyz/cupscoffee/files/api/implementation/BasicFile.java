package xyz.cupscoffee.files.api.implementation;

import xyz.cupscoffee.files.api.File;

import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

/**
 * Basic implementation of the {@link File} interface.
 */
public class BasicFile implements File {
    private String name;
    private ByteBuffer content;
    private LocalDateTime created;
    private LocalDateTime lastModified;
    private long size;
    private Path path;
    private Map<String, String> meta;

    public BasicFile(String name,
            ByteBuffer content,
            LocalDateTime created,
            LocalDateTime lastModified,
            long size,
            Path path,
            Map<String, String> meta) {
        Objects.requireNonNull(name, "Name cannot be null");
        Objects.requireNonNull(content, "Content cannot be null");
        Objects.requireNonNull(created, "Created cannot be null");
        Objects.requireNonNull(lastModified, "Last modified cannot be null");
        Objects.requireNonNull(path, "Path cannot be null");
        Objects.requireNonNull(meta, "Metadata cannot be null");

        this.name = name;
        this.content = content;
        this.created = created;
        this.lastModified = lastModified;
        this.size = size;
        this.path = path;
        this.meta = meta;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public ByteBuffer getContent() {
        return this.content;
    }

    public void setContent(ByteBuffer content) {
        this.content = content;
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

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public Path getPath() {
        return this.path;
    }

    @Override
    public Map<String, String> getOtherMeta() {
        return this.meta;
    }
}