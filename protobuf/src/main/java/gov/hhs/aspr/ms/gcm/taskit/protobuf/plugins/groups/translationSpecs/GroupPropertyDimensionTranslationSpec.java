package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translationSpecs;

import com.google.protobuf.Any;

import gov.hhs.aspr.ms.gcm.plugins.groups.support.GroupId;
import gov.hhs.aspr.ms.gcm.plugins.groups.support.GroupPropertyDimension;
import gov.hhs.aspr.ms.gcm.plugins.groups.support.GroupPropertyId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.support.input.GroupPropertyDimensionInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.support.input.GroupPropertyIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationSpec;

public class GroupPropertyDimensionTranslationSpec
        extends ProtobufTranslationSpec<GroupPropertyDimensionInput, GroupPropertyDimension> {

    @Override
    protected GroupPropertyDimension convertInputObject(GroupPropertyDimensionInput inputObject) {
        GroupPropertyDimension.Builder builder = GroupPropertyDimension.builder();

        GroupPropertyId globalPropertyId = this.translationEngine.convertObject(inputObject.getGroupPropertyId());
        GroupId groupId = new GroupId(inputObject.getGId());

        builder.setGroupPropertyId(globalPropertyId).setGroupId(groupId);

        for (Any anyValue : inputObject.getValuesList()) {
            Object value = this.translationEngine.getObjectFromAny(anyValue);
            builder.addValue(value);
        }

        return builder.build();
    }

    @Override
    protected GroupPropertyDimensionInput convertAppObject(GroupPropertyDimension appObject) {
        GroupPropertyDimensionInput.Builder builder = GroupPropertyDimensionInput.newBuilder();

        GroupPropertyIdInput globalPropertyIdInput = this.translationEngine
                .convertObjectAsSafeClass(appObject.getGroupPropertyId(), GroupPropertyId.class);

        builder.setGroupPropertyId(globalPropertyIdInput).setGId(appObject.getGroupId().getValue());

        for (Object objValue : appObject.getValues()) {
            builder.addValues(this.translationEngine.getAnyFromObject(objValue));
        }

        return builder.build();
    }

    @Override
    public Class<GroupPropertyDimension> getAppObjectClass() {
        return GroupPropertyDimension.class;
    }

    @Override
    public Class<GroupPropertyDimensionInput> getInputObjectClass() {
        return GroupPropertyDimensionInput.class;
    }

}
