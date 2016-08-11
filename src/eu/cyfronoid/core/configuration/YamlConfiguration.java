package eu.cyfronoid.core.configuration;

import java.io.IOException;
import java.io.InputStream;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import eu.cyfronoid.core.configuration.loader.ConfigurationInputStreamProvider;

public class YamlConfiguration {
    private final ConfigurationInputStreamProvider inputStreamProvider;

    public YamlConfiguration(ConfigurationInputStreamProvider inputStreamProvider){
        this.inputStreamProvider = inputStreamProvider;
    }

    public <T> T loadConfigFile(String fileName, Class<T> type) {
        try(InputStream resourceAsStream = getInputStreamProvider().load(fileName)){
            Constructor constructor = new Constructor(type);
            TypeDescription typeDescription = new TypeDescription(type);
            constructor.addTypeDescription(typeDescription);
            Yaml yaml = new Yaml(constructor);
            return yaml.loadAs(resourceAsStream, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ConfigurationInputStreamProvider getInputStreamProvider(){
        return inputStreamProvider;
    }
}
