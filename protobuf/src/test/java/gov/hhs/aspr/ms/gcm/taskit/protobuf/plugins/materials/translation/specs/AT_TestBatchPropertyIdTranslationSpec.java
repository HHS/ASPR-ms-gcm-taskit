package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.materials.testsupport.TestBatchPropertyId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.testsupport.input.TestBatchPropertyIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translation.MaterialsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translation.RegionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translation.ResourcesTranslator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_TestBatchPropertyIdTranslationSpec {

    @Test
    @UnitTestConstructor(target = TestBatchPropertyIdTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new TestBatchPropertyIdTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testTranslateObject() {
        ProtobufTaskitEngine protobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslator(MaterialsTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(ResourcesTranslator.getTranslator())
                .addTranslator(RegionsTranslator.getTranslator())
                .addTranslator(PeopleTranslator.getTranslator())
                .build();

        TestBatchPropertyIdTranslationSpec translationSpec = new TestBatchPropertyIdTranslationSpec();
        translationSpec.init(protobufTaskitEngine);

        TestBatchPropertyId expectedAppValue = TestBatchPropertyId.BATCH_PROPERTY_1_1_BOOLEAN_IMMUTABLE_NO_TRACK;

        TestBatchPropertyIdInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        TestBatchPropertyId actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = TestBatchPropertyIdTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        TestBatchPropertyIdTranslationSpec translationSpec = new TestBatchPropertyIdTranslationSpec();

        assertEquals(TestBatchPropertyId.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = TestBatchPropertyIdTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        TestBatchPropertyIdTranslationSpec translationSpec = new TestBatchPropertyIdTranslationSpec();

        assertEquals(TestBatchPropertyIdInput.class, translationSpec.getInputObjectClass());
    }
}
