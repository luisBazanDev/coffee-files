package xyz.cupscoffee.files.api.domain;

import xyz.cupscoffee.files.api.File;

import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Map;

public class FileImp implements File {
    private String name;
    private ByteBuffer content;
    private LocalDateTime created;
    private LocalDateTime lastModified;
    private long size;
    private Path path;
    private Map<String, String> meta;

    public FileImp(String name,
            ByteBuffer content,
            LocalDateTime created,
            LocalDateTime lastModified,
            long size,
            Path path,
            Map<String, String> meta) {
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

    @Override
    public ByteBuffer getContent() {
        return this.content;
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
        return this.meta;
    }
}
