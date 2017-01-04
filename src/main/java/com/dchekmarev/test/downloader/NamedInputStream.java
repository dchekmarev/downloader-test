package com.dchekmarev.test.downloader;

import java.io.IOException;
import java.io.InputStream;

public class NamedInputStream {
	private final String name;
	private final StreamSupplier inputStreamSupplier;

	public NamedInputStream(String name, StreamSupplier inputStreamSupplier) {
		this.name = name;
		this.inputStreamSupplier = inputStreamSupplier;
	}

	public String getName() {
		return name.substring(Math.max(0, name.lastIndexOf("/") + 1));
	}

	public InputStream getStream() throws IOException {
		return inputStreamSupplier.get();
	}
}
