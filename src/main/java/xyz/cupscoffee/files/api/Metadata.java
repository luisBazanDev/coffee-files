package xyz.cupscoffee.files.api;

import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;

public interface Metadata {
    Date getCreatedDate();

    Date getLastModifiedDate();

    long getSize();

    Path getPath();

    String getAuthor();

    HashMap<String, String> getOtherMeta();
}
