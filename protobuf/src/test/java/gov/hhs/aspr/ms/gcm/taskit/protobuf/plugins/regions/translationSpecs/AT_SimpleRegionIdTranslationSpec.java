package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.support.SimpleRegionId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.support.input.SimpleRegionIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translation.RegionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translation.specs.SimpleRegionIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_SimpleRegionIdTranslationSpec {

    @Test
    @UnitTestConstructor(target = SimpleRegionIdTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new SimpleRegionIdTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testtranslateObject() {
        ProtobufTaskitEngine ProtobufTaskitEngine = IProtobufTaskitEngineBuilder()
                .addTranslator(RegionsTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(PeopleTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .build();

        SimpleRegionIdTranslationSpec translationSpec = new SimpleRegionIdTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        SimpleRegionId expectedAppValue = new SimpleRegionId("test");

        SimpleRegionIdInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        SimpleRegionId actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = SimpleRegionIdTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        SimpleRegionIdTranslationSpec translationSpec = new SimpleRegionIdTranslationSpec();

        assertEquals(SimpleRegionId.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = SimpleRegionIdTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        SimpleRegionIdTranslationSpec translationSpec = new SimpleRegionIdTranslationSpec();

        assertEquals(SimpleRegionIdInput.class, translationSpec.getInputObjectClass());
    }
}
