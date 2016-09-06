package eu.cyfronoid.core.mapping.config;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.google.common.base.Preconditions;

import eu.cyfronoid.core.configuration.loader.ConfigurationInputStreamProvider;
import eu.cyfronoid.core.validator.annotation.NotNull;


public class YamlMultiMappingConfigProvider implements MultiMappingConfigProvider {
    private static final String MAPPINGS = "mappings";
    private final ConfigurationInputStreamProvider inputStreamProvider;
    private final String fileName;

    public YamlMultiMappingConfigProvider(@NotNull ConfigurationInputStreamProvider inputStreamProvider, String fileName) {
        this.fileName = Preconditions.checkNotNull(fileName);
        this.inputStreamProvider = Preconditions.checkNotNull(inputStreamProvider);
    }

    @Override
    public MultiMappingConfig getConfig() {
        Constructor constructor = new Constructor(MultiMappingConfig.class);

        TypeDescription buildingRuleDescription = new TypeDescription(MultiMappingConfig.class);
        buildingRuleDescription.putMapPropertyType(MAPPINGS, String.class, MappingConfig.class);
        constructor.addTypeDescription(buildingRuleDescription);

        Yaml yaml = new Yaml(constructor);
        MultiMappingConfig mappingConfig = (MultiMappingConfig) yaml.load(inputStreamProvider.load(fileName));
        if(mappingConfig == null) {
            return MultiMappingConfig.EMPTY;
        }

        return mappingConfig;
    }

}
