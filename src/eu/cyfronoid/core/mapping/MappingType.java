package eu.cyfronoid.core.mapping;

import java.util.Map;

import eu.cyfronoid.core.mapping.config.MappingConfig;

public enum MappingType {
    SIMPLE,
    REGEX,
    ;


    public Mapping createMapping(Map<String, String> values) {
        return MappingFactory.createMapping(() -> createMappingConfig(values));
    }

    public MappingConfig createMappingConfig(Map<String, String> values) {
        return MappingConfig.create(this, values);
    }
}
