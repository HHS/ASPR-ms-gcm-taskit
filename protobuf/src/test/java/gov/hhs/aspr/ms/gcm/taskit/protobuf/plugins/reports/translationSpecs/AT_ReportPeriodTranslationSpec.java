package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.ReportPeriod;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.support.input.ReportPeriodInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.translation.specs.ReportPeriodTranslationSpec;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_ReportPeriodTranslationSpec {

    @Test
    @UnitTestConstructor(target = ReportPeriodTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new ReportPeriodTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testTranslateObject() {
        ProtobufTaskitEngine ProtobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslator(ReportsTranslator.getTranslator())
                .build();

        ReportPeriodTranslationSpec translationSpec = new ReportPeriodTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        ReportPeriod expectedAppValue = ReportPeriod.DAILY;

        ReportPeriodInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        ReportPeriod actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = ReportPeriodTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        ReportPeriodTranslationSpec translationSpec = new ReportPeriodTranslationSpec();

        assertEquals(ReportPeriod.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = ReportPeriodTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        ReportPeriodTranslationSpec translationSpec = new ReportPeriodTranslationSpec();

        assertEquals(ReportPeriodInput.class, translationSpec.getInputObjectClass());
    }
}
