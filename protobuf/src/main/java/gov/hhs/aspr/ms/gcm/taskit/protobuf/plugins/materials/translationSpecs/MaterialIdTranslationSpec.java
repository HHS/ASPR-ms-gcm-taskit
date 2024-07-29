package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translationSpecs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.materials.support.MaterialId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.support.input.MaterialIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain MaterialIdInput} and {@linkplain MaterialId}
 */
public class MaterialIdTranslationSpec extends ProtobufTranslationSpec<MaterialIdInput, MaterialId> {

    @Override
    protected MaterialId translateInputObject(MaterialIdInput inputObject) {
        return this.taskitEngine.getObjectFromAny(inputObject.getId());
    }

    @Override
    protected MaterialIdInput translateAppObject(MaterialId appObject) {
        return MaterialIdInput.newBuilder().setId(this.taskitEngine.getAnyFromObject(appObject)).build();
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
