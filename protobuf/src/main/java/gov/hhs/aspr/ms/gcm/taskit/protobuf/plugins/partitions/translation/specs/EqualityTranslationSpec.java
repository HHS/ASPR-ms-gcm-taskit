package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translation.specs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.Equality;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.support.input.EqualityInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain EqualityInput} and {@linkplain Equality}
 */
public class EqualityTranslationSpec extends ProtobufTranslationSpec<EqualityInput, Equality> {

    @Override
    protected Equality translateInputObject(EqualityInput inputObject) {
        return Equality.valueOf(inputObject.name());
    }

    @Override
    protected EqualityInput translateAppObject(Equality appObject) {
        return EqualityInput.valueOf(appObject.name());
    }

    @Override
    public Class<Equality> getAppObjectClass() {
        return Equality.class;
    }

    @Override
    public Class<EqualityInput> getInputObjectClass() {
        return EqualityInput.class;
    }

}
