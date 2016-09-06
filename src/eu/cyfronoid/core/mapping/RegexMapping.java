package eu.cyfronoid.core.mapping;

import java.util.Optional;

import com.google.common.base.Preconditions;

import eu.cyfronoid.core.configuration.Mapper;
import eu.cyfronoid.core.mapping.config.MappingConfig;
import eu.cyfronoid.core.validator.annotation.NotNull;

public class RegexMapping implements Mapping {

    private final Mapper mapper;

    public RegexMapping(@NotNull MappingConfig mappingConfig) {
        this.mapper = new Mapper(Preconditions.checkNotNull(mappingConfig).getValues());
    }

    @Override
    public Optional<String> getMappedValue(String key) {
        return mapper.getMappedValue(key);
    }

}
