package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.Equality;
import gov.hhs.aspr.ms.gcm.simulation.plugins.resources.support.ResourceFilter;
import gov.hhs.aspr.ms.gcm.simulation.plugins.resources.testsupport.TestResourceId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.PartitionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.RegionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.ResourcesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.support.input.ResourceFilterInput;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_ResourceFilterTranslationSpec {

    @Test
    @UnitTestConstructor(target = ResourceFilterTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new ResourceFilterTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testConvertObject() {
        ProtobufTranslationEngine protobufTranslationEngine = ProtobufTranslationEngine.builder()
                .addTranslator(ResourcesTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(PeopleTranslator.getTranslator())
                .addTranslator(RegionsTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .addTranslator(PartitionsTranslator.getTranslator())
                .build();

        ResourceFilterTranslationSpec translationSpec = new ResourceFilterTranslationSpec();
        translationSpec.init(protobufTranslationEngine);

        ResourceFilter expectedAppValue = new ResourceFilter(TestResourceId.RESOURCE_1, Equality.EQUAL, 100L);

        ResourceFilterInput inputValue = translationSpec.convertAppObject(expectedAppValue);

        ResourceFilter actualAppValue = translationSpec.convertInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = ResourceFilterTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        ResourceFilterTranslationSpec translationSpec = new ResourceFilterTranslationSpec();

        assertEquals(ResourceFilter.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = ResourceFilterTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        ResourceFilterTranslationSpec translationSpec = new ResourceFilterTranslationSpec();

        assertEquals(ResourceFilterInput.class, translationSpec.getInputObjectClass());
    }
}
