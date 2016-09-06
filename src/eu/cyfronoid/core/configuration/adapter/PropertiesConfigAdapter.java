package eu.cyfronoid.core.configuration.adapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import eu.cyfronoid.core.configuration.MapBasedConfigProperties;

public class PropertiesConfigAdapter extends MapBasedConfigProperties {
    private static Logger logger = Logger.getLogger(PropertiesConfigAdapter.class);

    public PropertiesConfigAdapter(String pathToFile) {
        try {
            processFileInputStream(new FileInputStream(pathToFile));
        } catch (FileNotFoundException e) {
            logger.warn(Messages.FILE_NOT_FOUND.get(pathToFile), e);
        }
    }

    public PropertiesConfigAdapter(InputStream fileInputStream) {
        processFileInputStream(fileInputStream);
    }

    private void processFileInputStream(InputStream fileInputStream) {
        Preconditions.checkNotNull(fileInputStream);
        Properties tempProperties = new Properties();
        try {
            tempProperties.load(fileInputStream);
            populateMapFromProperties(tempProperties);
        } catch (IOException e) {
            logger.warn(Messages.IO.get(), e);
        }
    }

    private void populateMapFromProperties(Properties tempProperties) {
        Map<String, String> map = Maps.newHashMap();
        tempProperties.stringPropertyNames().forEach(k -> map.put(k, tempProperties.getProperty(k)));
        setProperties(map);
    }

    protected enum Messages {
        FILE_NOT_FOUND("File {0} not found."),
        IO("Problem while processing file."),
        ;

        private final String message;

        Messages(String message) {
            this.message = message;
        }

        public String get(Object... arguments) {
            return MessageFormat.format(message, arguments);
        }
    }
}
