package eu.cyfronoid.core.mapping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;

import eu.cyfronoid.core.configuration.loader.ResourceConfigProvider;

public class MappingFactoryTest {
    private static final String REGEX_MAPPING_YML = "regex-mapping.yml";
    private static final String TEST_MAPPING_YML = "test-mapping.yml";
    private static final ResourceConfigProvider configProvider = new ResourceConfigProvider(MappingFactoryTest.class);

    @Test
    public void test() {
        Mapping mapping = MappingFactory.createMapping(configProvider, TEST_MAPPING_YML);
        Optional<String> mappedValue = mapping.getMappedValue("x");
        assertTrue(mappedValue.isPresent());
        assertEquals("asdf", mappedValue.get());
    }

    @Test
    public void testDefault() {
        Mapping mapping = MappingFactory.createMapping(configProvider, TEST_MAPPING_YML);
        String mappedValue = mapping.getMappedValueOrDefault("y");
        assertEquals("y", mappedValue);
    }

    @Test
    public void testAnotherDefault() {
        Mapping mapping = MappingFactory.createMapping(configProvider, TEST_MAPPING_YML);
        String mappedValue = mapping.getMappedValueOrDefault("y", "z");
        assertEquals("z", mappedValue);
    }

    @Test
    public void testRegexMapping() {
        Mapping mapping = MappingFactory.createMapping(configProvider, REGEX_MAPPING_YML);
        Optional<String> mappedValue = mapping.getMappedValue("GigabitEthernet3");
        assertTrue(mappedValue.isPresent());
        assertEquals("Gi3", mappedValue.get());
    }

}
