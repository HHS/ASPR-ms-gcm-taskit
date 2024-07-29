package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.globalproperties.support.GlobalPropertyDimension;
import gov.hhs.aspr.ms.gcm.simulation.plugins.globalproperties.testsupport.TestGlobalPropertyId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.support.input.GlobalPropertyDimensionInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.translation.GlobalPropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_GlobalPropertyDimensionTranslationSpec {

    @Test
    @UnitTestConstructor(target = GlobalPropertyDimensionTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new GlobalPropertyDimensionTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testTranslateObject() {
        ProtobufTaskitEngine protobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslator(GlobalPropertiesTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .build();

        GlobalPropertyDimensionTranslationSpec translationSpec = new GlobalPropertyDimensionTranslationSpec();
        translationSpec.init(protobufTaskitEngine);

        GlobalPropertyDimension expectedAppValue = GlobalPropertyDimension.builder()
                .setAssignmentTime(0)
                .setGlobalPropertyId(TestGlobalPropertyId.GLOBAL_PROPERTY_3_DOUBLE_MUTABLE)
                .addValue(10.0)
                .addValue(1250.2)
                .addValue(15000.5)
                .build();

        GlobalPropertyDimensionInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        GlobalPropertyDimension actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = GlobalPropertyDimensionTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        GlobalPropertyDimensionTranslationSpec translationSpec = new GlobalPropertyDimensionTranslationSpec();

        assertEquals(GlobalPropertyDimension.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = GlobalPropertyDimensionTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        GlobalPropertyDimensionTranslationSpec translationSpec = new GlobalPropertyDimensionTranslationSpec();

        assertEquals(GlobalPropertyDimensionInput.class, translationSpec.getInputObjectClass());
    }

}
