package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.testsupport.attributes.support.AttributeId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.testsupport.attributes.support.TestAttributeId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.support.attributes.input.AttributeIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.translation.specs.TestFilterTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.translation.specs.TestLabelerTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translation.PartitionsTranslator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_AttributeIdTranslationSpec {

    @Test
    @UnitTestConstructor(target = AttributeIdTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new AttributeIdTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testTranslateObject() {
        ProtobufTaskitEngine protobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslationSpec(new TestFilterTranslationSpec())
                .addTranslationSpec(new TestLabelerTranslationSpec())
                .addTranslator(PartitionsTranslator.getTranslator())
                .build();

        AttributeIdTranslationSpec translationSpec = new AttributeIdTranslationSpec();
        translationSpec.init(protobufTaskitEngine);

        AttributeId expectedAppValue = TestAttributeId.BOOLEAN_0;

        AttributeIdInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        AttributeId actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = AttributeIdTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        AttributeIdTranslationSpec translationSpec = new AttributeIdTranslationSpec();

        assertEquals(AttributeId.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = AttributeIdTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        AttributeIdTranslationSpec translationSpec = new AttributeIdTranslationSpec();

        assertEquals(AttributeIdInput.class, translationSpec.getInputObjectClass());
    }
}
