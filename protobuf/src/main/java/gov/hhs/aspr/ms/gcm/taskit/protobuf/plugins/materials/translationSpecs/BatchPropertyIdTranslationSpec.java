package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translationSpecs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.materials.support.BatchPropertyId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.support.input.BatchPropertyIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain BatchPropertyIdInput} and {@linkplain BatchPropertyId}
 */
public class BatchPropertyIdTranslationSpec extends ProtobufTranslationSpec<BatchPropertyIdInput, BatchPropertyId> {

    @Override
    protected BatchPropertyId translateInputObject(BatchPropertyIdInput inputObject) {
        return this.taskitEngine.getObjectFromAny(inputObject.getId());
    }

    @Override
    protected BatchPropertyIdInput translateAppObject(BatchPropertyId appObject) {
        return BatchPropertyIdInput.newBuilder().setId(this.taskitEngine.getAnyFromObject(appObject)).build();
    }

    @Override
    public Class<BatchPropertyId> getAppObjectClass() {
        return BatchPropertyId.class;
    }

    @Override
    public Class<BatchPropertyIdInput> getInputObjectClass() {
        return BatchPropertyIdInput.class;
    }

}
