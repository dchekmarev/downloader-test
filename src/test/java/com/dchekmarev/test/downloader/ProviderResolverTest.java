package com.dchekmarev.test.downloader;

import java.io.IOException;
import java.util.Collections;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import org.junit.Test;

public class ProviderResolverTest extends TestCommons {
	@Test
	public void testEmptyResolver() {
		ProviderResolver resolver = new ProviderResolver(emptyList());
		assertNull(resolver.getNamedInputStream(""));
		assertNull(resolver.getNamedInputStream("anything"));
		assertNull(resolver.getNamedInputStream("http://test.com/"));
	}

	@Test
	public void testSingle() {
		TestStreamProvider provider = new TestStreamProvider(EMPTY_STREAM1);
		ProviderResolver resolver = new ProviderResolver(Collections.singletonList(provider));
		assertNull(resolver.getNamedInputStream("test"));
		provider.supports(true);
		assertNotNull(resolver.getNamedInputStream("test"));
		provider.supports(false);
		assertNull(resolver.getNamedInputStream("test"));
	}

	@Test
	public void testIOException() {
		TestStreamProvider provider = new TestStreamProvider(EMPTY_STREAM1) {
			@Override
			public NamedInputStream getStream(String url) throws IOException {
				throw new IOException();
			}
		};
		provider.supports(true);
		ProviderResolver resolver = new ProviderResolver(Collections.singletonList(provider));
		assertNull(resolver.getNamedInputStream("test"));
	}

	@Test
	public void testManyNoMatch() {
		ProviderResolver resolver = getTestManyProvider();
		assertNull(resolver.getNamedInputStream("test"));
	}

	@Test
	public void testManyFirstMatch() {
		TestProviderResolver resolver = getTestManyProvider();
		resolver.providers.get(0).supports(true);
		assertSame(EMPTY_STREAM1, resolver.getNamedInputStream("test").getStream());
	}

	@Test
	public void testManySecondMatch() {
		TestProviderResolver resolver = getTestManyProvider();
		resolver.providers.get(1).supports(true);
		assertSame(EMPTY_STREAM2, resolver.getNamedInputStream("test").getStream());
	}

	@Test
	public void testManyThirdMatch() {
		TestProviderResolver resolver = getTestManyProvider();
		resolver.providers.get(2).supports(true);
		assertSame(EMPTY_STREAM3, resolver.getNamedInputStream("test").getStream());
	}

	@Test
	public void testManyAllMatchTakeFirst() {
		TestProviderResolver resolver = getTestManyProvider();
		resolver.providers.get(0).supports(true);
		resolver.providers.get(1).supports(true);
		resolver.providers.get(2).supports(true);
		assertSame(EMPTY_STREAM1, resolver.getNamedInputStream("test").getStream());
	}

	@Test
	public void testManyTwoThreeMatchTakeFirst() {
		TestProviderResolver resolver = getTestManyProvider();
		resolver.providers.get(1).supports(true);
		resolver.providers.get(2).supports(true);
		assertSame(EMPTY_STREAM2, resolver.getNamedInputStream("test").getStream());
	}
}
