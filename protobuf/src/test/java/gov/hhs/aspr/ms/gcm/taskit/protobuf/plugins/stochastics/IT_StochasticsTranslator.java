package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.stochastics.datamanagers.StochasticsDataManager;
import gov.hhs.aspr.ms.gcm.simulation.plugins.stochastics.datamanagers.StochasticsPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.stochastics.support.RandomNumberGeneratorId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.stochastics.support.Well;
import gov.hhs.aspr.ms.gcm.simulation.plugins.stochastics.support.WellState;
import gov.hhs.aspr.ms.gcm.simulation.plugins.stochastics.testsupport.StochasticsTestPluginFactory;
import gov.hhs.aspr.ms.gcm.simulation.plugins.stochastics.testsupport.TestRandomGeneratorId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.data.input.StochasticsPluginDataInput;
import gov.hhs.aspr.ms.taskit.core.TranslationController;
import gov.hhs.aspr.ms.taskit.core.TranslationEngineType;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.resourcehelper.ResourceHelper;

public class IT_StochasticsTranslator {
    Path basePath = ResourceHelper.getResourceDir(this.getClass());
    Path filePath = ResourceHelper.makeOutputDir(basePath, "test-output");

    @Test
    @UnitTestForCoverage
    public void testStochasticsTranslator() {
        String fileName = "stochasticsPluginData.json";

        ResourceHelper.createOutputFile(filePath, fileName);

        TranslationController translatorController = TranslationController.builder()
                .addTranslationEngine(ProtobufTranslationEngine.builder()
                        .addTranslator(StochasticsTranslator.getTranslator())
                        .build())
                .addInputFilePath(filePath.resolve(fileName), StochasticsPluginDataInput.class,
                        TranslationEngineType.PROTOBUF)
                .build();

        long seed = 524805676405822016L;

        StochasticsPluginData stochasticsPluginDataIn = StochasticsTestPluginFactory
                .getStandardStochasticsPluginData(seed);

        StochasticsDataManager stochasticsDataManager = new StochasticsDataManager(stochasticsPluginDataIn);

        StochasticsPluginData.Builder builder = StochasticsPluginData.builder();

        for (RandomNumberGeneratorId randomNumberGeneratorId : stochasticsDataManager.getRandomNumberGeneratorIds()) {
            Well wellRNG = (Well) stochasticsDataManager.getRandomGeneratorFromId(randomNumberGeneratorId);
            if (randomNumberGeneratorId == TestRandomGeneratorId.DASHER
                    || randomNumberGeneratorId == TestRandomGeneratorId.CUPID) {
                builder.addRNG(randomNumberGeneratorId,
                        WellState.builder().setSeed(wellRNG.getWellState().getSeed()).build());
            } else {
                builder.addRNG(randomNumberGeneratorId, wellRNG.getWellState());
            }
        }
        builder.setMainRNGState(((Well) stochasticsDataManager.getRandomGenerator()).getWellState());

        StochasticsPluginData expectedPluginData = builder.build();

        translatorController.writeOutput(expectedPluginData, filePath.resolve(fileName),
                TranslationEngineType.PROTOBUF);
        translatorController.readInput();

        StochasticsPluginData actualPluginData = translatorController.getFirstObject(StochasticsPluginData.class);

        assertEquals(expectedPluginData, actualPluginData);
        assertEquals(expectedPluginData.toString(), actualPluginData.toString());
    }
}