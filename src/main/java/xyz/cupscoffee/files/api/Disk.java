package xyz.cupscoffee.files.api;

public interface Disk {
    String getName();

    Long getLimitSize();

    Long getOccupedSize();

    Folder getRootFolder();
}
