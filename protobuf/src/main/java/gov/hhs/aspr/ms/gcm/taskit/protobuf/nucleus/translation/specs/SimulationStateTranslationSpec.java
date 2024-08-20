package gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.translation.specs;

import java.time.LocalDate;

import com.google.type.Date;

import gov.hhs.aspr.ms.gcm.simulation.nucleus.SimulationState;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.input.SimulationStateInput;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitError;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;
import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain SimulationStateInput} and {@linkplain SimulationState}
 */
public class SimulationStateTranslationSpec extends ProtobufTranslationSpec<SimulationStateInput, SimulationState> {

    @Override
    protected SimulationState translateInputObject(SimulationStateInput inputObject) {
        if (!SimulationState.checkVersionSupported(inputObject.getVersion())) {
            throw new ContractException(TaskitError.UNSUPPORTED_VERSION);
        }

        SimulationState.Builder builder = SimulationState.builder();

        builder.setStartTime(inputObject.getStartTime());

        if (inputObject.hasBaseDate()) {
            LocalDate LocalDate = this.taskitEngine.translateObject(inputObject.getBaseDate());
            builder.setBaseDate(LocalDate);
        }

        return builder.build();
    }

    @Override
    protected SimulationStateInput translateAppObject(SimulationState appObject) {
        SimulationStateInput.Builder builder = SimulationStateInput.newBuilder()
                .setStartTime(appObject.getStartTime())
                .setVersion(appObject.getVersion());

        Date date = this.taskitEngine.translateObject(appObject.getBaseDate());
        builder.setBaseDate(date);

        return builder.build();
    }

    @Override
    public Class<SimulationState> getAppObjectClass() {
        return SimulationState.class;
    }

    @Override
    public Class<SimulationStateInput> getInputObjectClass() {
        return SimulationStateInput.class;
    }

}
