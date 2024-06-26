package gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.nucleus.ExperimentParameterData;
import gov.hhs.aspr.ms.gcm.simulation.nucleus.SimulationState;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.input.ExperimentParameterDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.input.SimulationStateInput;
import gov.hhs.aspr.ms.taskit.core.TranslationController;
import gov.hhs.aspr.ms.taskit.core.TranslationEngineType;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.resourcehelper.ResourceHelper;

public class IT_NucleusTranslator {
    Path basePath = ResourceHelper.getResourceDir(this.getClass());
    Path filePath = ResourceHelper.createDirectory(basePath, "test-output");

    @Test
    @UnitTestForCoverage
    public void testSimulationStateTranslator() {
        String fileName = "simulationState.json";

        ResourceHelper.createFile(filePath, fileName);

        TranslationController translatorController = TranslationController.builder()
                .addTranslationEngine(
                        ProtobufTranslationEngine.builder().addTranslator(NucleusTranslator.getTranslator()).build())
                .addInputFilePath(filePath.resolve(fileName), SimulationStateInput.class,
                        TranslationEngineType.PROTOBUF)
                .build();

        double startTime = 5;

        SimulationState exptectedSimulationState = SimulationState.builder()
                .setStartTime(startTime)
                .setBaseDate(LocalDate.of(2023, 4, 12))
                .build();

        translatorController.writeOutput(exptectedSimulationState, filePath.resolve(fileName),
                TranslationEngineType.PROTOBUF);

        translatorController.readInput();

        SimulationState actualSimulationState = translatorController.getFirstObject(SimulationState.class);

        assertEquals(exptectedSimulationState, actualSimulationState);
    }

    @Test
    @UnitTestForCoverage
    public void testExperimentParameterDataTranslator() {
        String fileName = "experimentParameterData.json";

        ResourceHelper.createFile(filePath, fileName);

        TranslationController translatorController = TranslationController.builder()
                .addTranslationEngine(
                        ProtobufTranslationEngine.builder().addTranslator(NucleusTranslator.getTranslator()).build())
                .addInputFilePath(filePath.resolve(fileName), ExperimentParameterDataInput.class,
                        TranslationEngineType.PROTOBUF)
                .build();

        ExperimentParameterData.Builder builder = ExperimentParameterData.builder()
                .setThreadCount(8)
                .setRecordState(false)
                .setHaltOnException(true)
                .setContinueFromProgressLog(false);

        for (int i = 0; i < 10; i++) {
            builder.addExplicitScenarioId(i);
        }

        ExperimentParameterData expectedExperimentParameterData = builder.build();

        translatorController.writeOutput(expectedExperimentParameterData, filePath.resolve(fileName),
                TranslationEngineType.PROTOBUF);

        translatorController.readInput();

        ExperimentParameterData actualExperimentParameterData = translatorController
                .getFirstObject(ExperimentParameterData.class);

        assertEquals(expectedExperimentParameterData, actualExperimentParameterData);
    }

}
