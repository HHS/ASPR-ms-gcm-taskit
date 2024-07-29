package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.resources.testsupport.TestResourcePropertyId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translation.RegionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.testsupport.input.TestResourcePropertyIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translation.ResourcesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translation.specs.TestResourcePropertyIdTranslationSpec;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_TestResourcePropertyIdTranslationSpec {

    @Test
    @UnitTestConstructor(target = TestResourcePropertyIdTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new TestResourcePropertyIdTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testTranslateObject() {
        ProtobufTaskitEngine ProtobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslator(ResourcesTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(PeopleTranslator.getTranslator())
                .addTranslator(RegionsTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .build();

        TestResourcePropertyIdTranslationSpec translationSpec = new TestResourcePropertyIdTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        TestResourcePropertyId expectedAppValue = TestResourcePropertyId.ResourceProperty_1_1_BOOLEAN_MUTABLE;

        TestResourcePropertyIdInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        TestResourcePropertyId actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = TestResourcePropertyIdTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        TestResourcePropertyIdTranslationSpec translationSpec = new TestResourcePropertyIdTranslationSpec();

        assertEquals(TestResourcePropertyId.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = TestResourcePropertyIdTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        TestResourcePropertyIdTranslationSpec translationSpec = new TestResourcePropertyIdTranslationSpec();

        assertEquals(TestResourcePropertyIdInput.class, translationSpec.getInputObjectClass());
    }
}
