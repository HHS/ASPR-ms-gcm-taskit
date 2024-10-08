package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translation.specs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.testsupport.TestRegionPropertyId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.testsupport.input.TestRegionPropertyIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain TestRegionPropertyIdInput} and {@linkplain TestRegionPropertyId}
 */
public class TestRegionPropertyIdTranslationSpec
        extends ProtobufTranslationSpec<TestRegionPropertyIdInput, TestRegionPropertyId> {

    @Override
    protected TestRegionPropertyId translateInputObject(TestRegionPropertyIdInput inputObject) {
        return TestRegionPropertyId.valueOf(inputObject.name());
    }

    @Override
    protected TestRegionPropertyIdInput translateAppObject(TestRegionPropertyId appObject) {
        return TestRegionPropertyIdInput.valueOf(appObject.name());
    }

    @Override
    public Class<TestRegionPropertyId> getAppObjectClass() {
        return TestRegionPropertyId.class;
    }

    @Override
    public Class<TestRegionPropertyIdInput> getInputObjectClass() {
        return TestRegionPropertyIdInput.class;
    }

}
