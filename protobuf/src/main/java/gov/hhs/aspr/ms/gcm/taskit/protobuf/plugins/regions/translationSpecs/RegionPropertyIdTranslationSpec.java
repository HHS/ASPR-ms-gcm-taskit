package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translationSpecs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.support.RegionPropertyId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.support.input.RegionPropertyIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain RegionPropertyIdInput} and {@linkplain RegionPropertyId}
 */
public class RegionPropertyIdTranslationSpec extends ProtobufTranslationSpec<RegionPropertyIdInput, RegionPropertyId> {

    @Override
    protected RegionPropertyId translateInputObject(RegionPropertyIdInput inputObject) {
        return this.taskitEngine.getObjectFromAny(inputObject.getId());
    }

    @Override
    protected RegionPropertyIdInput translateAppObject(RegionPropertyId appObject) {
        return RegionPropertyIdInput.newBuilder().setId(this.taskitEngine.getAnyFromObject(appObject)).build();
    }

    @Override
    public Class<RegionPropertyId> getAppObjectClass() {
        return RegionPropertyId.class;
    }

    @Override
    public Class<RegionPropertyIdInput> getInputObjectClass() {
        return RegionPropertyIdInput.class;
    }

}
