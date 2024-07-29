package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.stochastics.support.RandomNumberGeneratorId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.stochastics.testsupport.TestRandomGeneratorId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.support.input.RandomNumberGeneratorIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.translation.StochasticsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.translation.specs.RandomNumberGeneratorIdTranslationSpec;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_RandomNumberGeneratorIdTranslationSpec {

    @Test
    @UnitTestConstructor(target = RandomNumberGeneratorIdTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new RandomNumberGeneratorIdTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testTranslateObject() {
        ProtobufTaskitEngine ProtobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslator(StochasticsTranslator.getTranslator())
                .build();

        RandomNumberGeneratorIdTranslationSpec translationSpec = new RandomNumberGeneratorIdTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        RandomNumberGeneratorId expectedAppValue = TestRandomGeneratorId.BLITZEN;

        RandomNumberGeneratorIdInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        RandomNumberGeneratorId actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = RandomNumberGeneratorIdTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        RandomNumberGeneratorIdTranslationSpec translationSpec = new RandomNumberGeneratorIdTranslationSpec();

        assertEquals(RandomNumberGeneratorId.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = RandomNumberGeneratorIdTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        RandomNumberGeneratorIdTranslationSpec translationSpec = new RandomNumberGeneratorIdTranslationSpec();

        assertEquals(RandomNumberGeneratorIdInput.class, translationSpec.getInputObjectClass());
    }
}
