package eu.cyfronoid.core.mapping;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import eu.cyfronoid.core.configuration.loader.ConfigurationInputStreamProvider;
import eu.cyfronoid.core.mapping.config.MappingConfig;
import eu.cyfronoid.core.mapping.config.MappingConfigProvider;
import eu.cyfronoid.core.mapping.config.YamlMappingConfigProvider;

public enum MappingFactory {
    ;

    private static MappingCreator DEFAULT_MAPPING = p -> new SimpleMapping(p);

    private static Map<MappingType, MappingCreator> mappingCreatorStrategies = ImmutableMap.<MappingType, MappingCreator>builder()
            .put(MappingType.REGEX, p -> new RegexMapping(p))
            .build();

    public static interface MappingCreator {
        Mapping createMapping(MappingConfig provider);
    }

    public static Mapping createMapping(ConfigurationInputStreamProvider configProvider, String fileName) {
        YamlMappingConfigProvider mappingConfigProvider = new YamlMappingConfigProvider(configProvider, fileName);
        return createMapping(mappingConfigProvider);
    }

    public static Mapping createMapping(MappingConfigProvider mappingConfigProvider) {
        MappingConfig config = mappingConfigProvider.getConfig();
        return createMapping(config);
    }

    public static Mapping createMapping(MappingConfig config) {
        MappingType type = config.getType();
        return mappingCreatorStrategies.getOrDefault(type, DEFAULT_MAPPING)
                .createMapping(config);
    }

}
