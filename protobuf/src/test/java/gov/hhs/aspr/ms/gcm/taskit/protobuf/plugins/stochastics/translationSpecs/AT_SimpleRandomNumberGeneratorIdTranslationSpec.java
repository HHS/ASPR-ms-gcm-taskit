package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.stochastics.support.SimpleRandomNumberGeneratorId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.support.input.SimpleRandomNumberGeneratorIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_SimpleRandomNumberGeneratorIdTranslationSpec {

    @Test
    @UnitTestConstructor(target = SimpleRandomNumberGeneratorIdTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new SimpleRandomNumberGeneratorIdTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testConvertObject() {
        ProtobufTranslationEngine protobufTranslationEngine = ProtobufTranslationEngine.builder()
                .addTranslator(ReportsTranslator.getTranslator())
                .build();

        SimpleRandomNumberGeneratorIdTranslationSpec translationSpec = new SimpleRandomNumberGeneratorIdTranslationSpec();
        translationSpec.init(protobufTranslationEngine);

        SimpleRandomNumberGeneratorId expectedAppValue = new SimpleRandomNumberGeneratorId("report label");

        SimpleRandomNumberGeneratorIdInput inputValue = translationSpec.convertAppObject(expectedAppValue);

        SimpleRandomNumberGeneratorId actualAppValue = translationSpec.convertInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = SimpleRandomNumberGeneratorIdTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        SimpleRandomNumberGeneratorIdTranslationSpec translationSpec = new SimpleRandomNumberGeneratorIdTranslationSpec();

        assertEquals(SimpleRandomNumberGeneratorId.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = SimpleRandomNumberGeneratorIdTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        SimpleRandomNumberGeneratorIdTranslationSpec translationSpec = new SimpleRandomNumberGeneratorIdTranslationSpec();

        assertEquals(SimpleRandomNumberGeneratorIdInput.class, translationSpec.getInputObjectClass());
    }
}
