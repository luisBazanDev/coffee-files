package xyz.cupscoffee.files.api.domain;

import xyz.cupscoffee.files.api.interfaces.File;
import xyz.cupscoffee.files.api.interfaces.Folder;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FolderImp implements Folder {
    private String name;
    private List<File> files = new ArrayList<>();
    private List<Folder> folders = new ArrayList<>();
    private Date created;
    private Date lastModified;
    private long size;
    private Path path;
    private Map<String, String> meta;

    public FolderImp(String name, List<File> files, Date created,
            Date lastModified,
            long size, Path path, Map<String, String> meta) {
        this.name = name;
        this.files = files;
        this.created = created;
        this.lastModified = lastModified;
        this.size = size;
        this.path = path;
        this.meta = meta;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<File> getFiles() {
        return files;
    }

    @Override
    public Date getCreatedDate() {
        return this.created;
    }

    @Override
    public Date getLastModifiedDate() {
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

    @Override
    public List<Folder> getFolders() {
        return this.folders;
    }
}
