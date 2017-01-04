package com.dchekmarev.test.downloader;

import java.io.IOException;
import java.io.InputStream;

public interface StreamSupplier {
	InputStream get() throws IOException;
}
