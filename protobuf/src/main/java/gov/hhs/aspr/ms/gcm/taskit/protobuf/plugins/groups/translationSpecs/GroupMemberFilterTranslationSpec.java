package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translationSpecs;

import gov.hhs.aspr.ms.gcm.plugins.groups.support.GroupId;
import gov.hhs.aspr.ms.gcm.plugins.groups.support.GroupMemberFilter;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.support.input.GroupMemberFilterInput;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationSpec;

public class GroupMemberFilterTranslationSpec
        extends ProtobufTranslationSpec<GroupMemberFilterInput, GroupMemberFilter> {

    @Override
    protected GroupMemberFilter convertInputObject(GroupMemberFilterInput inputObject) {
        return new GroupMemberFilter(new GroupId(inputObject.getGId()));
    }

    @Override
    protected GroupMemberFilterInput convertAppObject(GroupMemberFilter appObject) {
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
