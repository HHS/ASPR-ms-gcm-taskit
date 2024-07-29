package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.Labeler;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.support.input.LabelerInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.TestLabeler;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.translation.specs.TestFilterTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.translation.specs.TestLabelerTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translation.PartitionsTranslator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_LabelerTranslationSpec {

    @Test
    @UnitTestConstructor(target = LabelerTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new LabelerTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testTranslateObject() {
        ProtobufTaskitEngine ProtobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslationSpec(new TestFilterTranslationSpec())
                .addTranslationSpec(new TestLabelerTranslationSpec())
                .addTranslator(PartitionsTranslator.getTranslator())
                .build();

        LabelerTranslationSpec translationSpec = new LabelerTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        Labeler expectedAppValue = new TestLabeler("test");

        LabelerInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        Labeler actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = LabelerTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        LabelerTranslationSpec translationSpec = new LabelerTranslationSpec();

        assertEquals(Labeler.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = LabelerTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        LabelerTranslationSpec translationSpec = new LabelerTranslationSpec();

        assertEquals(LabelerInput.class, translationSpec.getInputObjectClass());
    }
}
