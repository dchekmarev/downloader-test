package com.dchekmarev.test.downloader;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;

public class NamedInputStreamTest {
	@Test
	public void testGetNameEmpty() {
		assertEquals("", getNameFor(""));
	}

	@Test
	public void testGetNameOneWord() {
		assertEquals("test", getNameFor("test"));
	}

	@Test
	public void testGetNameTwoWords() {
		assertEquals("test two", getNameFor("test two"));
	}

	@Test
	public void testGetNameSplitted() {
		assertEquals("two", getNameFor("test/two"));
	}

	@Test
	public void testGetNameMultiSplitted() {
		assertEquals("three", getNameFor("/test/two/three"));
	}

	@Test
	public void testGetNameSimpleUrl() {
		assertEquals("four", getNameFor("http://one.two/three/four"));
	}

	@Test
	public void testGetNameComplexUrl() {
		assertEquals("four", getNameFor("ftp://user:password@one.two/three/four"));
	}

	private String getNameFor(String path) {
		return new NamedInputStream(path, null).getName();
	}
}
