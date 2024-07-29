package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translationSpecs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.support.SimpleRegionPropertyId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.support.input.SimpleRegionPropertyIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain SimpleRegionPropertyIdInput} and
 * {@linkplain SimpleRegionPropertyId}
 */
public class SimpleRegionPropertyIdTranslationSpec
        extends ProtobufTranslationSpec<SimpleRegionPropertyIdInput, SimpleRegionPropertyId> {

    @Override
    protected SimpleRegionPropertyId translateInputObject(SimpleRegionPropertyIdInput inputObject) {
        return new SimpleRegionPropertyId(this.taskitEngine.getObjectFromAny(inputObject.getValue()));
    }

    @Override
    protected SimpleRegionPropertyIdInput translateAppObject(SimpleRegionPropertyId appObject) {
        return SimpleRegionPropertyIdInput.newBuilder()
                .setValue(this.taskitEngine.getAnyFromObject(appObject.getValue()))
                .build();
    }

    @Override
    public Class<SimpleRegionPropertyId> getAppObjectClass() {
        return SimpleRegionPropertyId.class;
    }

    @Override
    public Class<SimpleRegionPropertyIdInput> getInputObjectClass() {
        return SimpleRegionPropertyIdInput.class;
    }

}
