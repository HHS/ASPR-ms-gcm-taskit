package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translation.specs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.support.SimpleRegionId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.support.input.SimpleRegionIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain SimpleRegionIdInput} and {@linkplain SimpleRegionId}
 */
public class SimpleRegionIdTranslationSpec extends ProtobufTranslationSpec<SimpleRegionIdInput, SimpleRegionId> {

    @Override
    protected SimpleRegionId translateInputObject(SimpleRegionIdInput inputObject) {
        return new SimpleRegionId(this.taskitEngine.getObjectFromAny(inputObject.getValue()));
    }

    @Override
    protected SimpleRegionIdInput translateAppObject(SimpleRegionId appObject) {
        return SimpleRegionIdInput.newBuilder()
                .setValue(this.taskitEngine.getAnyFromObject(appObject.getValue()))
                .build();
    }

    @Override
    public Class<SimpleRegionId> getAppObjectClass() {
        return SimpleRegionId.class;
    }

    @Override
    public Class<SimpleRegionIdInput> getInputObjectClass() {
        return SimpleRegionIdInput.class;
    }

}
