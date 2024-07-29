package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translationSpecs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.resources.support.ResourceId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.resources.support.ResourceInitialization;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.support.input.ResourceInitializationInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain ResourceInitializationInput} and
 * {@linkplain ResourceInitialization}
 */
public class ResourceInitializationTranslationSpec
        extends ProtobufTranslationSpec<ResourceInitializationInput, ResourceInitialization> {

    @Override
    protected ResourceInitialization translateInputObject(ResourceInitializationInput inputObject) {
        ResourceId resourceId = this.taskitEngine.translateObject(inputObject.getResourceId());
        long amount = inputObject.getAmount();
        return new ResourceInitialization(resourceId, amount);
    }

    @Override
    protected ResourceInitializationInput translateAppObject(ResourceInitialization appObject) {
        return ResourceInitializationInput.newBuilder()
                .setAmount(appObject.getAmount())
                .setResourceId(this.taskitEngine.getAnyFromObject(appObject.getResourceId()))
                .build();
    }

    @Override
    public Class<ResourceInitialization> getAppObjectClass() {
        return ResourceInitialization.class;
    }

    @Override
    public Class<ResourceInitializationInput> getInputObjectClass() {
        return ResourceInitializationInput.class;
    }

}
