package eu.cyfronoid.core.mapping;

import java.util.Map;
import java.util.Optional;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import eu.cyfronoid.core.injector.validator.NotNull;
import eu.cyfronoid.core.mapping.config.MappingConfig;

public class MultiMapping {
    private final Map<String, Mapping> mapping;

    public static Builder create() {
        return new Builder();
    }

    private MultiMapping(Map<String, Mapping> mapping) {
        this.mapping = ImmutableMap.<String, Mapping>builder()
                .putAll(mapping)
                .build();
    }

    public Map<String, Mapping> getMapping() {
        return mapping;
    }

    public Optional<String> getMappedValue(String key, String type) {
        return getMappingFor(type).getMappedValue(key);
    }

    public String getMappedValueOrDefault(String key, String type) {
        return getMappingFor(type).getMappedValueOrDefault(key);
    }

    public String getMappedValueOrDefault(String key, String defaultValue, String type) {
        return getMappingFor(type).getMappedValueOrDefault(key, defaultValue);
    }

    private Mapping getMappingFor(String type) {
        return mapping.getOrDefault(type, Mapping.NO_MAPPING);
    }

    public static class Builder {
        private final Map<String, Mapping> mappingByKey = Maps.newHashMap();

        public Builder addMapping(@NotNull String type, @NotNull MappingConfig config) {
            Mapping mapping = MappingFactory.createMapping(config);
            mappingByKey.put(type, mapping);
            return this;
        }

        public MultiMapping build() {
            return new MultiMapping(mappingByKey);
        }
    }
}
