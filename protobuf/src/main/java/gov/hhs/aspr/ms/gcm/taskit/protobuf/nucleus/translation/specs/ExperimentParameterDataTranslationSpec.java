package gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.translation.specs;

import java.nio.file.Path;

import gov.hhs.aspr.ms.gcm.simulation.nucleus.ExperimentParameterData;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.input.ExperimentParameterDataInput;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitError;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;
import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain ExperimentParameterDataInput} and
 * {@linkplain ExperimentParameterData}
 */
public class ExperimentParameterDataTranslationSpec
        extends ProtobufTranslationSpec<ExperimentParameterDataInput, ExperimentParameterData> {

    @Override
    protected ExperimentParameterData translateInputObject(ExperimentParameterDataInput inputObject) {
        if (!ExperimentParameterData.checkVersionSupported(inputObject.getVersion())) {
            throw new ContractException(TaskitError.UNSUPPORTED_VERSION);
        }

        ExperimentParameterData.Builder builder = ExperimentParameterData.builder()
                .setThreadCount(inputObject.getThreadCount())
                .setRecordState(inputObject.getStartRecordingIsScheduled())
                .setHaltOnException(inputObject.getHaltOnException())
                .setContinueFromProgressLog(inputObject.getContinueFromProgressLog());

        for (Integer scenarioId : inputObject.getExplictScenarioIdsList()) {
            builder.addExplicitScenarioId(scenarioId);
        }

        if (inputObject.hasSimulationHaltTime()) {
            builder.setSimulationHaltTime(inputObject.getSimulationHaltTime());
        }

        if (inputObject.hasExperimentProgressLogPath()) {
            builder.setExperimentProgressLog(Path.of(inputObject.getExperimentProgressLogPath()));
        }

        return builder.build();
    }

    @Override
    protected ExperimentParameterDataInput translateAppObject(ExperimentParameterData appObject) {
        ExperimentParameterDataInput.Builder builder = ExperimentParameterDataInput.newBuilder()
                .setThreadCount(appObject.getThreadCount())
                .setStartRecordingIsScheduled(appObject.stateRecordingIsScheduled())
                .setHaltOnException(appObject.haltOnException())
                .setContinueFromProgressLog(appObject.continueFromProgressLog())
                .addAllExplictScenarioIds(appObject.getExplicitScenarioIds())
                .setVersion(appObject.getVersion());

        if (appObject.getExperimentProgressLogPath().isPresent()) {
            Path path = appObject.getExperimentProgressLogPath().get();

            builder.setExperimentProgressLogPath(path.toString());
        }

        if (appObject.getSimulationHaltTime().isPresent()) {
            double simHaltTime = appObject.getSimulationHaltTime().get();
            builder.setSimulationHaltTime(simHaltTime);
        }

        return builder.build();
    }

    @Override
    public Class<ExperimentParameterData> getAppObjectClass() {
        return ExperimentParameterData.class;
    }

    @Override
    public Class<ExperimentParameterDataInput> getInputObjectClass() {
        return ExperimentParameterDataInput.class;
    }

}
