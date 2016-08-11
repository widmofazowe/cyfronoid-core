package eu.cyfronoid.core.mapping.config;

import java.util.Map;

import com.google.common.collect.Maps;

public class MultiMappingConfig {
    public static final MultiMappingConfig EMPTY = new MultiMappingConfig();

    private Map<String, MappingConfig> mappings = Maps.newHashMap();

    public Map<String, MappingConfig> getMappings() {
        return mappings;
    }

    public void setMappings(Map<String, MappingConfig> mappings) {
        this.mappings = mappings;
    }

}
