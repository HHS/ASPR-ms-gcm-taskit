package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translationSpecs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.testsupport.attributes.support.AttributeId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.support.attributes.input.AttributeIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain AttributeIdInput} and {@linkplain AttributeId}
 */
public class AttributeIdTranslationSpec extends ProtobufTranslationSpec<AttributeIdInput, AttributeId> {

    @Override
    protected AttributeId translateInputObject(AttributeIdInput inputObject) {
        return this.taskitEngine.getObjectFromAny(inputObject.getId());
    }

    @Override
    protected AttributeIdInput translateAppObject(AttributeId appObject) {
        return AttributeIdInput.newBuilder().setId(this.taskitEngine.getAnyFromObject(appObject)).build();
    }

    @Override
    public Class<AttributeId> getAppObjectClass() {
        return AttributeId.class;
    }

    @Override
    public Class<AttributeIdInput> getInputObjectClass() {
        return AttributeIdInput.class;
    }

}
