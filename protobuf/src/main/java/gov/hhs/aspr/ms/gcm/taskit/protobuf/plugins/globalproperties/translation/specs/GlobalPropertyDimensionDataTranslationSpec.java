package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.translation.specs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.globalproperties.support.GlobalPropertyDimensionData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.globalproperties.support.GlobalPropertyId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.input.DimensionSingleValueInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.support.input.GlobalPropertyDimensionDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.support.input.GlobalPropertyIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.engine.TaskitObjectHelper;
import gov.hhs.aspr.ms.taskit.protobuf.objects.TaskitObjectInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

public class GlobalPropertyDimensionDataTranslationSpec
        extends ProtobufTranslationSpec<GlobalPropertyDimensionDataInput, GlobalPropertyDimensionData> {

    @Override
    protected GlobalPropertyDimensionData translateInputObject(GlobalPropertyDimensionDataInput inputObject) {
        GlobalPropertyDimensionData.Builder builder = GlobalPropertyDimensionData.builder();

        GlobalPropertyId globalPropertyId = this.taskitEngine.translateObject(inputObject.getGlobalPropertyId());

        builder.setGlobalPropertyId(globalPropertyId).setAssignmentTime(inputObject.getAssignmentTime());

        for (DimensionSingleValueInput dimDataInput : inputObject.getValuesList()) {
            String levelName = dimDataInput.getLevelName();
            Object value = TaskitObjectHelper.getValue(dimDataInput.getValue());

            builder.addValue(levelName, value);
        }

        return builder.build();
    }

    @Override
    protected GlobalPropertyDimensionDataInput translateAppObject(GlobalPropertyDimensionData appObject) {
        GlobalPropertyDimensionDataInput.Builder builder = GlobalPropertyDimensionDataInput.newBuilder();

        GlobalPropertyIdInput globalPropertyIdInput = this.taskitEngine
                .translateObjectAsClassSafe(appObject.getGlobalPropertyId(), GlobalPropertyId.class);

        builder.setGlobalPropertyId(globalPropertyIdInput).setAssignmentTime(appObject.getAssignmentTime());

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
    public Class<GlobalPropertyDimensionData> getAppObjectClass() {
        return GlobalPropertyDimensionData.class;
    }

    @Override
    public Class<GlobalPropertyDimensionDataInput> getInputObjectClass() {
        return GlobalPropertyDimensionDataInput.class;
    }

}
