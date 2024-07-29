package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translationSpecs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.testsupport.attributes.support.TestAttributeId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.input.TestAttributeIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain TestAttributeIdInput} and {@linkplain TestAttributeId}
 */
public class TestAttributeIdTranslationSpec extends ProtobufTranslationSpec<TestAttributeIdInput, TestAttributeId> {

    @Override
    protected TestAttributeId translateInputObject(TestAttributeIdInput inputObject) {
        return TestAttributeId.valueOf(inputObject.name());
    }

    @Override
    protected TestAttributeIdInput translateAppObject(TestAttributeId appObject) {
        return TestAttributeIdInput.valueOf(appObject.name());
    }

    @Override
    public Class<TestAttributeId> getAppObjectClass() {
        return TestAttributeId.class;
    }

    @Override
    public Class<TestAttributeIdInput> getInputObjectClass() {
        return TestAttributeIdInput.class;
    }

}
