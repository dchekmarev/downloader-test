package com.dchekmarev.test.downloader;

import java.io.IOException;

public interface StreamProvider {
	NamedInputStream getStream(String url) throws IOException;

	boolean supports(String url);
}
