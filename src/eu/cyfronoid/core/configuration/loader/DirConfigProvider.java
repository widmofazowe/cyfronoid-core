package eu.cyfronoid.core.configuration.loader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.log4j.Logger;

public class DirConfigProvider implements ConfigurationInputStreamProvider {
    private static final Logger logger = Logger.getLogger(DirConfigProvider.class);
    private String dir;

    public DirConfigProvider(String dir) {
        this.dir = dir;
    }

    @Override
    public InputStream load(String fileName) {
        logger.debug("Loading file " + fileName);
        try {
            return new FileInputStream(dir + "/" + fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
