package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.testsupport.attributes.support.TestAttributeId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.PartitionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.input.TestAttributeIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.translationSpecs.TestFilterTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.translationSpecs.TestLabelerTranslationSpec;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_TestAttributeIdTranslationSpec {

    @Test
    @UnitTestConstructor(target = TestAttributeIdTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new TestAttributeIdTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testConvertObject() {
        ProtobufTranslationEngine protobufTranslationEngine = ProtobufTranslationEngine.builder()
                .addTranslationSpec(new TestFilterTranslationSpec())
                .addTranslationSpec(new TestLabelerTranslationSpec())
                .addTranslator(PartitionsTranslator.getTranslator())
                .build();

        TestAttributeIdTranslationSpec translationSpec = new TestAttributeIdTranslationSpec();
        translationSpec.init(protobufTranslationEngine);

        TestAttributeId expectedAppValue = TestAttributeId.BOOLEAN_0;

        TestAttributeIdInput inputValue = translationSpec.convertAppObject(expectedAppValue);

        TestAttributeId actualAppValue = translationSpec.convertInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = TestAttributeIdTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        TestAttributeIdTranslationSpec translationSpec = new TestAttributeIdTranslationSpec();

        assertEquals(TestAttributeId.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = TestAttributeIdTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        TestAttributeIdTranslationSpec translationSpec = new TestAttributeIdTranslationSpec();

        assertEquals(TestAttributeIdInput.class, translationSpec.getInputObjectClass());
    }
}
