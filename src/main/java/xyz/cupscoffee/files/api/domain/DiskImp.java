package xyz.cupscoffee.files.api.domain;

import xyz.cupscoffee.files.api.interfaces.Disk;
import xyz.cupscoffee.files.api.interfaces.Folder;

import java.util.Map;

public class DiskImp implements Disk {
	private String name;
	private Folder root;
	private long limitSize;
	private long occupied;
	private Map<String, String> meta;

	public DiskImp(String name, Folder root, long limitSize, long occupied,
			Map<String, String> meta) {
		this.name = name;
		this.root = root;
		this.limitSize = limitSize;
		this.occupied = occupied;
		this.meta = meta;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Folder getRootFolder() {
		return root;
	}

	@Override
	public long getLimitSize() {
		return limitSize;
	}

	public Folder getRoot() {
		return root;
	}

	@Override
	public long getOccupiedSize() {
		return occupied;
	}

	@Override
	public Map<String, String> getMeta() {
		return this.meta;
	}
}
