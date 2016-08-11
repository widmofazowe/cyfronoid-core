package eu.cyfronoid.core.mapping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.cyfronoid.core.configuration.loader.ResourceConfigProvider;

public class MultiMappingTest {
    private static final String TEST_MAPPING_YML = "multi-mapping.yml";

    private static MultiMapping mapping;

    @BeforeClass
    public static void setUp() {
        ResourceConfigProvider configProvider = new ResourceConfigProvider(MultiMappingTest.class);
        mapping = MultiMappingFactory.createMapping(configProvider, TEST_MAPPING_YML);
    }

    @Test
    public void testRadioEquipment() {
        Optional<String> mappedValue = mapping.getMappedValue("OptiX RTN 950", "RadioEquipment");
        assertTrue(mappedValue.isPresent());
        assertEquals("RTN950", mappedValue.get());
    }

    @Test
    public void testFan() {
        Optional<String> anotherMappedValue = mapping.getMappedValue("RTN910", "Fan");
        assertTrue(anotherMappedValue.isPresent());
        assertEquals("RTN910 FAN", anotherMappedValue.get());
    }

}
