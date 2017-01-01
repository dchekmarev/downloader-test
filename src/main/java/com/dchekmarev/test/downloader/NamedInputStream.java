package com.dchekmarev.test.downloader;

import java.io.InputStream;

public class NamedInputStream {
	private final String name;
	private final InputStream stream;

	public NamedInputStream(String name, InputStream stream) {
		this.name = name;
		this.stream = stream;
	}

	public String getName() {
		return name.substring(Math.max(0, name.lastIndexOf("/") + 1));
	}

	public InputStream getStream() {
		return stream;
	}
}
