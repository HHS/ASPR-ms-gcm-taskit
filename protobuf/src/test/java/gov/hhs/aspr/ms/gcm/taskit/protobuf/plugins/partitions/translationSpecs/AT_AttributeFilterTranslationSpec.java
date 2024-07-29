package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.Equality;
import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.testsupport.attributes.support.AttributeFilter;
import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.testsupport.attributes.support.TestAttributeId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.support.attributes.input.AttributeFilterInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.translation.specs.TestFilterTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.translation.specs.TestLabelerTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translation.PartitionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translation.specs.AttributeFilterTranslationSpec;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_AttributeFilterTranslationSpec {

    @Test
    @UnitTestConstructor(target = AttributeFilterTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new AttributeFilterTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testtranslateObject() {
        ProtobufTaskitEngine ProtobufTaskitEngine = IProtobufTaskitEngineBuilder()
                .addTranslationSpec(new TestFilterTranslationSpec())
                .addTranslationSpec(new TestLabelerTranslationSpec())
                .addTranslator(PartitionsTranslator.getTranslator())
                .build();

        AttributeFilterTranslationSpec translationSpec = new AttributeFilterTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        AttributeFilter expectedAppValue = new AttributeFilter(TestAttributeId.BOOLEAN_0, Equality.EQUAL, "test");

        AttributeFilterInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        AttributeFilter actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = AttributeFilterTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        AttributeFilterTranslationSpec translationSpec = new AttributeFilterTranslationSpec();

        assertEquals(AttributeFilter.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = AttributeFilterTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        AttributeFilterTranslationSpec translationSpec = new AttributeFilterTranslationSpec();

        assertEquals(AttributeFilterInput.class, translationSpec.getInputObjectClass());
    }
}
