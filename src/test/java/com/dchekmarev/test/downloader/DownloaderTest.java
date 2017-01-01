package com.dchekmarev.test.downloader;

import java.util.ArrayList;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class DownloaderTest extends TestCommons {

	@Test
	public void testEmpty() {
		new Downloader(new ProviderResolver(emptyList()), ForkJoinPool.commonPool(), "").download(emptyList());
	}

	@Test
	public void testUnsupported() {
		final List<String> unsupportedUrls = new CopyOnWriteArrayList<>();
		List<String> sourceUrls = asList("1", "2", "3", "4");
		NullReporter reporter = new NullReporter() {
			@Override
			public void unsupported(String url) {
				unsupportedUrls.add(url);
			}
		};
		ProviderResolver resolver = new ProviderResolver(singletonList(new TestStreamProvider(EMPTY_STREAM1)), reporter);
		new Downloader(resolver, ForkJoinPool.commonPool(), "", reporter).download(sourceUrls);
		assertEquals(new TreeSet<>(sourceUrls), new TreeSet<>(unsupportedUrls));
	}

	@Test
	public void testCommonPipeline() {
		TestProviderResolver resolver = new TestProviderResolver(singletonList(new TestStreamProvider(EMPTY_STREAM1)));
		resolver.providers.get(0).supports(true);
		TestDownloader downloader = new TestDownloader(resolver, ForkJoinPool.commonPool(), "");
		List<String> simpleUrls = asList("test1", "test2");
		downloader.download(simpleUrls);

		assertEquals(1, downloader.taskList.get(0).completed);
		assertEquals(1, downloader.taskList.get(1).completed);

		List<String> processedNames = downloader.taskList.stream().map(t -> t.name).collect(Collectors.toList());
		assertEquals(new TreeSet<>(simpleUrls), new TreeSet<>(processedNames));
	}

	private static class TestDownloader extends Downloader {
		List<TestStreamSaveTask> taskList = new ArrayList<>();

		public TestDownloader(ProviderResolver resolver, ExecutorService executorService, String output) {
			super(resolver, executorService, output);
		}

		@Override
		protected TestStreamSaveTask createStreamSaveTask(NamedInputStream nis) {
			TestStreamSaveTask task = new TestStreamSaveTask(nis, "");
			taskList.add(task);
			return task;
		}
	}

	private static class TestStreamSaveTask extends StreamSaveTask {
		int completed;
		String name;

		public TestStreamSaveTask(NamedInputStream namedStream, String targetPath) {
			super(namedStream, targetPath);
			name = namedStream.getName();
		}

		@Override
		public void run() {
			completed++;
		}
	}
}
