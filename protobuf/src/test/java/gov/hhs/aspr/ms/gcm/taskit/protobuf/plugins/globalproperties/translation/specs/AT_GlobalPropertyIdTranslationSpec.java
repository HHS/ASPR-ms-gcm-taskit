package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.globalproperties.support.GlobalPropertyId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.globalproperties.testsupport.TestGlobalPropertyId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.support.input.GlobalPropertyIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.translation.GlobalPropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_GlobalPropertyIdTranslationSpec {

    @Test
    @UnitTestConstructor(target = GlobalPropertyIdTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new GlobalPropertyIdTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testTranslateObject() {
        ProtobufTaskitEngine ProtobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslator(GlobalPropertiesTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .build();

        GlobalPropertyIdTranslationSpec translationSpec = new GlobalPropertyIdTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        GlobalPropertyId expectedAppValue = TestGlobalPropertyId.GLOBAL_PROPERTY_1_BOOLEAN_MUTABLE;

        GlobalPropertyIdInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        GlobalPropertyId actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = GlobalPropertyIdTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        GlobalPropertyIdTranslationSpec translationSpec = new GlobalPropertyIdTranslationSpec();

        assertEquals(GlobalPropertyId.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = GlobalPropertyIdTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        GlobalPropertyIdTranslationSpec translationSpec = new GlobalPropertyIdTranslationSpec();

        assertEquals(GlobalPropertyIdInput.class, translationSpec.getInputObjectClass());
    }

}
