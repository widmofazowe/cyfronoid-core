package eu.cyfronoid.core.text;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.google.common.collect.Maps;

public class Replacer {
    private final InputStream stream;
    private final Map<String, String> keyToValueMap = Maps.newHashMap();

    public Replacer(InputStream stream) {
        this.stream = stream;
    }

    public Replacer putProperties(Properties properties) {
        for (Entry<Object, Object> entry : properties.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            putWithBraces(key, value);
        }
        return this;
    }

    public Replacer put(String key, String value) {
        putWithBraces(key, value);
        return this;
    }

    private void putWithBraces(String key, String value) {
        keyToValueMap.put("\\$\\{" + key + "\\}", value);
    }

    public InputStream execute() {
        try {
            BufferedReader reader = new BufferedReader( new InputStreamReader(stream));
            String line;
            ItemStringBuilder b = new ItemStringBuilder();
            while((line = reader.readLine()) != null) {
                for (Map.Entry<String, String> entry : keyToValueMap.entrySet()) {
                    line = line.replaceAll(entry.getKey(), entry.getValue());
                }
                b.append(line);
            }
            stream.close();

            return new ByteArrayInputStream(b.toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
