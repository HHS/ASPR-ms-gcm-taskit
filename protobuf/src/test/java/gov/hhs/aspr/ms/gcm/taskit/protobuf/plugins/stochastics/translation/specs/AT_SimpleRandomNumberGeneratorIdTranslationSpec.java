package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.stochastics.support.SimpleRandomNumberGeneratorId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.support.input.SimpleRandomNumberGeneratorIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
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
    public void testTranslateObject() {
        ProtobufTaskitEngine ProtobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslator(ReportsTranslator.getTranslator())
                .build();

        SimpleRandomNumberGeneratorIdTranslationSpec translationSpec = new SimpleRandomNumberGeneratorIdTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        SimpleRandomNumberGeneratorId expectedAppValue = new SimpleRandomNumberGeneratorId("report label");

        SimpleRandomNumberGeneratorIdInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        SimpleRandomNumberGeneratorId actualAppValue = translationSpec.translateInputObject(inputValue);

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
