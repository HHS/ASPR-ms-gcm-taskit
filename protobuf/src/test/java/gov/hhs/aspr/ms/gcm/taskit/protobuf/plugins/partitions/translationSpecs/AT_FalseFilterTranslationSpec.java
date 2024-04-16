package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.filters.FalseFilter;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.PartitionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.support.filters.input.FalseFilterInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.translationSpecs.TestFilterTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.translationSpecs.TestLabelerTranslationSpec;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationEngine;
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
    public void testConvertObject() {
        ProtobufTranslationEngine protobufTranslationEngine = ProtobufTranslationEngine.builder()
                .addTranslationSpec(new TestFilterTranslationSpec())
                .addTranslationSpec(new TestLabelerTranslationSpec())
                .addTranslator(PartitionsTranslator.getTranslator())
                .build();

        FalseFilterTranslationSpec translationSpec = new FalseFilterTranslationSpec();
        translationSpec.init(protobufTranslationEngine);

        FalseFilter expectedAppValue = new FalseFilter();

        FalseFilterInput inputValue = translationSpec.convertAppObject(expectedAppValue);

        FalseFilter actualAppValue = translationSpec.convertInputObject(inputValue);

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
