package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.reports.GroupPopulationReportPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.ReportLabel;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.ReportPeriod;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.SimpleReportLabel;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.reports.input.GroupPopulationReportPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translation.GroupsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitError;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;
import gov.hhs.aspr.ms.util.errors.ContractException;

public class AT_GroupPopulationReportPluginDataTranslationSpec {

    @Test
    @UnitTestConstructor(target = GroupPopulationReportPluginDataTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new GroupPopulationReportPluginDataTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testTranslateObject() {
        ProtobufTaskitEngine protobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslator(GroupsTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(PeopleTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .build();

        GroupPopulationReportPluginDataTranslationSpec translationSpec = new GroupPopulationReportPluginDataTranslationSpec();
        translationSpec.init(protobufTaskitEngine);

        ReportLabel reportLabel = new SimpleReportLabel("property report label");
        ReportPeriod reportPeriod = ReportPeriod.DAILY;

        GroupPopulationReportPluginData.Builder builder = //
                GroupPopulationReportPluginData.builder()//
                        .setReportPeriod(reportPeriod)//
                        .setReportLabel(reportLabel);//

        GroupPopulationReportPluginData expectedAppValue = builder.build();

        GroupPopulationReportPluginDataInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        GroupPopulationReportPluginData actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
        assertEquals(expectedAppValue.toString(), actualAppValue.toString());

        // preconditions
        // version is not supported
        ContractException contractException = assertThrows(ContractException.class, () -> {
            translationSpec.translateInputObject(
                    GroupPopulationReportPluginDataInput.newBuilder().setVersion("badversion").build());
        });

        assertEquals(TaskitError.UNSUPPORTED_VERSION, contractException.getErrorType());
    }

    @Test
    @UnitTestMethod(target = GroupPopulationReportPluginDataTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        GroupPopulationReportPluginDataTranslationSpec translationSpec = new GroupPopulationReportPluginDataTranslationSpec();

        assertEquals(GroupPopulationReportPluginData.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = GroupPopulationReportPluginDataTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        GroupPopulationReportPluginDataTranslationSpec translationSpec = new GroupPopulationReportPluginDataTranslationSpec();

        assertEquals(GroupPopulationReportPluginDataInput.class, translationSpec.getInputObjectClass());
    }
}
