package eu.cyfronoid.core.configuration;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public interface ConfigProperties {
    public Map<String, String> getConfigProperties();
    public String get(String name);
    public String getString(String name);
    public int getInt(String name);
    public float getFloat(String name);
    public long getLong(String name);
    public double getDouble(String name);
    public Set<Entry<String, String>> getEntrySet();
}
