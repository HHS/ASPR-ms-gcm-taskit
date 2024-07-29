package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translationSpecs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.support.GroupTypeId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.support.input.GroupTypeIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain GroupTypeIdInput} and {@linkplain GroupTypeId}
 */
public class GroupTypeIdTranslationSpec extends ProtobufTranslationSpec<GroupTypeIdInput, GroupTypeId> {

    @Override
    protected GroupTypeId translateInputObject(GroupTypeIdInput inputObject) {
        return this.taskitEngine.getObjectFromAny(inputObject.getId());
    }

    @Override
    protected GroupTypeIdInput translateAppObject(GroupTypeId appObject) {
        return GroupTypeIdInput.newBuilder().setId(this.taskitEngine.getAnyFromObject(appObject)).build();
    }

    @Override
    public Class<GroupTypeId> getAppObjectClass() {
        return GroupTypeId.class;
    }

    @Override
    public Class<GroupTypeIdInput> getInputObjectClass() {
        return GroupTypeIdInput.class;
    }
}
