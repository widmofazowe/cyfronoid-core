package eu.cyfronoid.core.configuration.adapter;

import com.google.common.base.Preconditions;

import eu.cyfronoid.core.configuration.ConfigProperties;
import eu.cyfronoid.core.configuration.MapBasedConfigProperties;

public class CompositeConfigAdapter extends MapBasedConfigProperties {
    public void addConfigProperties(ConfigProperties properties) {
        Preconditions.checkNotNull(properties);
        addProperties(properties.getConfigProperties());
    }
}
