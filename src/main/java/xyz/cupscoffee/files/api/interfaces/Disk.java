package xyz.cupscoffee.files.api.interfaces;

import java.util.Map;

public interface Disk {
    String getName();

    long getLimitSize();

    long getOccupiedSize();

    Folder getRootFolder();

    Map<String, String> getMeta();
}
