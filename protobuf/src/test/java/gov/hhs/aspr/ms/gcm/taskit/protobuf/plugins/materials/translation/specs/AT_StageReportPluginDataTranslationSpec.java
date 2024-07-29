package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.materials.reports.StageReportPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.ReportLabel;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.SimpleReportLabel;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.reports.input.StageReportPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translation.MaterialsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translation.specs.StageReportPluginDataTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translation.RegionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translation.ResourcesTranslator;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitError;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;
import gov.hhs.aspr.ms.util.errors.ContractException;

public class AT_StageReportPluginDataTranslationSpec {

    @Test
    @UnitTestConstructor(target = StageReportPluginDataTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new StageReportPluginDataTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testTranslateObject() {
        ProtobufTaskitEngine ProtobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslator(MaterialsTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(ResourcesTranslator.getTranslator())
                .addTranslator(RegionsTranslator.getTranslator())
                .addTranslator(PeopleTranslator.getTranslator())
                .build();

        StageReportPluginDataTranslationSpec translationSpec = new StageReportPluginDataTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        StageReportPluginData.Builder builder = StageReportPluginData.builder();

        ReportLabel reportLabel = new SimpleReportLabel("stage report label");

        builder.setReportLabel(reportLabel);

        StageReportPluginData expectedAppValue = builder.build();

        StageReportPluginDataInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        StageReportPluginData actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
        assertEquals(expectedAppValue.toString(), actualAppValue.toString());

        // preconditions
        // version is not supported
        ContractException contractException = assertThrows(ContractException.class, () -> {
            translationSpec
                    .translateInputObject(StageReportPluginDataInput.newBuilder().setVersion("badversion").build());
        });

        assertEquals(TaskitError.UNSUPPORTED_VERSION, contractException.getErrorType());
    }

    @Test
    @UnitTestMethod(target = StageReportPluginDataTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        StageReportPluginDataTranslationSpec translationSpec = new StageReportPluginDataTranslationSpec();

        assertEquals(StageReportPluginData.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = StageReportPluginDataTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        StageReportPluginDataTranslationSpec translationSpec = new StageReportPluginDataTranslationSpec();

        assertEquals(StageReportPluginDataInput.class, translationSpec.getInputObjectClass());
    }
}
