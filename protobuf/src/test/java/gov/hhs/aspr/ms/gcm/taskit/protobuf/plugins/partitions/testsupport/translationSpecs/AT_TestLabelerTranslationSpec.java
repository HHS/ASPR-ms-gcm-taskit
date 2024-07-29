package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.TestLabeler;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.input.TestLabelerInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.translation.specs.TestFilterTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.translation.specs.TestLabelerTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translation.PartitionsTranslator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_TestLabelerTranslationSpec {

    @Test
    @UnitTestConstructor(target = TestLabelerTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new TestLabelerTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testtranslateObject() {
        ProtobufTaskitEngine ProtobufTaskitEngine = IProtobufTaskitEngineBuilder()
                .addTranslationSpec(new TestFilterTranslationSpec())
                .addTranslationSpec(new TestLabelerTranslationSpec())
                .addTranslator(PartitionsTranslator.getTranslator())
                .build();

        TestLabelerTranslationSpec translationSpec = new TestLabelerTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        TestLabeler expectedAppValue = new TestLabeler("test");

        TestLabelerInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        TestLabeler actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = TestLabelerTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        TestLabelerTranslationSpec translationSpec = new TestLabelerTranslationSpec();

        assertEquals(TestLabeler.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = TestLabelerTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        TestLabelerTranslationSpec translationSpec = new TestLabelerTranslationSpec();

        assertEquals(TestLabelerInput.class, translationSpec.getInputObjectClass());
    }
}
