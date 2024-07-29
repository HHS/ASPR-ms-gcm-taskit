package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.resources.support.ResourceInitialization;
import gov.hhs.aspr.ms.gcm.simulation.plugins.resources.testsupport.TestResourceId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translation.RegionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.support.input.ResourceInitializationInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translation.ResourcesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translation.specs.ResourceInitializationTranslationSpec;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_ResourceInitializationTranslationSpec {

    @Test
    @UnitTestConstructor(target = ResourceInitializationTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new ResourceInitializationTranslationSpec());
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

        ResourceInitializationTranslationSpec translationSpec = new ResourceInitializationTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        ResourceInitialization expectedAppValue = new ResourceInitialization(TestResourceId.RESOURCE_1, 100L);

        ResourceInitializationInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        ResourceInitialization actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = ResourceInitializationTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        ResourceInitializationTranslationSpec translationSpec = new ResourceInitializationTranslationSpec();

        assertEquals(ResourceInitialization.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = ResourceInitializationTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        ResourceInitializationTranslationSpec translationSpec = new ResourceInitializationTranslationSpec();

        assertEquals(ResourceInitializationInput.class, translationSpec.getInputObjectClass());
    }
}
