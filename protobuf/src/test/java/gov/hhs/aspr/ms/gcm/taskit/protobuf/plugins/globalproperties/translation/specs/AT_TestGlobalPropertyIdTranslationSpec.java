package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.globalproperties.testsupport.TestGlobalPropertyId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.testsupport.input.TestGlobalPropertyIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.translation.GlobalPropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_TestGlobalPropertyIdTranslationSpec {

    @Test
    @UnitTestConstructor(target = TestGlobalPropertyIdTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new TestGlobalPropertyIdTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testTranslateObject() {
        ProtobufTaskitEngine ProtobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslator(GlobalPropertiesTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .build();

        TestGlobalPropertyIdTranslationSpec translationSpec = new TestGlobalPropertyIdTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        TestGlobalPropertyId expectedValue = TestGlobalPropertyId.GLOBAL_PROPERTY_1_BOOLEAN_MUTABLE;

        TestGlobalPropertyIdInput inputValue = translationSpec.translateAppObject(expectedValue);

        TestGlobalPropertyId actualValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedValue, actualValue);
    }

    @Test
    @UnitTestMethod(target = TestGlobalPropertyIdTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        TestGlobalPropertyIdTranslationSpec translationSpec = new TestGlobalPropertyIdTranslationSpec();

        assertEquals(TestGlobalPropertyId.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = TestGlobalPropertyIdTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        TestGlobalPropertyIdTranslationSpec translationSpec = new TestGlobalPropertyIdTranslationSpec();

        assertEquals(TestGlobalPropertyIdInput.class, translationSpec.getInputObjectClass());
    }

}
