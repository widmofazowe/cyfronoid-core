package eu.cyfronoid.core.configuration.loader;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class SVNConfigProvider implements ConfigurationInputStreamProvider {

    private String baseUrl;

    public SVNConfigProvider(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public InputStream load(String fileName) {
        URL url;
        try {
            url = new URL(baseUrl + fileName);
            URLConnection connection = url.openConnection();
            InputStream stream = connection.getInputStream();
            return stream;
        } catch (MalformedURLException e) {
            rethrow(e);
        } catch (IOException e) {
            rethrow(e);
        }
        return null;
    }

    private void rethrow(Exception e) throws RuntimeException {
        throw new RuntimeException(e);
    }
}
