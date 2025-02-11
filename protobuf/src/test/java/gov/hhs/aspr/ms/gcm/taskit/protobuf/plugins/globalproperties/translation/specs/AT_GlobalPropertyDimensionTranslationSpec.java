package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.globalproperties.support.GlobalPropertyDimension;
import gov.hhs.aspr.ms.gcm.simulation.plugins.globalproperties.support.GlobalPropertyDimensionData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.globalproperties.testsupport.TestGlobalPropertyId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.support.input.GlobalPropertyDimensionDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.translation.GlobalPropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.translation.ReportsTranslator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_GlobalPropertyDimensionTranslationSpec {

    @Test
    @UnitTestConstructor(target = GlobalPropertyDimensionDataTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new GlobalPropertyDimensionDataTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testTranslateObject() {
        ProtobufTaskitEngine protobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslator(GlobalPropertiesTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .build();

        GlobalPropertyDimensionDataTranslationSpec translationSpec = new GlobalPropertyDimensionDataTranslationSpec();
        translationSpec.init(protobufTaskitEngine);

        GlobalPropertyDimensionData expectedAppValue = GlobalPropertyDimensionData.builder()
                .setAssignmentTime(0)
                .setGlobalPropertyId(TestGlobalPropertyId.GLOBAL_PROPERTY_3_DOUBLE_MUTABLE)
                .addValue("1", 10.0)
                .addValue("2", 1250.2)
                .addValue("3", 15000.5)
                .build();

        GlobalPropertyDimensionDataInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        GlobalPropertyDimensionData actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = GlobalPropertyDimensionDataTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        GlobalPropertyDimensionDataTranslationSpec translationSpec = new GlobalPropertyDimensionDataTranslationSpec();

        assertEquals(GlobalPropertyDimension.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = GlobalPropertyDimensionDataTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        GlobalPropertyDimensionDataTranslationSpec translationSpec = new GlobalPropertyDimensionDataTranslationSpec();

        assertEquals(GlobalPropertyDimensionDataInput.class, translationSpec.getInputObjectClass());
    }

}
