package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.datamanagers.PartitionsPluginData;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.data.input.PartitionsPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.translation.specs.TestFilterTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.translation.specs.TestLabelerTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translation.PartitionsTranslator;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitError;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;
import gov.hhs.aspr.ms.util.errors.ContractException;

public class AT_PartitionsPluginDataTranslationSpec {

    @Test
    @UnitTestConstructor(target = PartitionsPluginDataTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new PartitionsPluginDataTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testTranslateObject() {
        ProtobufTaskitEngine protobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslationSpec(new TestFilterTranslationSpec())
                .addTranslationSpec(new TestLabelerTranslationSpec())
                .addTranslator(PartitionsTranslator.getTranslator())
                .build();

        PartitionsPluginDataTranslationSpec translationSpec = new PartitionsPluginDataTranslationSpec();
        translationSpec.init(protobufTaskitEngine);

        PartitionsPluginData expectedAppValue = PartitionsPluginData.builder().setRunContinuitySupport(true).build();

        PartitionsPluginDataInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        PartitionsPluginData actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
        assertEquals(expectedAppValue.toString(), actualAppValue.toString());

        expectedAppValue = PartitionsPluginData.builder().setRunContinuitySupport(false).build();

        inputValue = translationSpec.translateAppObject(expectedAppValue);
        actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
        assertEquals(expectedAppValue.toString(), actualAppValue.toString());

        // preconditions
        // version is not supported
        ContractException contractException = assertThrows(ContractException.class, () -> {
            translationSpec
                    .translateInputObject(PartitionsPluginDataInput.newBuilder().setVersion("badversion").build());
        });

        assertEquals(TaskitError.UNSUPPORTED_VERSION, contractException.getErrorType());
    }

    @Test
    @UnitTestMethod(target = PartitionsPluginDataTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        PartitionsPluginDataTranslationSpec translationSpec = new PartitionsPluginDataTranslationSpec();

        assertEquals(PartitionsPluginData.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = PartitionsPluginDataTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        PartitionsPluginDataTranslationSpec translationSpec = new PartitionsPluginDataTranslationSpec();

        assertEquals(PartitionsPluginDataInput.class, translationSpec.getInputObjectClass());
    }
}
