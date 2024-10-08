package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.translation.specs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.personproperties.testsupport.TestPersonPropertyId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.testsupport.input.TestPersonPropertyIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain TestPersonPropertyIdInput} and {@linkplain TestPersonPropertyId}
 */
public class TestPersonPropertyIdTranslationSpec
        extends ProtobufTranslationSpec<TestPersonPropertyIdInput, TestPersonPropertyId> {

    @Override
    protected TestPersonPropertyId translateInputObject(TestPersonPropertyIdInput inputObject) {
        return TestPersonPropertyId.valueOf(inputObject.name());
    }

    @Override
    protected TestPersonPropertyIdInput translateAppObject(TestPersonPropertyId appObject) {
        return TestPersonPropertyIdInput.valueOf(appObject.name());
    }

    @Override
    public Class<TestPersonPropertyId> getAppObjectClass() {
        return TestPersonPropertyId.class;
    }

    @Override
    public Class<TestPersonPropertyIdInput> getInputObjectClass() {
        return TestPersonPropertyIdInput.class;
    }

}
