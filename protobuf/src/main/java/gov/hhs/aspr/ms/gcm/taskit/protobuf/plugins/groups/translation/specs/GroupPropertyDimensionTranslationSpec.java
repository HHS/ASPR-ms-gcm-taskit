package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translation.specs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.support.GroupId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.support.GroupPropertyDimensionData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.support.GroupPropertyId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.input.DimensionSingleValueInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.support.input.GroupPropertyDimensionDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.support.input.GroupPropertyIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.engine.TaskitObjectHelper;
import gov.hhs.aspr.ms.taskit.protobuf.objects.TaskitObjectInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

public class GroupPropertyDimensionTranslationSpec
        extends ProtobufTranslationSpec<GroupPropertyDimensionDataInput, GroupPropertyDimensionData> {

    @Override
    protected GroupPropertyDimensionData translateInputObject(GroupPropertyDimensionDataInput inputObject) {
        GroupPropertyDimensionData.Builder builder = GroupPropertyDimensionData.builder();

        GroupPropertyId globalPropertyId = this.taskitEngine.translateObject(inputObject.getGroupPropertyId());
        GroupId groupId = new GroupId(inputObject.getGId());

        builder.setGroupPropertyId(globalPropertyId).setGroupId(groupId);

        for (DimensionSingleValueInput dimDataInput : inputObject.getValuesList()) {
            String levelName = dimDataInput.getLevelName();
            Object value = TaskitObjectHelper.getValue(dimDataInput.getValue());

            builder.addValue(levelName, value);
        }

        return builder.build();
    }

    @Override
    protected GroupPropertyDimensionDataInput translateAppObject(GroupPropertyDimensionData appObject) {
        GroupPropertyDimensionDataInput.Builder builder = GroupPropertyDimensionDataInput.newBuilder();

        GroupPropertyIdInput globalPropertyIdInput = this.taskitEngine
                .translateObjectAsClassSafe(appObject.getGroupPropertyId(), GroupPropertyId.class);

        builder.setGroupPropertyId(globalPropertyIdInput).setGId(appObject.getGroupId().getValue());

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
    public Class<GroupPropertyDimensionData> getAppObjectClass() {
        return GroupPropertyDimensionData.class;
    }

    @Override
    public Class<GroupPropertyDimensionDataInput> getInputObjectClass() {
        return GroupPropertyDimensionDataInput.class;
    }

}
