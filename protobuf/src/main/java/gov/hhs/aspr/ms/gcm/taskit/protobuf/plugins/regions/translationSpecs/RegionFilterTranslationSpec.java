package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translationSpecs;

import java.util.ArrayList;
import java.util.List;

import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.support.RegionFilter;
import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.support.RegionId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.support.input.RegionFilterInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.support.input.RegionIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain RegionFilterInput} and {@linkplain RegionFilter}
 */
public class RegionFilterTranslationSpec extends ProtobufTranslationSpec<RegionFilterInput, RegionFilter> {

    @Override
    protected RegionFilter translateInputObject(RegionFilterInput inputObject) {
        List<RegionId> regionIds = new ArrayList<>();

        for (RegionIdInput regionIdInput : inputObject.getRegionIdsList()) {
            regionIds.add(this.taskitEngine.translateObject(regionIdInput));
        }
        return new RegionFilter(regionIds.toArray(new RegionId[0]));
    }

    @Override
    protected RegionFilterInput translateAppObject(RegionFilter appObject) {
        RegionFilterInput.Builder builder = RegionFilterInput.newBuilder();

        for (RegionId regionId : appObject.getRegionIds()) {
            RegionIdInput regionIdInput = this.taskitEngine.translateObjectAsClassSafe(regionId, RegionId.class);
            builder.addRegionIds(regionIdInput);
        }

        return builder.build();
    }

    @Override
    public Class<RegionFilter> getAppObjectClass() {
        return RegionFilter.class;
    }

    @Override
    public Class<RegionFilterInput> getInputObjectClass() {
        return RegionFilterInput.class;
    }

}
