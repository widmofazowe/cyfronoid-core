package eu.cyfronoid.core.file;

import java.io.IOException;
import java.io.InputStream;

public class NamedInputStreamImpl implements NamedInputStream {
    final private String name;
    final private InputStream inputStream;
    private boolean opened = false;

    private NamedInputStreamImpl(String name, InputStream inputStream) {
        this.name = name;
        this.inputStream = inputStream;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public InputStream getInputStream() {
        return inputStream;
    }

    @Override
    public void close() {
        if(opened) {
            try {
                inputStream.close();
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



}


