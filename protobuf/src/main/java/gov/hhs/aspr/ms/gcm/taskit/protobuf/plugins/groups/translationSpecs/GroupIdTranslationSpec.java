package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translationSpecs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.support.GroupId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.support.input.GroupIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between {@linkplain GroupIdInput}
 * and {@linkplain GroupId}
 */
public class GroupIdTranslationSpec extends ProtobufTranslationSpec<GroupIdInput, GroupId> {

    @Override
    protected GroupId translateInputObject(GroupIdInput inputObject) {
        return new GroupId(inputObject.getId());
    }

    @Override
    protected GroupIdInput translateAppObject(GroupId appObject) {
        return GroupIdInput.newBuilder().setId(appObject.getValue()).build();
    }

    @Override
    public Class<GroupId> getAppObjectClass() {
        return GroupId.class;
    }

    @Override
    public Class<GroupIdInput> getInputObjectClass() {
        return GroupIdInput.class;
    }

}
