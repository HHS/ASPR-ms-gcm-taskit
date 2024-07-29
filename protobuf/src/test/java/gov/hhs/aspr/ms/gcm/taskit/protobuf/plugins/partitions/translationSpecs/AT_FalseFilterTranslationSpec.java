package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.filters.FalseFilter;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.support.filters.input.FalseFilterInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.translation.specs.TestFilterTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.translation.specs.TestLabelerTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translation.PartitionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translation.specs.FalseFilterTranslationSpec;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_FalseFilterTranslationSpec {

    @Test
    @UnitTestConstructor(target = FalseFilterTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new FalseFilterTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testTranslateObject() {
        ProtobufTaskitEngine ProtobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslationSpec(new TestFilterTranslationSpec())
                .addTranslationSpec(new TestLabelerTranslationSpec())
                .addTranslator(PartitionsTranslator.getTranslator())
                .build();

        FalseFilterTranslationSpec translationSpec = new FalseFilterTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        FalseFilter expectedAppValue = new FalseFilter();

        FalseFilterInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        FalseFilter actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = FalseFilterTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        FalseFilterTranslationSpec translationSpec = new FalseFilterTranslationSpec();

        assertEquals(FalseFilter.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = FalseFilterTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        FalseFilterTranslationSpec translationSpec = new FalseFilterTranslationSpec();

        assertEquals(FalseFilterInput.class, translationSpec.getInputObjectClass());
    }
}
