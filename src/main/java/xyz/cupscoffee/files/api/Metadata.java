package xyz.cupscoffee.files.api;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Map;

public interface Metadata {
    LocalDateTime getCreatedDateTime();

    LocalDateTime getLastModifiedDateTime();

    long getSize();

    Path getPath();

    Map<String, String> getOtherMeta();
}
