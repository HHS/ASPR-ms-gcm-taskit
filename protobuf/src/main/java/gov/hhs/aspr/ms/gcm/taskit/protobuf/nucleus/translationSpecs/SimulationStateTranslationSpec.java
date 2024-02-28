package gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.translationSpecs;

import java.time.LocalDate;

import com.google.type.Date;

import gov.hhs.aspr.ms.gcm.nucleus.SimulationState;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.input.SimulationStateInput;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain SimulationStateInput} and {@linkplain SimulationState}
 */
public class SimulationStateTranslationSpec extends ProtobufTranslationSpec<SimulationStateInput, SimulationState> {

    @Override
    protected SimulationState convertInputObject(SimulationStateInput inputObject) {
        SimulationState.Builder builder = SimulationState.builder();

        builder.setStartTime(inputObject.getStartTime());

        if (inputObject.hasBaseDate()) {
            LocalDate LocalDate = this.translationEngine.convertObject(inputObject.getBaseDate());
            builder.setBaseDate(LocalDate);
        }

        return builder.build();
    }

    @Override
    protected SimulationStateInput convertAppObject(SimulationState appObject) {
        SimulationStateInput.Builder builder = SimulationStateInput.newBuilder();

        builder.setStartTime(appObject.getStartTime());

        Date date = this.translationEngine.convertObject(appObject.getBaseDate());
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
