package gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.translation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.nucleus.ExperimentParameterData;
import gov.hhs.aspr.ms.gcm.simulation.nucleus.SimulationState;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.input.ExperimentParameterDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.input.SimulationStateInput;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitEngineManager;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngineId;
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

        TaskitEngineManager taskitEngineManager = TaskitEngineManager.builder()
                .addTaskitEngine(
                        ProtobufJsonTaskitEngine.builder().addTranslator(NucleusTranslator.getTranslator()).build())
                .build();

        double startTime = 5;

        SimulationState expectedSimulationState = SimulationState.builder()
                .setStartTime(startTime)
                .setBaseDate(LocalDate.of(2023, 4, 12))
                .build();

        taskitEngineManager.translateAndWrite(filePath.resolve(fileName), expectedSimulationState,
                ProtobufTaskitEngineId.JSON_ENGINE_ID);

        SimulationState actualSimulationState = taskitEngineManager.readAndTranslate(filePath.resolve(fileName),
                SimulationStateInput.class, ProtobufTaskitEngineId.JSON_ENGINE_ID);

        assertEquals(expectedSimulationState, actualSimulationState);
    }

    @Test
    @UnitTestForCoverage
    public void testExperimentParameterDataTranslator() {
        String fileName = "experimentParameterData.json";

        ResourceHelper.createFile(filePath, fileName);

        TaskitEngineManager taskitEngineManager = TaskitEngineManager.builder()
                .addTaskitEngine(
                        ProtobufJsonTaskitEngine.builder().addTranslator(NucleusTranslator.getTranslator()).build())
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

        taskitEngineManager.translateAndWrite(filePath.resolve(fileName), expectedExperimentParameterData,
                ProtobufTaskitEngineId.JSON_ENGINE_ID);

        ExperimentParameterData actualExperimentParameterData = taskitEngineManager.readAndTranslate(
                filePath.resolve(fileName), ExperimentParameterDataInput.class, ProtobufTaskitEngineId.JSON_ENGINE_ID);

        assertEquals(expectedExperimentParameterData, actualExperimentParameterData);
    }

}
