package com.dchekmarev.test.downloader.providers;

import com.dchekmarev.test.downloader.NamedInputStream;
import com.dchekmarev.test.downloader.StreamProvider;
import java.io.IOException;
import java.net.URL;

public class URLStreamProvider implements StreamProvider {
	@Override
	public NamedInputStream getStream(String url) throws IOException {
		return new NamedInputStream(url, new URL(url).openStream());
	}

	@Override
	public boolean supports(String url) {
		return null != url && url.toLowerCase().contains("://");
	}
}
