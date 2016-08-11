package eu.cyfronoid.core.configuration.loader;

import java.io.InputStream;

public interface ConfigurationInputStreamProvider {
    InputStream load(String fileName);
}
