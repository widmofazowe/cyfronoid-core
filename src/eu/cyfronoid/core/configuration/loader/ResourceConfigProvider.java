package eu.cyfronoid.core.configuration.loader;

import java.io.InputStream;

import org.apache.log4j.Logger;

public class ResourceConfigProvider implements ConfigurationInputStreamProvider {
    private static final Logger logger = Logger.getLogger(ResourceConfigProvider.class);
    private final Class<?> clazz;

    public ResourceConfigProvider() {
        this(ResourceConfigProvider.class);
    }

    public ResourceConfigProvider(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public InputStream load(String fileName) {
        logger.debug("Loading file " + fileName);
        return clazz.getResourceAsStream(fileName);
    }

}
