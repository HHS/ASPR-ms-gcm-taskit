package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translation.specs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.support.GroupPropertyId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.support.input.GroupPropertyIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain GroupPropertyIdInput} and {@linkplain GroupPropertyId}
 */
public class GroupPropertyIdTranslationSpec extends ProtobufTranslationSpec<GroupPropertyIdInput, GroupPropertyId> {

    @Override
    protected GroupPropertyId translateInputObject(GroupPropertyIdInput inputObject) {
        return this.taskitEngine.getObjectFromAny(inputObject.getId());
    }

    @Override
    protected GroupPropertyIdInput translateAppObject(GroupPropertyId appObject) {
        return GroupPropertyIdInput.newBuilder().setId(this.taskitEngine.getAnyFromObject(appObject)).build();
    }

    @Override
    public Class<GroupPropertyId> getAppObjectClass() {
        return GroupPropertyId.class;
    }

    @Override
    public Class<GroupPropertyIdInput> getInputObjectClass() {
        return GroupPropertyIdInput.class;
    }
}
