package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.testsupport.TestRegionId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.testsupport.input.TestRegionIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translation.RegionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translation.specs.TestRegionIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_TestRegionIdTranslationSpec {

    @Test
    @UnitTestConstructor(target = TestRegionIdTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new TestRegionIdTranslationSpec());
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

        TestRegionIdTranslationSpec translationSpec = new TestRegionIdTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        TestRegionId expectedAppValue = TestRegionId.REGION_1;

        TestRegionIdInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        TestRegionId actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = TestRegionIdTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        TestRegionIdTranslationSpec translationSpec = new TestRegionIdTranslationSpec();

        assertEquals(TestRegionId.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = TestRegionIdTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        TestRegionIdTranslationSpec translationSpec = new TestRegionIdTranslationSpec();

        assertEquals(TestRegionIdInput.class, translationSpec.getInputObjectClass());
    }
}
