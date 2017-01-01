package com.dchekmarev.test.downloader;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

public class ProviderResolver {
	private final List<StreamProvider> providers;
	private final Reporter reporter;

	public ProviderResolver(List<StreamProvider> providers) {
		this(providers, new NullReporter());
	}

	public ProviderResolver(List<StreamProvider> providers, Reporter reporter) {
		this.providers = providers;
		this.reporter = reporter;
	}

	public NamedInputStream getNamedInputStream(String url) {
		try {
			return providers.stream()
				.filter(x -> x.supports(url))
				.findFirst().get()
				.getStream(url);
		} catch (IOException e) {
			reporter.streamOpenException(url, e);
		} catch (NoSuchElementException e) {
			reporter.unsupported(url);
		}
		return null;
	}
}
