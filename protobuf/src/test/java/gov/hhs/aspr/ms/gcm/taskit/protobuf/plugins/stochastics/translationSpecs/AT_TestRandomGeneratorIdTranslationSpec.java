package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.stochastics.testsupport.TestRandomGeneratorId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.StochasticsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.testsupport.input.TestRandomGeneratorIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationEngine;
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
    public void testConvertObject() {
        ProtobufTranslationEngine protobufTranslationEngine = ProtobufTranslationEngine.builder()
                .addTranslator(StochasticsTranslator.getTranslator())
                .build();

        TestRandomGeneratorIdTranslationSpec translationSpec = new TestRandomGeneratorIdTranslationSpec();
        translationSpec.init(protobufTranslationEngine);

        TestRandomGeneratorId expectedAppValue = TestRandomGeneratorId.BLITZEN;

        TestRandomGeneratorIdInput inputValue = translationSpec.convertAppObject(expectedAppValue);

        TestRandomGeneratorId actualAppValue = translationSpec.convertInputObject(inputValue);

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
