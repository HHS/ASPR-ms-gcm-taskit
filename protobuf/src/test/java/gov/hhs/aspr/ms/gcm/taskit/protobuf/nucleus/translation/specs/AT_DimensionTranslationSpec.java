package gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.nucleus.Dimension;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.input.DimensionInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.testsupport.ExampleDimension;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.translation.NucleusTranslator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_DimensionTranslationSpec {

    @Test
    @UnitTestConstructor(target = DimensionTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new DimensionTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testTranslateObject() {
        ProtobufTaskitEngine ProtobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslator(NucleusTranslator.getTranslator())
                .build();

        DimensionTranslationSpec translationSpec = new DimensionTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        Dimension expectedAppValue = new ExampleDimension("test");

        DimensionInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        Dimension actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = DimensionTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        DimensionTranslationSpec translationSpec = new DimensionTranslationSpec();

        assertEquals(Dimension.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = DimensionTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        DimensionTranslationSpec translationSpec = new DimensionTranslationSpec();

        assertEquals(DimensionInput.class, translationSpec.getInputObjectClass());
    }
}
