package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translationSpecs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.materials.support.MaterialsProducerId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.support.input.MaterialsProducerIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain MaterialsProducerIdInput} and {@linkplain MaterialsProducerId}
 */
public class MaterialsProducerIdTranslationSpec
        extends ProtobufTranslationSpec<MaterialsProducerIdInput, MaterialsProducerId> {

    @Override
    protected MaterialsProducerId translateInputObject(MaterialsProducerIdInput inputObject) {
        return this.taskitEngine.getObjectFromAny(inputObject.getId());
    }

    @Override
    protected MaterialsProducerIdInput translateAppObject(MaterialsProducerId appObject) {
        return MaterialsProducerIdInput.newBuilder().setId(this.taskitEngine.getAnyFromObject(appObject)).build();
    }

    @Override
    public Class<MaterialsProducerId> getAppObjectClass() {
        return MaterialsProducerId.class;
    }

    @Override
    public Class<MaterialsProducerIdInput> getInputObjectClass() {
        return MaterialsProducerIdInput.class;
    }

}
