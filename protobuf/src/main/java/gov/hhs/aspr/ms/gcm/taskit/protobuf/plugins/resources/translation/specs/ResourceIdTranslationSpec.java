package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translation.specs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.resources.support.ResourceId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.support.input.ResourceIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain ResourceIdInput} and {@linkplain ResourceId}
 */
public class ResourceIdTranslationSpec extends ProtobufTranslationSpec<ResourceIdInput, ResourceId> {

    @Override
    protected ResourceId translateInputObject(ResourceIdInput inputObject) {
        return this.taskitEngine.getObjectFromAny(inputObject.getId());
    }

    @Override
    protected ResourceIdInput translateAppObject(ResourceId appObject) {
        return ResourceIdInput.newBuilder().setId(this.taskitEngine.getAnyFromObject(appObject)).build();
    }

    @Override
    public Class<ResourceId> getAppObjectClass() {
        return ResourceId.class;
    }

    @Override
    public Class<ResourceIdInput> getInputObjectClass() {
        return ResourceIdInput.class;
    }

}
