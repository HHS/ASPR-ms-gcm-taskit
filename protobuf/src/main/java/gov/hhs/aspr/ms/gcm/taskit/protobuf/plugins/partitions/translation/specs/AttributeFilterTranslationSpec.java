package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translation.specs;

import com.google.protobuf.Any;

import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.Equality;
import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.testsupport.attributes.support.AttributeFilter;
import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.testsupport.attributes.support.AttributeId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.support.attributes.input.AttributeFilterInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.support.attributes.input.AttributeIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.support.input.EqualityInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain AttributeFilterInput} and {@linkplain AttributeFilter}
 */
public class AttributeFilterTranslationSpec extends ProtobufTranslationSpec<AttributeFilterInput, AttributeFilter> {

    @Override
    protected AttributeFilter translateInputObject(AttributeFilterInput inputObject) {
        AttributeId attributeId = this.taskitEngine.translateObject(inputObject.getAttributeId());
        Equality equality = this.taskitEngine.translateObject(inputObject.getEquality());
        Object value = this.taskitEngine.getObjectFromAny(inputObject.getValue());

        return new AttributeFilter(attributeId, equality, value);
    }

    @Override
    protected AttributeFilterInput translateAppObject(AttributeFilter appObject) {
        AttributeIdInput attributeIdInput = this.taskitEngine.translateObjectAsClassSafe(appObject.getAttributeId(),
                AttributeId.class);
        EqualityInput equalityInput = this.taskitEngine.translateObjectAsClassSafe(appObject.getEquality(),
                Equality.class);
        Any value = this.taskitEngine.getAnyFromObject(appObject.getValue());

        return AttributeFilterInput.newBuilder()
                .setAttributeId(attributeIdInput)
                .setEquality(equalityInput)
                .setValue(value)
                .build();
    }

    @Override
    public Class<AttributeFilter> getAppObjectClass() {
        return AttributeFilter.class;
    }

    @Override
    public Class<AttributeFilterInput> getInputObjectClass() {
        return AttributeFilterInput.class;
    }

}
