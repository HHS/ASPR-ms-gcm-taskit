package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.testsupport.TestRegionPropertyId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.RegionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.testsupport.input.TestRegionPropertyIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_TestRegionPropertyIdTranslationSpec {

    @Test
    @UnitTestConstructor(target = TestRegionPropertyIdTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new TestRegionPropertyIdTranslationSpec());
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

        TestRegionPropertyIdTranslationSpec translationSpec = new TestRegionPropertyIdTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        TestRegionPropertyId expectedAppValue = TestRegionPropertyId.REGION_PROPERTY_1_BOOLEAN_MUTABLE;

        TestRegionPropertyIdInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        TestRegionPropertyId actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = TestRegionPropertyIdTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        TestRegionPropertyIdTranslationSpec translationSpec = new TestRegionPropertyIdTranslationSpec();

        assertEquals(TestRegionPropertyId.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = TestRegionPropertyIdTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        TestRegionPropertyIdTranslationSpec translationSpec = new TestRegionPropertyIdTranslationSpec();

        assertEquals(TestRegionPropertyIdInput.class, translationSpec.getInputObjectClass());
    }
}
