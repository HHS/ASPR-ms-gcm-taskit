package gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.nucleus.ExperimentParameterData;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.NucleusTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.input.ExperimentParameterDataInput;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitError;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;
import gov.hhs.aspr.ms.util.errors.ContractException;

public class AT_ExperimentParameterDataTranslationSpec {

    @Test
    @UnitTestConstructor(target = ExperimentParameterDataTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new ExperimentParameterDataTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testtranslateObject() {
        ProtobufTaskitEngine ProtobufTaskitEngine = IProtobufTaskitEngineBuilder()
                .addTranslator(NucleusTranslator.getTranslator())
                .build();

        ExperimentParameterDataTranslationSpec translationSpec = new ExperimentParameterDataTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        ExperimentParameterData.Builder builder = ExperimentParameterData.builder()
                .setThreadCount(8)
                .setRecordState(false)
                .setHaltOnException(true)
                .setContinueFromProgressLog(false);

        for (int i = 0; i < 10; i++) {
            builder.addExplicitScenarioId(i);
        }

        ExperimentParameterData expectedAppValue = builder.build();

        ExperimentParameterDataInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        ExperimentParameterData actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);

        expectedAppValue = builder.setSimulationHaltTime(10.0).build();
        inputValue = translationSpec.translateAppObject(expectedAppValue);
        actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);

        expectedAppValue = builder.setExperimentProgressLog(Path.of("")).build();
        inputValue = translationSpec.translateAppObject(expectedAppValue);
        actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);

        // preconditions
        // version is not supported
        ContractException contractException = assertThrows(ContractException.class, () -> {
            translationSpec.translateInputObject(ExperimentParameterDataInput.newBuilder().setVersion("badversion").build());
        });

        assertEquals(TaskitError.UNSUPPORTED_VERSION, contractException.getErrorType());
    }

    @Test
    @UnitTestMethod(target = ExperimentParameterDataTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        ExperimentParameterDataTranslationSpec translationSpec = new ExperimentParameterDataTranslationSpec();

        assertEquals(ExperimentParameterData.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = ExperimentParameterDataTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        ExperimentParameterDataTranslationSpec translationSpec = new ExperimentParameterDataTranslationSpec();

        assertEquals(ExperimentParameterDataInput.class, translationSpec.getInputObjectClass());
    }
}
