package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.support.RegionPropertyDimensionData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.testsupport.TestRegionId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.testsupport.TestRegionPropertyId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.support.input.RegionPropertyDimensionDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translation.RegionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.translation.ReportsTranslator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_RegionPropertyDimensionTranslationSpec {

    @Test
    @UnitTestConstructor(target = RegionPropertyDimensionTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new RegionPropertyDimensionTranslationSpec());
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

        RegionPropertyDimensionTranslationSpec translationSpec = new RegionPropertyDimensionTranslationSpec();
        translationSpec.init(protobufTaskitEngine);

        RegionPropertyDimensionData expectedAppValue = RegionPropertyDimensionData.builder()
                .setRegionId(TestRegionId.REGION_1)
                .setRegionPropertyId(TestRegionPropertyId.REGION_PROPERTY_3_DOUBLE_MUTABLE)
                .addValue("1",10.0)
                .addValue("2",1250.2)
                .addValue("3",15000.5)
                .build();

        RegionPropertyDimensionDataInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        RegionPropertyDimensionData actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = RegionPropertyDimensionTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        RegionPropertyDimensionTranslationSpec translationSpec = new RegionPropertyDimensionTranslationSpec();

        assertEquals(RegionPropertyDimensionData.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = RegionPropertyDimensionTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        RegionPropertyDimensionTranslationSpec translationSpec = new RegionPropertyDimensionTranslationSpec();

        assertEquals(RegionPropertyDimensionDataInput.class, translationSpec.getInputObjectClass());
    }
}
