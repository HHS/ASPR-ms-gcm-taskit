package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translationSpecs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.materials.support.MaterialId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.support.input.MaterialIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain MaterialIdInput} and {@linkplain MaterialId}
 */
public class MaterialIdTranslationSpec extends ProtobufTranslationSpec<MaterialIdInput, MaterialId> {

    @Override
    protected MaterialId convertInputObject(MaterialIdInput inputObject) {
        return this.translationEngine.getObjectFromAny(inputObject.getId());
    }

    @Override
    protected MaterialIdInput convertAppObject(MaterialId appObject) {
        return MaterialIdInput.newBuilder().setId(this.translationEngine.getAnyFromObject(appObject)).build();
    }

    @Override
    public Class<MaterialId> getAppObjectClass() {
        return MaterialId.class;
    }

    @Override
    public Class<MaterialIdInput> getInputObjectClass() {
        return MaterialIdInput.class;
    }

}
