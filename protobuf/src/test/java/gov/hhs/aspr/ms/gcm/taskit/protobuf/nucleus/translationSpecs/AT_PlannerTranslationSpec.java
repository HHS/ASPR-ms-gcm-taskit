package gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.nucleus.Planner;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.NucleusTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.input.PlannerInput;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_PlannerTranslationSpec {

    @Test
    @UnitTestConstructor(target = PlannerTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new PlannerTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testtranslateObject() {
        ProtobufTaskitEngine ProtobufTaskitEngine = IProtobufTaskitEngineBuilder()
                .addTranslator(NucleusTranslator.getTranslator())
                .build();

        PlannerTranslationSpec translationSpec = new PlannerTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        Planner expectedAppValue = Planner.DATA_MANAGER;

        PlannerInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        Planner actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = PlannerTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        PlannerTranslationSpec translationSpec = new PlannerTranslationSpec();

        assertEquals(Planner.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = PlannerTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        PlannerTranslationSpec translationSpec = new PlannerTranslationSpec();

        assertEquals(PlannerInput.class, translationSpec.getInputObjectClass());
    }
}
