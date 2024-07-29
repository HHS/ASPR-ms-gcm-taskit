package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.resources.testsupport.TestResourceId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translation.RegionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.testsupport.input.TestResourceIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translation.ResourcesTranslator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_TestResourceIdTranslationSpec {

    @Test
    @UnitTestConstructor(target = TestResourceIdTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new TestResourceIdTranslationSpec());
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

        TestResourceIdTranslationSpec translationSpec = new TestResourceIdTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        TestResourceId expectedAppValue = TestResourceId.RESOURCE_1;

        TestResourceIdInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        TestResourceId actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = TestResourceIdTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        TestResourceIdTranslationSpec translationSpec = new TestResourceIdTranslationSpec();

        assertEquals(TestResourceId.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = TestResourceIdTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        TestResourceIdTranslationSpec translationSpec = new TestResourceIdTranslationSpec();

        assertEquals(TestResourceIdInput.class, translationSpec.getInputObjectClass());
    }
}
