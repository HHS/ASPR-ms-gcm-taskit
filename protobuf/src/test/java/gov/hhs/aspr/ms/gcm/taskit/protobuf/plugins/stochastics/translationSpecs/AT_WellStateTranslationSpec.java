package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.stochastics.support.WellState;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.support.input.WellStateInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.translation.StochasticsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.translation.specs.WellStateTranslationSpec;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_WellStateTranslationSpec {

    @Test
    @UnitTestConstructor(target = WellStateTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new WellStateTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testTranslateObject() {
        ProtobufTaskitEngine ProtobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslator(StochasticsTranslator.getTranslator())
                .build();

        WellStateTranslationSpec translationSpec = new WellStateTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        WellState expectedAppValue = WellState.builder().setSeed(524805676405822016L).build();

        WellStateInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        WellState actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);

        inputValue = inputValue.toBuilder().clearVArray().build();

        actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = WellStateTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        WellStateTranslationSpec translationSpec = new WellStateTranslationSpec();

        assertEquals(WellState.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = WellStateTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        WellStateTranslationSpec translationSpec = new WellStateTranslationSpec();

        assertEquals(WellStateInput.class, translationSpec.getInputObjectClass());
    }
}
