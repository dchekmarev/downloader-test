package com.dchekmarev.test.downloader;

import java.io.IOException;

public interface Reporter {
	void unsupported(String url);

	void streamOpenException(String url, IOException e);

	void copyIOException(String url, IOException e);

	void unlinkException(String url);
}
