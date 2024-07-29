package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translation.specs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.support.GroupTypeId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.support.GroupsForPersonAndGroupTypeFilter;
import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.Equality;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.support.input.GroupTypeIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.support.input.GroupsForPersonAndGroupTypeFilterInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.support.input.EqualityInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

public class GroupsForPersonAndGroupTypeFilterTranslationSpec
        extends ProtobufTranslationSpec<GroupsForPersonAndGroupTypeFilterInput, GroupsForPersonAndGroupTypeFilter> {

    @Override
    protected GroupsForPersonAndGroupTypeFilter translateInputObject(
            GroupsForPersonAndGroupTypeFilterInput inputObject) {
        GroupTypeId groupTypeId = this.taskitEngine.translateObject(inputObject.getGroupTypeId());
        Equality equality = this.taskitEngine.translateObject(inputObject.getEquality());
        int groupCount = inputObject.getGroupCount();

        return new GroupsForPersonAndGroupTypeFilter(groupTypeId, equality, groupCount);
    }

    @Override
    protected GroupsForPersonAndGroupTypeFilterInput translateAppObject(GroupsForPersonAndGroupTypeFilter appObject) {
        GroupTypeIdInput groupTypeIdInput = this.taskitEngine.translateObjectAsClassSafe(appObject.getGroupTypeId(),
                GroupTypeId.class);
        EqualityInput equalityInput = this.taskitEngine.translateObjectAsClassSafe(appObject.getEquality(),
                Equality.class);
        int groupCount = appObject.getGroupCount();

        return GroupsForPersonAndGroupTypeFilterInput.newBuilder()
                .setEquality(equalityInput)
                .setGroupTypeId(groupTypeIdInput)
                .setGroupCount(groupCount)
                .build();
    }

    @Override
    public Class<GroupsForPersonAndGroupTypeFilter> getAppObjectClass() {
        return GroupsForPersonAndGroupTypeFilter.class;
    }

    @Override
    public Class<GroupsForPersonAndGroupTypeFilterInput> getInputObjectClass() {
        return GroupsForPersonAndGroupTypeFilterInput.class;
    }

}
