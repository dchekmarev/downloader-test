package com.dchekmarev.test.downloader;

import java.io.IOException;

public class NullReporter implements Reporter {
	public void unsupported(String url) {
	}

	public void streamOpenException(String url, IOException e) {
	}

	public void copyIOException(String url, IOException e) {
	}

	public void unlinkException(String url) {
	}
}
