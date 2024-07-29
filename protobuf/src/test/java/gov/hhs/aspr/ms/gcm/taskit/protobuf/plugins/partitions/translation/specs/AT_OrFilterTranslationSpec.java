package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.filters.Filter;
import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.filters.OrFilter;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.support.filters.input.OrFilterInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.TestFilter;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.translation.specs.TestFilterTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.translation.specs.TestLabelerTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translation.PartitionsTranslator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_OrFilterTranslationSpec {

    @Test
    @UnitTestConstructor(target = OrFilterTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new OrFilterTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testTranslateObject() {
        ProtobufTaskitEngine ProtobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslationSpec(new TestFilterTranslationSpec())
                .addTranslationSpec(new TestLabelerTranslationSpec())
                .addTranslator(PartitionsTranslator.getTranslator())
                .build();

        OrFilterTranslationSpec translationSpec = new OrFilterTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        Filter filterA = new TestFilter(0);
        Filter filterB = new TestFilter(1);

        OrFilter expectedAppValue = new OrFilter(filterA, filterB);

        OrFilterInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        OrFilter actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = OrFilterTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        OrFilterTranslationSpec translationSpec = new OrFilterTranslationSpec();

        assertEquals(OrFilter.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = OrFilterTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        OrFilterTranslationSpec translationSpec = new OrFilterTranslationSpec();

        assertEquals(OrFilterInput.class, translationSpec.getInputObjectClass());
    }
}
