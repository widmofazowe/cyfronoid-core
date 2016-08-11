package eu.cyfronoid.core.mapping.config;

import java.util.Map;

import com.google.common.collect.Maps;

import eu.cyfronoid.core.mapping.MappingType;

public class MappingConfig {
    public static final MappingConfig EMPTY = new MappingConfig();

    private MappingType type;
    private Map<String, String> values;

    public MappingConfig() {
        this(null, Maps.newHashMap());
    }

    public static MappingConfig create(MappingType type, Map<String, String> values) {
        return new MappingConfig(type, values);
    }

    public MappingConfig(MappingType type, Map<String, String> values) {
        this.type = type;
        this.values = values;
    }

    public MappingType getType() {
        return type;
    }

    public void setType(MappingType type) {
        this.type = type;
    }

    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(Map<String, String> values) {
        this.values = values;
    }

}
