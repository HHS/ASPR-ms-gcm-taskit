package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.translationSpecs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.stochastics.datamanagers.StochasticsPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.stochastics.support.RandomNumberGeneratorId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.stochastics.support.WellState;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.data.input.StochasticsPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.support.input.RandomNumberGeneratorIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.support.input.RandomNumberGeneratorMapInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.support.input.WellStateInput;
import gov.hhs.aspr.ms.taskit.core.CoreTranslationError;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationSpec;
import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain StochasticsPluginDataInput} and
 * {@linkplain StochasticsPluginData}
 */
public class StochasticsPluginDataTranslationSpec
        extends ProtobufTranslationSpec<StochasticsPluginDataInput, StochasticsPluginData> {

    @Override
    protected StochasticsPluginData convertInputObject(StochasticsPluginDataInput inputObject) {
        if (!StochasticsPluginData.checkVersionSupported(inputObject.getVersion())) {
            throw new ContractException(CoreTranslationError.UNSUPPORTED_VERSION);
        }

        StochasticsPluginData.Builder builder = StochasticsPluginData.builder();

        WellState wellState = this.translationEngine.convertObject(inputObject.getWellState());

        builder.setMainRNGState(wellState);

        for (RandomNumberGeneratorMapInput randomGenIdInput : inputObject.getRandomNumberGeneratorIdsList()) {
            RandomNumberGeneratorId generatorId = this.translationEngine
                    .convertObject(randomGenIdInput.getRandomNumberGeneratorId());
            WellState generatorWellState = this.translationEngine.convertObject(randomGenIdInput.getWellState());
            builder.addRNG(generatorId, generatorWellState);
        }

        return builder.build();
    }

    @Override
    protected StochasticsPluginDataInput convertAppObject(StochasticsPluginData appObject) {
        StochasticsPluginDataInput.Builder builder = StochasticsPluginDataInput.newBuilder();

        builder.setVersion(appObject.getVersion());

        WellStateInput wellStateInput = this.translationEngine.convertObject(appObject.getWellState());
        builder.setWellState(wellStateInput);

        for (RandomNumberGeneratorId randomNumberGeneratorId : appObject.getRandomNumberGeneratorIds()) {
            RandomNumberGeneratorIdInput randomNumberGeneratorIdInput = this.translationEngine
                    .convertObjectAsSafeClass(randomNumberGeneratorId, RandomNumberGeneratorId.class);
            WellStateInput generatorWellStateInput = this.translationEngine
                    .convertObject(appObject.getWellState(randomNumberGeneratorId));

            builder.addRandomNumberGeneratorIds(RandomNumberGeneratorMapInput.newBuilder()
                    .setWellState(generatorWellStateInput)
                    .setRandomNumberGeneratorId(randomNumberGeneratorIdInput)
                    .build());
        }

        return builder.build();
    }

    @Override
    public Class<StochasticsPluginData> getAppObjectClass() {
        return StochasticsPluginData.class;
    }

    @Override
    public Class<StochasticsPluginDataInput> getInputObjectClass() {
        return StochasticsPluginDataInput.class;
    }

}
