package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translationSpecs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.support.GroupTypesForPersonFilter;
import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.Equality;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.support.input.GroupTypesForPersonFilterInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.support.input.EqualityInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

public class GroupTypesForPersonFilterTranslationSpec
        extends ProtobufTranslationSpec<GroupTypesForPersonFilterInput, GroupTypesForPersonFilter> {

    @Override
    protected GroupTypesForPersonFilter translateInputObject(GroupTypesForPersonFilterInput inputObject) {
        Equality equality = this.taskitEngine.translateObject(inputObject.getEquality());
        int groupTypeCount = inputObject.getGroupTypeCount();

        return new GroupTypesForPersonFilter(equality, groupTypeCount);
    }

    @Override
    protected GroupTypesForPersonFilterInput translateAppObject(GroupTypesForPersonFilter appObject) {
        EqualityInput equality = this.taskitEngine.translateObjectAsClassSafe(appObject.getEquality(),
                Equality.class);
        int groupTypeCount = appObject.getGroupTypeCount();

        return GroupTypesForPersonFilterInput.newBuilder()
                .setEquality(equality)
                .setGroupTypeCount(groupTypeCount)
                .build();
    }

    @Override
    public Class<GroupTypesForPersonFilter> getAppObjectClass() {
        return GroupTypesForPersonFilter.class;
    }

    @Override
    public Class<GroupTypesForPersonFilterInput> getInputObjectClass() {
        return GroupTypesForPersonFilterInput.class;
    }

}
