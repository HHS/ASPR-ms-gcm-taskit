package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.translation.specs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.stochastics.datamanagers.StochasticsPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.stochastics.support.RandomNumberGeneratorId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.stochastics.support.WellState;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.data.input.StochasticsPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.support.input.RandomNumberGeneratorIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.support.input.RandomNumberGeneratorMapInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.support.input.WellStateInput;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitError;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;
import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain StochasticsPluginDataInput} and
 * {@linkplain StochasticsPluginData}
 */
public class StochasticsPluginDataTranslationSpec
        extends ProtobufTranslationSpec<StochasticsPluginDataInput, StochasticsPluginData> {

    @Override
    protected StochasticsPluginData translateInputObject(StochasticsPluginDataInput inputObject) {
        if (!StochasticsPluginData.checkVersionSupported(inputObject.getVersion())) {
            throw new ContractException(TaskitError.UNSUPPORTED_VERSION);
        }

        StochasticsPluginData.Builder builder = StochasticsPluginData.builder();

        WellState wellState = this.taskitEngine.translateObject(inputObject.getWellState());

        builder.setMainRNGState(wellState);

        for (RandomNumberGeneratorMapInput randomGenIdInput : inputObject.getRandomNumberGeneratorIdsList()) {
            RandomNumberGeneratorId generatorId = this.taskitEngine
                    .translateObject(randomGenIdInput.getRandomNumberGeneratorId());
            WellState generatorWellState = this.taskitEngine.translateObject(randomGenIdInput.getWellState());
            builder.addRNG(generatorId, generatorWellState);
        }

        return builder.build();
    }

    @Override
    protected StochasticsPluginDataInput translateAppObject(StochasticsPluginData appObject) {
        StochasticsPluginDataInput.Builder builder = StochasticsPluginDataInput.newBuilder();

        builder.setVersion(appObject.getVersion());

        WellStateInput wellStateInput = this.taskitEngine.translateObject(appObject.getWellState());
        builder.setWellState(wellStateInput);

        for (RandomNumberGeneratorId randomNumberGeneratorId : appObject.getRandomNumberGeneratorIds()) {
            RandomNumberGeneratorIdInput randomNumberGeneratorIdInput = this.taskitEngine
                    .translateObjectAsClassSafe(randomNumberGeneratorId, RandomNumberGeneratorId.class);
            WellStateInput generatorWellStateInput = this.taskitEngine
                    .translateObject(appObject.getWellState(randomNumberGeneratorId));

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
