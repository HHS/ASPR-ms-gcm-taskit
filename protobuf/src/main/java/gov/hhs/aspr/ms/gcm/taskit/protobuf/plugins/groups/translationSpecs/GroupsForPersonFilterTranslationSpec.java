package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translationSpecs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.support.GroupsForPersonFilter;
import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.Equality;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.support.input.GroupsForPersonFilterInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.support.input.EqualityInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

public class GroupsForPersonFilterTranslationSpec
        extends ProtobufTranslationSpec<GroupsForPersonFilterInput, GroupsForPersonFilter> {

    @Override
    protected GroupsForPersonFilter translateInputObject(GroupsForPersonFilterInput inputObject) {
        Equality equality = this.taskitEngine.translateObject(inputObject.getEquality());
        int groupCount = inputObject.getGroupCount();

        return new GroupsForPersonFilter(equality, groupCount);
    }

    @Override
    protected GroupsForPersonFilterInput translateAppObject(GroupsForPersonFilter appObject) {
        EqualityInput equalityInput = this.taskitEngine.translateObjectAsClassSafe(appObject.getEquality(),
                Equality.class);
        int groupCount = appObject.getGroupCount();

        return GroupsForPersonFilterInput.newBuilder().setEquality(equalityInput).setGroupCount(groupCount).build();
    }

    @Override
    public Class<GroupsForPersonFilter> getAppObjectClass() {
        return GroupsForPersonFilter.class;
    }

    @Override
    public Class<GroupsForPersonFilterInput> getInputObjectClass() {
        return GroupsForPersonFilterInput.class;
    }

}
