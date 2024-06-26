package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.support.RegionPropertyId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.testsupport.TestRegionPropertyId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.RegionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.support.input.RegionPropertyIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_RegionPropertyIdTranslationSpec {

    @Test
    @UnitTestConstructor(target = RegionPropertyIdTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new RegionPropertyIdTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testConvertObject() {
        ProtobufTranslationEngine protobufTranslationEngine = ProtobufTranslationEngine.builder()
                .addTranslator(RegionsTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(PeopleTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .build();

        RegionPropertyIdTranslationSpec translationSpec = new RegionPropertyIdTranslationSpec();
        translationSpec.init(protobufTranslationEngine);

        RegionPropertyId expectedAppValue = TestRegionPropertyId.REGION_PROPERTY_1_BOOLEAN_MUTABLE;

        RegionPropertyIdInput inputValue = translationSpec.convertAppObject(expectedAppValue);

        RegionPropertyId actualAppValue = translationSpec.convertInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = RegionPropertyIdTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        RegionPropertyIdTranslationSpec translationSpec = new RegionPropertyIdTranslationSpec();

        assertEquals(RegionPropertyId.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = RegionPropertyIdTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        RegionPropertyIdTranslationSpec translationSpec = new RegionPropertyIdTranslationSpec();

        assertEquals(RegionPropertyIdInput.class, translationSpec.getInputObjectClass());
    }
}
