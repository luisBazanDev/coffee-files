package xyz.cupscoffee.files.api.interfaces;

import java.nio.file.Path;
import java.util.Date;
import java.util.Map;

public interface Metadata {
    Date getCreatedDate();

    Date getLastModifiedDate();

    long getSize();

    Path getPath();

    Map<String, String> getOtherMeta();
}
