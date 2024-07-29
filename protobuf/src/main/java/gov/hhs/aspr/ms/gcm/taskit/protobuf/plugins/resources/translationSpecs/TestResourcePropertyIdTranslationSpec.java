package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translationSpecs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.resources.testsupport.TestResourcePropertyId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.testsupport.input.TestResourcePropertyIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain TestResourcePropertyIdInput} and
 * {@linkplain TestResourcePropertyId}
 */
public class TestResourcePropertyIdTranslationSpec
        extends ProtobufTranslationSpec<TestResourcePropertyIdInput, TestResourcePropertyId> {

    @Override
    protected TestResourcePropertyId translateInputObject(TestResourcePropertyIdInput inputObject) {
        return TestResourcePropertyId.valueOf(inputObject.name());
    }

    @Override
    protected TestResourcePropertyIdInput translateAppObject(TestResourcePropertyId appObject) {
        return TestResourcePropertyIdInput.valueOf(appObject.name());
    }

    @Override
    public Class<TestResourcePropertyId> getAppObjectClass() {
        return TestResourcePropertyId.class;
    }

    @Override
    public Class<TestResourcePropertyIdInput> getInputObjectClass() {
        return TestResourcePropertyIdInput.class;
    }

}
