package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.Labeler;
import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.Partition;
import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.filters.Filter;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.support.input.PartitionInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.TestFilter;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.TestLabeler;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.translation.specs.TestFilterTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.translation.specs.TestLabelerTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translation.PartitionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translation.specs.PartitionTranslationSpec;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_PartitionTranslationSpec {

    @Test
    @UnitTestConstructor(target = PartitionTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new PartitionTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testTranslateObject() {
        ProtobufTaskitEngine ProtobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslationSpec(new TestFilterTranslationSpec())
                .addTranslationSpec(new TestLabelerTranslationSpec())
                .addTranslator(PartitionsTranslator.getTranslator())
                .build();

        PartitionTranslationSpec translationSpec = new PartitionTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        Filter partitionFilter = new TestFilter(0);
        Labeler partitionLabeler = new TestLabeler("Test");

        Partition expectedAppValue = Partition.builder()
                .setFilter(partitionFilter)
                .addLabeler(partitionLabeler)
                .build();

        PartitionInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        Partition actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);

        expectedAppValue = Partition.builder().addLabeler(partitionLabeler).build();

        inputValue = translationSpec.translateAppObject(expectedAppValue);

        actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = PartitionTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        PartitionTranslationSpec translationSpec = new PartitionTranslationSpec();

        assertEquals(Partition.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = PartitionTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        PartitionTranslationSpec translationSpec = new PartitionTranslationSpec();

        assertEquals(PartitionInput.class, translationSpec.getInputObjectClass());
    }
}
