package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.support.RegionId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.testsupport.TestRegionId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.support.input.RegionIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translation.RegionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.translation.ReportsTranslator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_RegionIdTranslationSpec {

    @Test
    @UnitTestConstructor(target = RegionIdTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new RegionIdTranslationSpec());
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

        RegionIdTranslationSpec translationSpec = new RegionIdTranslationSpec();
        translationSpec.init(protobufTaskitEngine);

        RegionId expectedAppValue = TestRegionId.REGION_1;

        RegionIdInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        RegionId actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = RegionIdTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        RegionIdTranslationSpec translationSpec = new RegionIdTranslationSpec();

        assertEquals(RegionId.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = RegionIdTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        RegionIdTranslationSpec translationSpec = new RegionIdTranslationSpec();

        assertEquals(RegionIdInput.class, translationSpec.getInputObjectClass());
    }
}
