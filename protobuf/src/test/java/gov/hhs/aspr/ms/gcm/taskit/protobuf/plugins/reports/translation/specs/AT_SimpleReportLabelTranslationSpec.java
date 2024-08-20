package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.SimpleReportLabel;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.support.input.SimpleReportLabelInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.translation.ReportsTranslator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_SimpleReportLabelTranslationSpec {

    @Test
    @UnitTestConstructor(target = SimpleReportLabelTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new SimpleReportLabelTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testTranslateObject() {
        ProtobufTaskitEngine protobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslator(ReportsTranslator.getTranslator())
                .build();

        SimpleReportLabelTranslationSpec translationSpec = new SimpleReportLabelTranslationSpec();
        translationSpec.init(protobufTaskitEngine);

        SimpleReportLabel expectedAppValue = new SimpleReportLabel("report label");

        SimpleReportLabelInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        SimpleReportLabel actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = SimpleReportLabelTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        SimpleReportLabelTranslationSpec translationSpec = new SimpleReportLabelTranslationSpec();

        assertEquals(SimpleReportLabel.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = SimpleReportLabelTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        SimpleReportLabelTranslationSpec translationSpec = new SimpleReportLabelTranslationSpec();

        assertEquals(SimpleReportLabelInput.class, translationSpec.getInputObjectClass());
    }
}
