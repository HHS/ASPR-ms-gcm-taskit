package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translationSpecs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.materials.testsupport.TestMaterialsProducerId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.testsupport.input.TestMaterialsProducerIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain TestMaterialsProducerIdInput} and
 * {@linkplain TestMaterialsProducerId}
 */
public class TestMaterialsProducerIdTranslationSpec
        extends ProtobufTranslationSpec<TestMaterialsProducerIdInput, TestMaterialsProducerId> {

    @Override
    protected TestMaterialsProducerId translateInputObject(TestMaterialsProducerIdInput inputObject) {
        return TestMaterialsProducerId.valueOf(inputObject.name());
    }

    @Override
    protected TestMaterialsProducerIdInput translateAppObject(TestMaterialsProducerId appObject) {
        return TestMaterialsProducerIdInput.valueOf(appObject.name());
    }

    @Override
    public Class<TestMaterialsProducerId> getAppObjectClass() {
        return TestMaterialsProducerId.class;
    }

    @Override
    public Class<TestMaterialsProducerIdInput> getInputObjectClass() {
        return TestMaterialsProducerIdInput.class;
    }

}
