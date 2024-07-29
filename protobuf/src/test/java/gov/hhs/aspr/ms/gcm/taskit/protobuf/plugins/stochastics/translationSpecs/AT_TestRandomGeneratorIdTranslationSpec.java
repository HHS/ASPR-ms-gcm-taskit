package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.stochastics.testsupport.TestRandomGeneratorId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.testsupport.input.TestRandomGeneratorIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.translation.StochasticsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.translation.specs.TestRandomGeneratorIdTranslationSpec;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_TestRandomGeneratorIdTranslationSpec {

    @Test
    @UnitTestConstructor(target = TestRandomGeneratorIdTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new TestRandomGeneratorIdTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testtranslateObject() {
        ProtobufTaskitEngine ProtobufTaskitEngine = IProtobufTaskitEngineBuilder()
                .addTranslator(StochasticsTranslator.getTranslator())
                .build();

        TestRandomGeneratorIdTranslationSpec translationSpec = new TestRandomGeneratorIdTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        TestRandomGeneratorId expectedAppValue = TestRandomGeneratorId.BLITZEN;

        TestRandomGeneratorIdInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        TestRandomGeneratorId actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = TestRandomGeneratorIdTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        TestRandomGeneratorIdTranslationSpec translationSpec = new TestRandomGeneratorIdTranslationSpec();

        assertEquals(TestRandomGeneratorId.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = TestRandomGeneratorIdTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        TestRandomGeneratorIdTranslationSpec translationSpec = new TestRandomGeneratorIdTranslationSpec();

        assertEquals(TestRandomGeneratorIdInput.class, translationSpec.getInputObjectClass());
    }
}
