package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.translation.specs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.globalproperties.testsupport.TestGlobalPropertyId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.testsupport.input.TestGlobalPropertyIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain TestGlobalPropertyIdInput} and {@linkplain TestGlobalPropertyId}
 */
public class TestGlobalPropertyIdTranslationSpec
        extends ProtobufTranslationSpec<TestGlobalPropertyIdInput, TestGlobalPropertyId> {

    @Override
    protected TestGlobalPropertyId translateInputObject(TestGlobalPropertyIdInput inputObject) {
        return TestGlobalPropertyId.valueOf(inputObject.name());
    }

    @Override
    protected TestGlobalPropertyIdInput translateAppObject(TestGlobalPropertyId appObject) {
        return TestGlobalPropertyIdInput.valueOf(appObject.name());
    }

    @Override
    public Class<TestGlobalPropertyId> getAppObjectClass() {
        return TestGlobalPropertyId.class;
    }

    @Override
    public Class<TestGlobalPropertyIdInput> getInputObjectClass() {
        return TestGlobalPropertyIdInput.class;
    }

}
