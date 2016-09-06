package eu.cyfronoid.core.file;

import java.io.InputStream;

public interface NamedInputStream {
    String getName();
    InputStream getInputStream();
    void close();
}
