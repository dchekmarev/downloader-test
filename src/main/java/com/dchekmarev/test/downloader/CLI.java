package com.dchekmarev.test.downloader;

import com.dchekmarev.test.downloader.providers.FileStreamProvider;
import com.dchekmarev.test.downloader.providers.URLStreamProvider;
import static java.util.Arrays.asList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.OptionHandlerFilter;

public class CLI {
	@Option(name="-o", usage="output to this path", metaVar = "PATH")
	private String savePath = "/tmp";

	@Option(name="-c", usage="how many downloads to run in parallel", metaVar = "CONCURRENCY")
	private int concurrency = Runtime.getRuntime().availableProcessors();

	@Argument(usage = "list of urls to download", required = true, metaVar = "URL(s)")
	private List<String> urls = null;


	public static void main(String[] args) {
		new CLI().doMain(args);
	}

	public void doMain(String[] args) {
		CmdLineParser parser = new CmdLineParser(this);
		try {
			parser.parseArgument(args);
		} catch (CmdLineException e) {
			System.err.println(e.getMessage());
			String jarName = "downloader.jar";
			System.err.println("java -jar " + jarName + " [options...] arguments...");

			parser.printUsage(System.err);
			System.err.println();

			System.err.println("Example: java -jar " + jarName + parser.printExample(OptionHandlerFilter.PUBLIC) + " URL1 URL2 ...");
			return;
		}

		Reporter reporter = new StdErrReporter();

		ProviderResolver resolver = new ProviderResolver(asList(
			new FileStreamProvider(),
			new URLStreamProvider()
		), reporter);
		ExecutorService es = Executors.newFixedThreadPool(concurrency);

		new Downloader(resolver, es, savePath, reporter).download(urls);

		es.shutdown();
		try {
			es.awaitTermination(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
		es.shutdownNow();
	}
}
