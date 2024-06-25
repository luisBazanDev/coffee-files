package xyz.cupscoffee.files.api;

import java.util.Map;

public interface Disk {
    String getName();

    long getLimitSize();

    long getOccupiedSize();

    Folder getRootFolder();

    Map<String, String> getMeta();
}
