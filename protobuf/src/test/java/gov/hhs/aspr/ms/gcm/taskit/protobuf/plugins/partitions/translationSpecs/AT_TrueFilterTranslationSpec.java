package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.filters.TrueFilter;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.support.filters.input.TrueFilterInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.translation.specs.TestFilterTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.translation.specs.TestLabelerTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translation.PartitionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translation.specs.TrueFilterTranslationSpec;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_TrueFilterTranslationSpec {

    @Test
    @UnitTestConstructor(target = TrueFilterTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new TrueFilterTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testtranslateObject() {
        ProtobufTaskitEngine ProtobufTaskitEngine = IProtobufTaskitEngineBuilder()
                .addTranslationSpec(new TestFilterTranslationSpec())
                .addTranslationSpec(new TestLabelerTranslationSpec())
                .addTranslator(PartitionsTranslator.getTranslator())
                .build();

        TrueFilterTranslationSpec translationSpec = new TrueFilterTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        TrueFilter expectedAppValue = new TrueFilter();

        TrueFilterInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        TrueFilter actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = TrueFilterTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        TrueFilterTranslationSpec translationSpec = new TrueFilterTranslationSpec();

        assertEquals(TrueFilter.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = TrueFilterTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        TrueFilterTranslationSpec translationSpec = new TrueFilterTranslationSpec();

        assertEquals(TrueFilterInput.class, translationSpec.getInputObjectClass());
    }
}
