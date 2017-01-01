package com.dchekmarev.test.downloader;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public class Downloader {
	private final ProviderResolver resolver;
	private final ExecutorService executorService;
	private final String output;
	private final Reporter reporter;

	public Downloader(ProviderResolver resolver, ExecutorService executorService, String output) {
		this(resolver, executorService, output, new NullReporter());
	}

	public Downloader(ProviderResolver resolver, ExecutorService executorService, String output, Reporter reporter) {
		this.resolver = resolver;
		this.executorService = executorService;
		this.output = output;
		this.reporter = reporter;
	}

	public void download(Collection<String> urls) {
		List<CompletableFuture<Void>> futures = urls.stream()
			.map(resolver::getNamedInputStream)
			.filter(Objects::nonNull)
			.map(this::createStreamSaveTask)
			.map(task -> CompletableFuture.runAsync(task, executorService))
			.collect(Collectors.toList());
		CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).join();
	}

	protected StreamSaveTask createStreamSaveTask(NamedInputStream nis) {
		return new StreamSaveTask(nis, output, reporter);
	}
}