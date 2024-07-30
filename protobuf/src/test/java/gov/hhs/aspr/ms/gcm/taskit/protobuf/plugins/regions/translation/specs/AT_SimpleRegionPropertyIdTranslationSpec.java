package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.support.SimpleRegionPropertyId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.support.input.SimpleRegionPropertyIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translation.RegionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.translation.ReportsTranslator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_SimpleRegionPropertyIdTranslationSpec {

    @Test
    @UnitTestConstructor(target = SimpleRegionPropertyIdTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new SimpleRegionPropertyIdTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testTranslateObject() {
        ProtobufTaskitEngine protobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslator(RegionsTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(PeopleTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .build();

        SimpleRegionPropertyIdTranslationSpec translationSpec = new SimpleRegionPropertyIdTranslationSpec();
        translationSpec.init(protobufTaskitEngine);

        SimpleRegionPropertyId expectedAppValue = new SimpleRegionPropertyId("test");

        SimpleRegionPropertyIdInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        SimpleRegionPropertyId actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = SimpleRegionPropertyIdTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        SimpleRegionPropertyIdTranslationSpec translationSpec = new SimpleRegionPropertyIdTranslationSpec();

        assertEquals(SimpleRegionPropertyId.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = SimpleRegionPropertyIdTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        SimpleRegionPropertyIdTranslationSpec translationSpec = new SimpleRegionPropertyIdTranslationSpec();

        assertEquals(SimpleRegionPropertyIdInput.class, translationSpec.getInputObjectClass());
    }
}
