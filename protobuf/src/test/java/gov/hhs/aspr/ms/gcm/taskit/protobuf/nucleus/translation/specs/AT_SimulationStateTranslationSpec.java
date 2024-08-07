package gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.nucleus.SimulationState;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.input.SimulationStateInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.translation.NucleusTranslator;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitError;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;
import gov.hhs.aspr.ms.util.errors.ContractException;

public class AT_SimulationStateTranslationSpec {

    @Test
    @UnitTestConstructor(target = SimulationStateTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new SimulationStateTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testTranslateObject() {
        ProtobufTaskitEngine protobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslator(NucleusTranslator.getTranslator())
                .build();

        SimulationStateTranslationSpec translationSpec = new SimulationStateTranslationSpec();
        translationSpec.init(protobufTaskitEngine);

        double startTime = 5;

        SimulationState expectedAppValue = SimulationState.builder()
                .setStartTime(startTime)
                .setBaseDate(LocalDate.of(2023, 4, 12))
                .build();

        SimulationStateInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        SimulationState actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);

        expectedAppValue = SimulationState.builder().setStartTime(startTime).build();

        actualAppValue = translationSpec.translateInputObject(inputValue.toBuilder().clearBaseDate().build());

        assertEquals(expectedAppValue, actualAppValue);

        // preconditions
        // version is not supported
        ContractException contractException = assertThrows(ContractException.class, () -> {
            translationSpec.translateInputObject(SimulationStateInput.newBuilder().setVersion("badversion").build());
        });

        assertEquals(TaskitError.UNSUPPORTED_VERSION, contractException.getErrorType());
    }

    @Test
    @UnitTestMethod(target = SimulationStateTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        SimulationStateTranslationSpec translationSpec = new SimulationStateTranslationSpec();

        assertEquals(SimulationState.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = SimulationStateTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        SimulationStateTranslationSpec translationSpec = new SimulationStateTranslationSpec();

        assertEquals(SimulationStateInput.class, translationSpec.getInputObjectClass());
    }
}
