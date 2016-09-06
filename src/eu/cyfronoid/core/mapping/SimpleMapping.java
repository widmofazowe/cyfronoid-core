package eu.cyfronoid.core.mapping;

import java.util.Map;
import java.util.Optional;

import com.google.common.base.Preconditions;

import eu.cyfronoid.core.mapping.config.MappingConfig;
import eu.cyfronoid.core.validator.annotation.NotNull;

public class SimpleMapping implements Mapping {

    private final Map<String, String> mapping;

    public SimpleMapping(@NotNull MappingConfig mappingConfig) {
        this.mapping = Preconditions.checkNotNull(mappingConfig).getValues();
    }

    @Override
    public Optional<String> getMappedValue(String key) {
        return Optional.ofNullable(mapping.get(key));
    }

}
