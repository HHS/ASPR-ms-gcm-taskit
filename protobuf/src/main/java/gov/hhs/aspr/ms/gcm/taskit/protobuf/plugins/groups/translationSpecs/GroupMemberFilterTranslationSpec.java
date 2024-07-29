package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translationSpecs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.support.GroupId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.support.GroupMemberFilter;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.support.input.GroupMemberFilterInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

public class GroupMemberFilterTranslationSpec
        extends ProtobufTranslationSpec<GroupMemberFilterInput, GroupMemberFilter> {

    @Override
    protected GroupMemberFilter translateInputObject(GroupMemberFilterInput inputObject) {
        return new GroupMemberFilter(new GroupId(inputObject.getGId()));
    }

    @Override
    protected GroupMemberFilterInput translateAppObject(GroupMemberFilter appObject) {
        return GroupMemberFilterInput.newBuilder().setGId(appObject.getGroupId().getValue()).build();
    }

    @Override
    public Class<GroupMemberFilter> getAppObjectClass() {
        return GroupMemberFilter.class;
    }

    @Override
    public Class<GroupMemberFilterInput> getInputObjectClass() {
        return GroupMemberFilterInput.class;
    }

}
