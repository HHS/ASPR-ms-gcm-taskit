package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.materials.reports.StageReportPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.ReportLabel;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.SimpleReportLabel;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.MaterialsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.reports.input.StageReportPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.RegionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.ResourcesTranslator;
import gov.hhs.aspr.ms.taskit.core.CoreTranslationError;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationEngine;
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
    public void testConvertObject() {
        ProtobufTranslationEngine protobufTranslationEngine = ProtobufTranslationEngine.builder()
                .addTranslator(MaterialsTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(ResourcesTranslator.getTranslator())
                .addTranslator(RegionsTranslator.getTranslator())
                .addTranslator(PeopleTranslator.getTranslator())
                .build();

        StageReportPluginDataTranslationSpec translationSpec = new StageReportPluginDataTranslationSpec();
        translationSpec.init(protobufTranslationEngine);

        StageReportPluginData.Builder builder = StageReportPluginData.builder();

        ReportLabel reportLabel = new SimpleReportLabel("stage report label");

        builder.setReportLabel(reportLabel);

        StageReportPluginData expectedAppValue = builder.build();

        StageReportPluginDataInput inputValue = translationSpec.convertAppObject(expectedAppValue);

        StageReportPluginData actualAppValue = translationSpec.convertInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
        assertEquals(expectedAppValue.toString(), actualAppValue.toString());

        // preconditions
        // version is not supported
        ContractException contractException = assertThrows(ContractException.class, () -> {
            translationSpec.convertInputObject(StageReportPluginDataInput.newBuilder().setVersion("badversion").build());
        });

        assertEquals(CoreTranslationError.UNSUPPORTED_VERSION, contractException.getErrorType());
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
