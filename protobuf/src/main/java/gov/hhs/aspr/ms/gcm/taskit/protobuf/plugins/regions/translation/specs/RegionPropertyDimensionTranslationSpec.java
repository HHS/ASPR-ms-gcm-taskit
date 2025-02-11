package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translation.specs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.support.RegionId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.support.RegionPropertyDimensionData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.support.RegionPropertyId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.input.DimensionSingleValueInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.support.input.RegionIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.support.input.RegionPropertyDimensionDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.support.input.RegionPropertyIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.engine.TaskitObjectHelper;
import gov.hhs.aspr.ms.taskit.protobuf.objects.TaskitObjectInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

public class RegionPropertyDimensionTranslationSpec
        extends ProtobufTranslationSpec<RegionPropertyDimensionDataInput, RegionPropertyDimensionData> {
    @Override
    protected RegionPropertyDimensionData translateInputObject(RegionPropertyDimensionDataInput inputObject) {
        RegionPropertyDimensionData.Builder builder = RegionPropertyDimensionData.builder();

        RegionPropertyId globalPropertyId = this.taskitEngine.translateObject(inputObject.getRegionPropertyId());
        RegionId groupId = this.taskitEngine.translateObject(inputObject.getRegionId());

        builder.setRegionPropertyId(globalPropertyId).setRegionId(groupId);

        for (DimensionSingleValueInput dimDataInput : inputObject.getValuesList()) {
            String levelName = dimDataInput.getLevelName();
            Object value = TaskitObjectHelper.getValue(dimDataInput.getValue());

            builder.addValue(levelName, value);
        }

        return builder.build();
    }

    @Override
    protected RegionPropertyDimensionDataInput translateAppObject(RegionPropertyDimensionData appObject) {
        RegionPropertyDimensionDataInput.Builder builder = RegionPropertyDimensionDataInput.newBuilder();

        RegionPropertyIdInput globalPropertyIdInput = this.taskitEngine
                .translateObjectAsClassSafe(appObject.getRegionPropertyId(), RegionPropertyId.class);
        RegionIdInput groupIdInput = this.taskitEngine.translateObjectAsClassSafe(appObject.getRegionId(),
                RegionId.class);

        builder.setRegionPropertyId(globalPropertyIdInput).setRegionId(groupIdInput);

        for (String levelName : appObject.getLevelNames()) {
            int level = appObject.getLevel(levelName);
            Object value = appObject.getValue(level);

            TaskitObjectInput inValue = TaskitObjectHelper.getTaskitObjectInput(value, taskitEngine);

            DimensionSingleValueInput dimInput = DimensionSingleValueInput.newBuilder()
                    .setLevelName(levelName)
                    .setValue(inValue)
                    .build();

            builder.addValues(dimInput);
        }

        return builder.build();
    }

    @Override
    public Class<RegionPropertyDimensionData> getAppObjectClass() {
        return RegionPropertyDimensionData.class;
    }

    @Override
    public Class<RegionPropertyDimensionDataInput> getInputObjectClass() {
        return RegionPropertyDimensionDataInput.class;
    }
}
