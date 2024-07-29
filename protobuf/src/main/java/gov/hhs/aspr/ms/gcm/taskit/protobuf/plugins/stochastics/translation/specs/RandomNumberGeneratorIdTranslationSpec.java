package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.translation.specs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.stochastics.support.RandomNumberGeneratorId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.support.input.RandomNumberGeneratorIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain RandomNumberGeneratorIdInput} and
 * {@linkplain RandomNumberGeneratorId}
 */
public class RandomNumberGeneratorIdTranslationSpec
        extends ProtobufTranslationSpec<RandomNumberGeneratorIdInput, RandomNumberGeneratorId> {

    @Override
    protected RandomNumberGeneratorId translateInputObject(RandomNumberGeneratorIdInput inputObject) {
        return this.taskitEngine.getObjectFromAny(inputObject.getId());
    }

    @Override
    protected RandomNumberGeneratorIdInput translateAppObject(RandomNumberGeneratorId appObject) {
        return RandomNumberGeneratorIdInput.newBuilder().setId(this.taskitEngine.getAnyFromObject(appObject)).build();
    }

    @Override
    public Class<RandomNumberGeneratorId> getAppObjectClass() {
        return RandomNumberGeneratorId.class;
    }

    @Override
    public Class<RandomNumberGeneratorIdInput> getInputObjectClass() {
        return RandomNumberGeneratorIdInput.class;
    }
}
