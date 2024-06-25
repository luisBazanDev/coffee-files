package xyz.cupscoffee.files.api;

import java.util.List;

public interface Folder extends Metadata {
    String getName();

    List<File> getFiles();

    List<Folder> getFolders();
}
