package eu.cyfronoid.core.mapping;

import eu.cyfronoid.core.configuration.loader.ConfigurationInputStreamProvider;
import eu.cyfronoid.core.mapping.MultiMapping.Builder;
import eu.cyfronoid.core.mapping.config.MultiMappingConfig;
import eu.cyfronoid.core.mapping.config.MultiMappingConfigProvider;
import eu.cyfronoid.core.mapping.config.YamlMultiMappingConfigProvider;

public enum MultiMappingFactory {
    ;

    public static MultiMapping createMapping(ConfigurationInputStreamProvider configProvider, String fileName) {
        YamlMultiMappingConfigProvider mappingConfigProvider = new YamlMultiMappingConfigProvider(configProvider, fileName);
        return createMapping(mappingConfigProvider);
    }

    public static MultiMapping createMapping(MultiMappingConfigProvider mappingConfigProvider) {
        MultiMappingConfig config = mappingConfigProvider.getConfig();
        return createMapping(config);
    }

    public static MultiMapping createMapping(MultiMappingConfig config) {
        Builder builder = MultiMapping.create();
        config.getMappings().forEach((t, c) -> builder.addMapping(t, c));
        return builder.build();
    }
}
