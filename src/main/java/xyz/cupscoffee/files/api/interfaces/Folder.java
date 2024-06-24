package xyz.cupscoffee.files.api.interfaces;

import java.util.List;

public interface Folder extends Metadata {
    String getName();
    List<File> getFiles();
}
