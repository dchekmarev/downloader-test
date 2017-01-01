package com.dchekmarev.test.downloader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class StreamSaveTask implements Runnable {
	private final NamedInputStream namedStream;
	private final String targetPath;
	private final Reporter reporter;

	public StreamSaveTask(NamedInputStream namedStream, String targetPath) {
		this(namedStream, targetPath, new NullReporter());
	}

	public StreamSaveTask(NamedInputStream namedStream, String targetPath, Reporter reporter) {
		this.namedStream = namedStream;
		this.targetPath = targetPath;
		this.reporter = reporter;
	}

	@Override
	public void run() {
		Path target = Paths.get(targetPath, namedStream.getName());
		try {
			Files.copy(namedStream.getStream(), target, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			reporter.copyIOException(namedStream.getName(), e);
			try {
				Files.delete(target);
			} catch (IOException e1) {
				reporter.unlinkException(target.toString());
			}
		}
	}
}
