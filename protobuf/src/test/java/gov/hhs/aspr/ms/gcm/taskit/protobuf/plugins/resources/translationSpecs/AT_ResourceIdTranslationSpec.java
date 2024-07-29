package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.resources.support.ResourceId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.resources.testsupport.TestResourceId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translation.RegionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.support.input.ResourceIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translation.ResourcesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translation.specs.ResourceIdTranslationSpec;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_ResourceIdTranslationSpec {

    @Test
    @UnitTestConstructor(target = ResourceIdTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new ResourceIdTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testtranslateObject() {
        ProtobufTaskitEngine ProtobufTaskitEngine = IProtobufTaskitEngineBuilder()
                .addTranslator(ResourcesTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(PeopleTranslator.getTranslator())
                .addTranslator(RegionsTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .build();

        ResourceIdTranslationSpec translationSpec = new ResourceIdTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        ResourceId expectedAppValue = TestResourceId.RESOURCE_1;

        ResourceIdInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        ResourceId actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = ResourceIdTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        ResourceIdTranslationSpec translationSpec = new ResourceIdTranslationSpec();

        assertEquals(ResourceId.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = ResourceIdTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        ResourceIdTranslationSpec translationSpec = new ResourceIdTranslationSpec();

        assertEquals(ResourceIdInput.class, translationSpec.getInputObjectClass());
    }
}
