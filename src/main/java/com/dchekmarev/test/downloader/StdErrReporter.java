package com.dchekmarev.test.downloader;

import java.io.IOException;

public class StdErrReporter implements Reporter {
	public void unsupported(String url) {
		System.err.println("unsupported url: " + url);
	}

	public void streamOpenException(String url, IOException e) {
		System.err.println("can't open stream " + url + " (" + e.getMessage() + ")");
	}

	public void copyIOException(String url, IOException e) {
		System.err.println("error while reading/saving stream " + url + " (" + e.getMessage() + ")");
	}

	public void unlinkException(String url) {
		System.err.println("failed to delete incomplete file: " + url);
	}
}
