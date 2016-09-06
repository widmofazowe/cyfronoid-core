package eu.cyfronoid.core.mapping.config;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.google.common.base.Preconditions;

import eu.cyfronoid.core.configuration.loader.ConfigurationInputStreamProvider;
import eu.cyfronoid.core.validator.annotation.NotNull;


public class YamlMappingConfigProvider implements MappingConfigProvider {
    private static final String VALUES = "values";
    private final ConfigurationInputStreamProvider inputStreamProvider;
    private final String fileName;

    public YamlMappingConfigProvider(@NotNull ConfigurationInputStreamProvider inputStreamProvider, String fileName) {
        this.fileName = Preconditions.checkNotNull(fileName);
        this.inputStreamProvider = Preconditions.checkNotNull(inputStreamProvider);
    }

    @Override
    public MappingConfig getConfig() {
        Constructor constructor = new Constructor(MappingConfig.class);

        TypeDescription buildingRuleDescription = new TypeDescription(MappingConfig.class);
        buildingRuleDescription.putMapPropertyType(VALUES, String.class, String.class);
        constructor.addTypeDescription(buildingRuleDescription);

        Yaml yaml = new Yaml(constructor);
        MappingConfig mappingConfig = (MappingConfig) yaml.load(inputStreamProvider.load(fileName));
        if(mappingConfig == null) {
            return MappingConfig.EMPTY;
        }

        return mappingConfig;
    }

}
