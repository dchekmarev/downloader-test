### System requirements

java 8, maven

### Build

mvn clean package

### Run

java -jar target/downloader-1.0-SNAPSHOT.jar -o OUTPUT_DIR URL1 URL2 ...

### How it works

entry point - CLI, it parses command line args, instantiates necessary classes and executes Downloader.download(urls).
downloader with help of ProviderResolver (instantiated in CLI) resolves concrete StreamProviders and save their streams with StreamSaveTask.
