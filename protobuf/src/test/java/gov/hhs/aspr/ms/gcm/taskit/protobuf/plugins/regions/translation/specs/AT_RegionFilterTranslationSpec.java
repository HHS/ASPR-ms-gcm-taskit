package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.support.RegionFilter;
import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.testsupport.TestRegionId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.support.input.RegionFilterInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translation.RegionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_RegionFilterTranslationSpec {

    @Test
    @UnitTestConstructor(target = RegionFilterTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new RegionFilterTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testTranslateObject() {
        ProtobufTaskitEngine ProtobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslator(RegionsTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(PeopleTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .build();

        RegionFilterTranslationSpec translationSpec = new RegionFilterTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        RegionFilter expectedAppValue = new RegionFilter(TestRegionId.values());

        RegionFilterInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        RegionFilter actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = RegionFilterTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        RegionFilterTranslationSpec translationSpec = new RegionFilterTranslationSpec();

        assertEquals(RegionFilter.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = RegionFilterTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        RegionFilterTranslationSpec translationSpec = new RegionFilterTranslationSpec();

        assertEquals(RegionFilterInput.class, translationSpec.getInputObjectClass());
    }
}
