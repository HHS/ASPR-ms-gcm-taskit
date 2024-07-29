package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translationSpecs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.materials.support.MaterialsProducerPropertyId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.support.input.MaterialsProducerPropertyIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain MaterialsProducerPropertyIdInput} and
 * {@linkplain MaterialsProducerPropertyId}
 */
public class MaterialsProducerPropertyIdTranslationSpec
        extends ProtobufTranslationSpec<MaterialsProducerPropertyIdInput, MaterialsProducerPropertyId> {

    @Override
    protected MaterialsProducerPropertyId translateInputObject(MaterialsProducerPropertyIdInput inputObject) {
        return this.taskitEngine.getObjectFromAny(inputObject.getId());
    }

    @Override
    protected MaterialsProducerPropertyIdInput translateAppObject(MaterialsProducerPropertyId appObject) {
        return MaterialsProducerPropertyIdInput.newBuilder()
                .setId(this.taskitEngine.getAnyFromObject(appObject))
                .build();
    }

    @Override
    public Class<MaterialsProducerPropertyId> getAppObjectClass() {
        return MaterialsProducerPropertyId.class;
    }

    @Override
    public Class<MaterialsProducerPropertyIdInput> getInputObjectClass() {
        return MaterialsProducerPropertyIdInput.class;
    }

}
