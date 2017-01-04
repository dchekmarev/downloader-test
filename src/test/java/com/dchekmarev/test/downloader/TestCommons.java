package com.dchekmarev.test.downloader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestCommons {

	protected static class TestProviderResolver extends ProviderResolver {
		final List<TestStreamProvider> providers;

		public TestProviderResolver(List<TestStreamProvider> providers) {
			super(providers.stream().map(p -> (StreamProvider)p).collect(Collectors.toList()));
			this.providers = providers;
		}
	}

	protected static final InputStream EMPTY_STREAM1 = new ByteArrayInputStream(new byte[0]);
	protected static final InputStream EMPTY_STREAM2 = new ByteArrayInputStream(new byte[0]);
	protected static final InputStream EMPTY_STREAM3 = new ByteArrayInputStream(new byte[0]);

	protected static class TestStreamProvider implements StreamProvider {
		private final InputStream stream;
		private boolean supports;

		public TestStreamProvider(InputStream stream) {
			this.stream = stream;
		}

		@Override
		public NamedInputStream getStream(String url) throws IOException {
			return new NamedInputStream(url, () -> stream);
		}

		@Override
		public boolean supports(String url) {
			return supports;
		}

		public void supports(boolean supports) {
			this.supports = supports;
		}
	}

	protected static TestProviderResolver getTestManyProvider() {
		TestStreamProvider provider1 = new TestStreamProvider(EMPTY_STREAM1);
		TestStreamProvider provider2 = new TestStreamProvider(EMPTY_STREAM2);
		TestStreamProvider provider3 = new TestStreamProvider(EMPTY_STREAM3);
		return new TestProviderResolver(Arrays.asList(provider1, provider2, provider3));
	}
}
