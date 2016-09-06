package eu.cyfronoid.core.configuration;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.Maps;

public abstract class MapBasedConfigProperties implements ConfigProperties {
    private Map<String, String> properties = Maps.newHashMap();

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    protected void addProperties(Map<String, String> properties) {
        this.properties.putAll(properties);
    }

    @Override
    public String get(String name) {
        return properties.get(name);
    }

    @Override
    public String getString(String name) {
        return properties.get(name);
    }

    @Override
    public int getInt(String name) {
        return Integer.parseInt(properties.get(name));
    }

    @Override
    public long getLong(String name) {
        return Long.parseLong(properties.get(name));
    }

    @Override
    public float getFloat(String name) {
        return Float.parseFloat(properties.get(name));
    }

    @Override
    public double getDouble(String name) {
        return Double.parseDouble(properties.get(name));
    }

    @Override
    public Map<String, String> getConfigProperties() {
        return properties;
    }

    @Override
    public Set<Entry<String, String>> getEntrySet() {
        return properties.entrySet();
    }

}
