package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.ReportLabel;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.SimpleReportLabel;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.support.input.ReportLabelInput;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_ReportLabelTranslationSpec {

    @Test
    @UnitTestConstructor(target = ReportLabelTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new ReportLabelTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testTranslateObject() {
        ProtobufTaskitEngine ProtobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslator(ReportsTranslator.getTranslator())
                .build();

        ReportLabelTranslationSpec translationSpec = new ReportLabelTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        ReportLabel expectedAppValue = new SimpleReportLabel("report label");

        ReportLabelInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        ReportLabel actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = ReportLabelTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        ReportLabelTranslationSpec translationSpec = new ReportLabelTranslationSpec();

        assertEquals(ReportLabel.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = ReportLabelTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        ReportLabelTranslationSpec translationSpec = new ReportLabelTranslationSpec();

        assertEquals(ReportLabelInput.class, translationSpec.getInputObjectClass());
    }
}
