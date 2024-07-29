package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translation.specs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.resources.testsupport.TestResourceId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.testsupport.input.TestResourceIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain TestResourceIdInput} and {@linkplain TestResourceId}
 */
public class TestResourceIdTranslationSpec extends ProtobufTranslationSpec<TestResourceIdInput, TestResourceId> {

    @Override
    protected TestResourceId translateInputObject(TestResourceIdInput inputObject) {
        return TestResourceId.valueOf(inputObject.name());
    }

    @Override
    protected TestResourceIdInput translateAppObject(TestResourceId appObject) {
        return TestResourceIdInput.valueOf(appObject.name());
    }

    @Override
    public Class<TestResourceId> getAppObjectClass() {
        return TestResourceId.class;
    }

    @Override
    public Class<TestResourceIdInput> getInputObjectClass() {
        return TestResourceIdInput.class;
    }

}
