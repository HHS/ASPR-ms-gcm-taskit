package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.PartitionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.TestFilter;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.input.TestFilterInput;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_TestFilterTranslationSpec {

    @Test
    @UnitTestConstructor(target = TestFilterTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new TestFilterTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testConvertObject() {
        ProtobufTranslationEngine protobufTranslationEngine = ProtobufTranslationEngine.builder()
                .addTranslationSpec(new TestFilterTranslationSpec())
                .addTranslationSpec(new TestLabelerTranslationSpec())
                .addTranslator(PartitionsTranslator.getTranslator())
                .build();

        TestFilterTranslationSpec translationSpec = new TestFilterTranslationSpec();
        translationSpec.init(protobufTranslationEngine);

        TestFilter expectedAppValue = new TestFilter(0);

        TestFilterInput inputValue = translationSpec.convertAppObject(expectedAppValue);

        TestFilter actualAppValue = translationSpec.convertInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = TestFilterTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        TestFilterTranslationSpec translationSpec = new TestFilterTranslationSpec();

        assertEquals(TestFilter.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = TestFilterTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        TestFilterTranslationSpec translationSpec = new TestFilterTranslationSpec();

        assertEquals(TestFilterInput.class, translationSpec.getInputObjectClass());
    }
}
