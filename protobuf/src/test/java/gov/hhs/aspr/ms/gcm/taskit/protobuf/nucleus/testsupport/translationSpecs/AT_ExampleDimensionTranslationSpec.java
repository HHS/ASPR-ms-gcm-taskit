package gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.testsupport.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.NucleusTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.testsupport.ExampleDimension;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.testsupport.input.ExampleDimensionInput;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_ExampleDimensionTranslationSpec {

    @Test
    @UnitTestConstructor(target = ExampleDimensionTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new ExampleDimensionTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testConvertObject() {

        ProtobufTranslationEngine protobufTranslationEngine = ProtobufTranslationEngine.builder()
                .addTranslator(NucleusTranslator.getTranslator())
                .build();

        ExampleDimensionTranslationSpec translationSpec = new ExampleDimensionTranslationSpec();
        translationSpec.init(protobufTranslationEngine);

        ExampleDimension expectedAppValue = new ExampleDimension("test");

        ExampleDimensionInput inputValue = translationSpec.convertAppObject(expectedAppValue);

        ExampleDimension actualAppValue = translationSpec.convertInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = ExampleDimensionTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        ExampleDimensionTranslationSpec translationSpec = new ExampleDimensionTranslationSpec();

        assertEquals(ExampleDimension.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = ExampleDimensionTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        ExampleDimensionTranslationSpec translationSpec = new ExampleDimensionTranslationSpec();

        assertEquals(ExampleDimensionInput.class, translationSpec.getInputObjectClass());
    }
}
