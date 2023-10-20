package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.translationSpecs;

import gov.hhs.aspr.ms.gcm.plugins.stochastics.testsupport.TestRandomGeneratorId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.testsupport.input.TestRandomGeneratorIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain TestRandomGeneratorIdInput} and
 * {@linkplain TestRandomGeneratorId}
 */
public class TestRandomGeneratorIdTranslationSpec
        extends ProtobufTranslationSpec<TestRandomGeneratorIdInput, TestRandomGeneratorId> {

    @Override
    protected TestRandomGeneratorId convertInputObject(TestRandomGeneratorIdInput inputObject) {
        return TestRandomGeneratorId.valueOf(inputObject.name());
    }

    @Override
    protected TestRandomGeneratorIdInput convertAppObject(TestRandomGeneratorId appObject) {
        return TestRandomGeneratorIdInput.valueOf(appObject.name());
    }

    @Override
    public Class<TestRandomGeneratorId> getAppObjectClass() {
        return TestRandomGeneratorId.class;
    }

    @Override
    public Class<TestRandomGeneratorIdInput> getInputObjectClass() {
        return TestRandomGeneratorIdInput.class;
    }

}
