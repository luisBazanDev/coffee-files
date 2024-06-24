package xyz.cupscoffee.files.api.domain;

import xyz.cupscoffee.files.api.interfaces.File;

import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.Date;
import java.util.Map;

public class FileImp implements File {
	private String name;
	private ByteBuffer content;
	private Date created;
	private Date lastModified;
	private long size;
	private Path path;
	private Map<String, String> meta;

	public FileImp(String name, ByteBuffer content, Date created,
			Date lastModified,
			long size, Path path, Map<String, String> meta) {
		this.name = name;
		this.content = content;
		this.created = created;
		this.lastModified = lastModified;
		this.size = size;
		this.path = path;
		this.meta = meta;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public ByteBuffer getContent() {
		return this.content;
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
}
