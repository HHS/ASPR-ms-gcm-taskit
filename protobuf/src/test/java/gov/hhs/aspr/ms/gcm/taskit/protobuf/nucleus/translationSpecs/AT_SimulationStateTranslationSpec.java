package gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.nucleus.SimulationState;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.NucleusTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.input.SimulationStateInput;
import gov.hhs.aspr.ms.taskit.core.CoreTranslationError;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationEngine;
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
    public void testConvertObject() {
        ProtobufTranslationEngine protobufTranslationEngine = ProtobufTranslationEngine.builder()
                .addTranslator(NucleusTranslator.getTranslator())
                .build();

        SimulationStateTranslationSpec translationSpec = new SimulationStateTranslationSpec();
        translationSpec.init(protobufTranslationEngine);

        double startTime = 5;

        SimulationState expectedAppValue = SimulationState.builder()
                .setStartTime(startTime)
                .setBaseDate(LocalDate.of(2023, 4, 12))
                .build();

        SimulationStateInput inputValue = translationSpec.convertAppObject(expectedAppValue);

        SimulationState actualAppValue = translationSpec.convertInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);

        expectedAppValue = SimulationState.builder().setStartTime(startTime).build();

        actualAppValue = translationSpec.convertInputObject(inputValue.toBuilder().clearBaseDate().build());

        assertEquals(expectedAppValue, actualAppValue);

        // preconditions
        // version is not supported
        ContractException contractException = assertThrows(ContractException.class, () -> {
            translationSpec.convertInputObject(SimulationStateInput.newBuilder().setVersion("badversion").build());
        });

        assertEquals(CoreTranslationError.UNSUPPORTED_VERSION, contractException.getErrorType());
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
