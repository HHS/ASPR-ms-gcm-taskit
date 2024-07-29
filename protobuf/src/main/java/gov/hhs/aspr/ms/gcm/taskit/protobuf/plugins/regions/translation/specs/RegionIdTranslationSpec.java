package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translation.specs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.support.RegionId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.support.input.RegionIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain RegionIdInput} and {@linkplain RegionId}
 */
public class RegionIdTranslationSpec extends ProtobufTranslationSpec<RegionIdInput, RegionId> {

    @Override
    protected RegionId translateInputObject(RegionIdInput inputObject) {
        return this.taskitEngine.getObjectFromAny(inputObject.getId());
    }

    @Override
    protected RegionIdInput translateAppObject(RegionId appObject) {
        return RegionIdInput.newBuilder().setId(this.taskitEngine.getAnyFromObject(appObject)).build();
    }

    @Override
    public Class<RegionId> getAppObjectClass() {
        return RegionId.class;
    }

    @Override
    public Class<RegionIdInput> getInputObjectClass() {
        return RegionIdInput.class;
    }

}
