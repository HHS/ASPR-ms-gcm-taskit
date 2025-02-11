package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translation.specs;

import com.google.protobuf.Any;

import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.support.RegionId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.support.RegionPropertyDimension;
import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.support.RegionPropertyDimensionData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.support.RegionPropertyId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.support.input.RegionIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.support.input.RegionPropertyDimensionInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.support.input.RegionPropertyIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

public class RegionPropertyDimensionTranslationSpec
        extends ProtobufTranslationSpec<RegionPropertyDimensionInput, RegionPropertyDimensionData> {
    @Override
    protected RegionPropertyDimensionData translateInputObject(RegionPropertyDimensionInput inputObject) {
        RegionPropertyDimensionData.Builder builder = RegionPropertyDimensionData.builder();

        RegionPropertyId globalPropertyId = this.taskitEngine.translateObject(inputObject.getRegionPropertyId());
        RegionId groupId = this.taskitEngine.translateObject(inputObject.getRegionId());

        builder.setRegionPropertyId(globalPropertyId).setRegionId(groupId);

        for (Any anyValue : inputObject.getValuesList()) {
            Object value = this.taskitEngine.getObjectFromAny(anyValue);
            builder.addValue(value);
        }

        return builder.build();
    }

    @Override
    protected RegionPropertyDimensionInput translateAppObject(RegionPropertyDimensionData appObject) {
        RegionPropertyDimensionInput.Builder builder = RegionPropertyDimensionInput.newBuilder();

        RegionPropertyIdInput globalPropertyIdInput = this.taskitEngine
                .translateObjectAsClassSafe(appObject.getRegionPropertyId(), RegionPropertyId.class);
        RegionIdInput groupIdInput = this.taskitEngine.translateObjectAsClassSafe(appObject.getRegionId(),
                RegionId.class);

        builder.setRegionPropertyId(globalPropertyIdInput).setRegionId(groupIdInput);

        for (Object objValue : appObject.getValues()) {
            builder.addValues(this.taskitEngine.getAnyFromObject(objValue));
        }

        return builder.build();
    }

    @Override
    public Class<RegionPropertyDimensionData> getAppObjectClass() {
        return RegionPropertyDimensionData.class;
    }

    @Override
    public Class<RegionPropertyDimensionInput> getInputObjectClass() {
        return RegionPropertyDimensionInput.class;
    }
}
