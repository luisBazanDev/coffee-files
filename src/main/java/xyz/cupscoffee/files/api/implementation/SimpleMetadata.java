package xyz.cupscoffee.files.api.implementation;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

import xyz.cupscoffee.files.api.Metadata;

/**
 * Simple implementation of the {@link Metadata} interface.
 */
public class SimpleMetadata implements Metadata {
    private LocalDateTime created;
    private LocalDateTime lastModified;
    private long size;
    private Path path;
    private Map<String, String> otherMetadata;

    public SimpleMetadata(LocalDateTime created,
            LocalDateTime lastModified,
            long size,
            Path path,
            Map<String, String> otherMetadata) {
        Objects.requireNonNull(created, "Created cannot be null");
        Objects.requireNonNull(lastModified, "Last modified cannot be null");
        Objects.requireNonNull(path, "Path cannot be null");
        Objects.requireNonNull(otherMetadata, "Metadata cannot be null");

        this.created = created;
        this.lastModified = lastModified;
        this.size = size;
        this.path = path;
        this.otherMetadata = otherMetadata;
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
    public Map<String, String> getOtherMetadata() {
        return this.otherMetadata;
    }
}
