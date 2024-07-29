package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translationSpecs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.resources.support.ResourcePropertyId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.support.input.ResourcePropertyIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain ResourcePropertyIdInput} and {@linkplain ResourcePropertyId}
 */
public class ResourcePropertyIdTranslationSpec
        extends ProtobufTranslationSpec<ResourcePropertyIdInput, ResourcePropertyId> {

    @Override
    protected ResourcePropertyId translateInputObject(ResourcePropertyIdInput inputObject) {
        return this.taskitEngine.getObjectFromAny(inputObject.getId());
    }

    @Override
    protected ResourcePropertyIdInput translateAppObject(ResourcePropertyId appObject) {
        return ResourcePropertyIdInput.newBuilder().setId(this.taskitEngine.getAnyFromObject(appObject)).build();
    }

    @Override
    public Class<ResourcePropertyId> getAppObjectClass() {
        return ResourcePropertyId.class;
    }

    @Override
    public Class<ResourcePropertyIdInput> getInputObjectClass() {
        return ResourcePropertyIdInput.class;
    }

}
