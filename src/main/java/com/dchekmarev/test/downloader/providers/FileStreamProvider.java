package com.dchekmarev.test.downloader.providers;

import com.dchekmarev.test.downloader.NamedInputStream;
import com.dchekmarev.test.downloader.StreamProvider;
import java.io.FileInputStream;

public class FileStreamProvider implements StreamProvider {
	@Override
	public NamedInputStream getStream(String url) {
		return new NamedInputStream(url, () -> new FileInputStream(url));
	}

	@Override
	public boolean supports(String url) {
		return null != url && url.toLowerCase().startsWith("/");
	}
}
