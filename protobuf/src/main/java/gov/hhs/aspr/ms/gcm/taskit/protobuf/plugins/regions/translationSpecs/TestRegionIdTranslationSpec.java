package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translationSpecs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.testsupport.TestRegionId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.testsupport.input.TestRegionIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain TestRegionIdInput} and {@linkplain TestRegionId}
 */
public class TestRegionIdTranslationSpec extends ProtobufTranslationSpec<TestRegionIdInput, TestRegionId> {

    @Override
    protected TestRegionId translateInputObject(TestRegionIdInput inputObject) {
        return TestRegionId.valueOf(inputObject.name());
    }

    @Override
    protected TestRegionIdInput translateAppObject(TestRegionId appObject) {
        return TestRegionIdInput.valueOf(appObject.name());
    }

    @Override
    public Class<TestRegionId> getAppObjectClass() {
        return TestRegionId.class;
    }

    @Override
    public Class<TestRegionIdInput> getInputObjectClass() {
        return TestRegionIdInput.class;
    }

}
