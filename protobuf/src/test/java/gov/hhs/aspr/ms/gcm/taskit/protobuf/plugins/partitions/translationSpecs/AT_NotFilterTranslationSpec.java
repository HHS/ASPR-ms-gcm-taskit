package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.filters.Filter;
import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.filters.NotFilter;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.support.filters.input.NotFilterInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.TestFilter;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.translation.specs.TestFilterTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.translation.specs.TestLabelerTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translation.PartitionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translation.specs.NotFilterTranslationSpec;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_NotFilterTranslationSpec {

    @Test
    @UnitTestConstructor(target = NotFilterTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new NotFilterTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testtranslateObject() {
        ProtobufTaskitEngine ProtobufTaskitEngine = IProtobufTaskitEngineBuilder()
                .addTranslationSpec(new TestFilterTranslationSpec())
                .addTranslationSpec(new TestLabelerTranslationSpec())
                .addTranslator(PartitionsTranslator.getTranslator())
                .build();

        NotFilterTranslationSpec translationSpec = new NotFilterTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        Filter filterA = new TestFilter(0);

        NotFilter expectedAppValue = new NotFilter(filterA);

        NotFilterInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        NotFilter actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = NotFilterTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        NotFilterTranslationSpec translationSpec = new NotFilterTranslationSpec();

        assertEquals(NotFilter.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = NotFilterTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        NotFilterTranslationSpec translationSpec = new NotFilterTranslationSpec();

        assertEquals(NotFilterInput.class, translationSpec.getInputObjectClass());
    }
}
