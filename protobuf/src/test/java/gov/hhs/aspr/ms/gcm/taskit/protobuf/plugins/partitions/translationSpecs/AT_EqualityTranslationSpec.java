package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.Equality;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.PartitionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.support.input.EqualityInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.translation.specs.TestFilterTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.translation.specs.TestLabelerTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translation.specs.EqualityTranslationSpec;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_EqualityTranslationSpec {

    @Test
    @UnitTestConstructor(target = EqualityTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new EqualityTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testtranslateObject() {
        ProtobufTaskitEngine ProtobufTaskitEngine = IProtobufTaskitEngineBuilder()
                .addTranslationSpec(new TestFilterTranslationSpec())
                .addTranslationSpec(new TestLabelerTranslationSpec())
                .addTranslator(PartitionsTranslator.getTranslator())
                .build();

        EqualityTranslationSpec translationSpec = new EqualityTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        Equality expectedAppValue = Equality.EQUAL;

        EqualityInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        Equality actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = EqualityTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        EqualityTranslationSpec translationSpec = new EqualityTranslationSpec();

        assertEquals(Equality.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = EqualityTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        EqualityTranslationSpec translationSpec = new EqualityTranslationSpec();

        assertEquals(EqualityInput.class, translationSpec.getInputObjectClass());
    }
}
