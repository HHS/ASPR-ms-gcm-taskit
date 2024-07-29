package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translationSpecs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.materials.support.BatchId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.support.input.BatchIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between {@linkplain BatchIdInput}
 * and {@linkplain BatchId}
 */
public class BatchIdTranslationSpec extends ProtobufTranslationSpec<BatchIdInput, BatchId> {

    @Override
    protected BatchId translateInputObject(BatchIdInput inputObject) {
        return new BatchId(inputObject.getId());
    }

    @Override
    protected BatchIdInput translateAppObject(BatchId appObject) {
        return BatchIdInput.newBuilder().setId(appObject.getValue()).build();
    }

    @Override
    public Class<BatchId> getAppObjectClass() {
        return BatchId.class;
    }

    @Override
    public Class<BatchIdInput> getInputObjectClass() {
        return BatchIdInput.class;
    }

}
