package eu.cyfronoid.core.mapping;

import java.util.Optional;

public interface Mapping {
    public static final Mapping NO_MAPPING = k -> Optional.empty();

    default String getMappedValueOrDefault(String key) {
        return getMappedValueOrDefault(key, key);
    }

    default String getMappedValueOrDefault(String key, String defaultValue) {
        return getMappedValue(key).orElse(defaultValue);
    }

    Optional<String> getMappedValue(String key);
}
