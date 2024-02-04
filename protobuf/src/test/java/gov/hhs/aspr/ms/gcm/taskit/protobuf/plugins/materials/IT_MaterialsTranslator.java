package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.plugins.materials.datamangers.MaterialsPluginData;
import gov.hhs.aspr.ms.gcm.plugins.materials.reports.BatchStatusReportPluginData;
import gov.hhs.aspr.ms.gcm.plugins.materials.reports.MaterialsProducerPropertyReportPluginData;
import gov.hhs.aspr.ms.gcm.plugins.materials.reports.MaterialsProducerResourceReportPluginData;
import gov.hhs.aspr.ms.gcm.plugins.materials.reports.StageReportPluginData;
import gov.hhs.aspr.ms.gcm.plugins.materials.testsupport.MaterialsTestPluginFactory;
import gov.hhs.aspr.ms.gcm.plugins.reports.support.ReportLabel;
import gov.hhs.aspr.ms.gcm.plugins.reports.support.SimpleReportLabel;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.data.input.MaterialsPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.reports.input.BatchStatusReportPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.reports.input.MaterialsProducerPropertyReportPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.reports.input.MaterialsProducerResourceReportPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.reports.input.StageReportPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.RegionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.ResourcesTranslator;
import gov.hhs.aspr.ms.taskit.core.TranslationController;
import gov.hhs.aspr.ms.taskit.core.TranslationEngineType;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.resourcehelper.TestResourceHelper;

public class IT_MaterialsTranslator {
    Path basePath = TestResourceHelper.getResourceDir(this.getClass());
    Path filePath = TestResourceHelper.makeOutputDir(basePath, "test-output");

    @Test
    @UnitTestForCoverage
    public void testMaterialsTranslator() {
        String fileName = "materialsPluginData.json";

        TestResourceHelper.createOutputFile(filePath, fileName);

        TranslationController translatorController = TranslationController.builder()
                .addTranslationEngine(ProtobufTranslationEngine.builder()
                        .addTranslator(MaterialsTranslator.getTranslator())
                        .addTranslator(PropertiesTranslator.getTranslator())
                        .addTranslator(ResourcesTranslator.getTranslator())
                        .addTranslator(RegionsTranslator.getTranslator())
                        .addTranslator(PeopleTranslator.getTranslator())
                        .addTranslator(ReportsTranslator.getTranslator())
                        .build())
                .addInputFilePath(filePath.resolve(fileName), MaterialsPluginDataInput.class,
                        TranslationEngineType.PROTOBUF)
                .addOutputFilePath(filePath.resolve(fileName), MaterialsPluginData.class,
                        TranslationEngineType.PROTOBUF)
                .build();

        int numBatches = 50;
        int numStages = 10;
        int numBatchesInStage = 30;
        long seed = 524805676405822016L;

        MaterialsPluginData expectedPluginData = MaterialsTestPluginFactory.getStandardMaterialsPluginData(numBatches,
                numStages, numBatchesInStage, seed);
        translatorController.writeOutput(expectedPluginData);

        translatorController.readInput();
        MaterialsPluginData actualPluginData = translatorController.getFirstObject(MaterialsPluginData.class);

        assertEquals(expectedPluginData, actualPluginData);
        assertEquals(expectedPluginData.toString(), actualPluginData.toString());
    }

    @Test
    @UnitTestForCoverage
    public void testBatchStatusReportPluginDataTranslatorSpec() {
        String fileName = "batchStatusReport.json";

        TestResourceHelper.createOutputFile(filePath, fileName);

        TranslationController translatorController = TranslationController.builder()
                .addTranslationEngine(ProtobufTranslationEngine.builder()
                        .addTranslator(MaterialsTranslator.getTranslator())
                        .addTranslator(ReportsTranslator.getTranslator())
                        .addTranslator(PropertiesTranslator.getTranslator())
                        .addTranslator(ResourcesTranslator.getTranslator())
                        .addTranslator(RegionsTranslator.getTranslator())
                        .addTranslator(PeopleTranslator.getTranslator())
                        .build())
                .addInputFilePath(filePath.resolve(fileName), BatchStatusReportPluginDataInput.class,
                        TranslationEngineType.PROTOBUF)
                .addOutputFilePath(filePath.resolve(fileName), BatchStatusReportPluginData.class,
                        TranslationEngineType.PROTOBUF)
                .build();

        BatchStatusReportPluginData.Builder builder = BatchStatusReportPluginData.builder();

        ReportLabel reportLabel = new SimpleReportLabel("batch status report label");

        builder.setReportLabel(reportLabel);

        BatchStatusReportPluginData expectedPluginData = builder.build();

        translatorController.writeOutput(expectedPluginData);

        translatorController.readInput();

        BatchStatusReportPluginData actualPluginData = translatorController
                .getFirstObject(BatchStatusReportPluginData.class);

        assertEquals(expectedPluginData, actualPluginData);
        assertEquals(expectedPluginData.toString(), actualPluginData.toString());
    }

    @Test
    @UnitTestForCoverage
    public void testMaterialsProducerPropertyReportPluginDataTranslatorSpec() {
        String fileName = "materialsProducerPropertyReport.json";

        TestResourceHelper.createOutputFile(filePath, fileName);

        TranslationController translatorController = TranslationController.builder()
                .addTranslationEngine(ProtobufTranslationEngine.builder()
                        .addTranslator(MaterialsTranslator.getTranslator())
                        .addTranslator(ReportsTranslator.getTranslator())
                        .addTranslator(PropertiesTranslator.getTranslator())
                        .addTranslator(ResourcesTranslator.getTranslator())
                        .addTranslator(RegionsTranslator.getTranslator())
                        .addTranslator(PeopleTranslator.getTranslator())
                        .build())
                .addInputFilePath(filePath.resolve(fileName), MaterialsProducerPropertyReportPluginDataInput.class,
                        TranslationEngineType.PROTOBUF)
                .addOutputFilePath(filePath.resolve(fileName), MaterialsProducerPropertyReportPluginData.class,
                        TranslationEngineType.PROTOBUF)
                .build();

        MaterialsProducerPropertyReportPluginData.Builder builder = MaterialsProducerPropertyReportPluginData.builder();
        ReportLabel reportLabel = new SimpleReportLabel("materials producer property report report label");

        builder.setReportLabel(reportLabel);

        MaterialsProducerPropertyReportPluginData expectedPluginData = builder.build();

        translatorController.writeOutput(expectedPluginData);

        translatorController.readInput();

        MaterialsProducerPropertyReportPluginData actualPluginData = translatorController
                .getFirstObject(MaterialsProducerPropertyReportPluginData.class);

        assertEquals(expectedPluginData, actualPluginData);
        assertEquals(expectedPluginData.toString(), actualPluginData.toString());
    }

    @Test
    @UnitTestForCoverage
    public void testMaterialsProducerResourceReportPluginDataTranslatorSpec() {
        String fileName = "materialsProducerResourceReport.json";

        TestResourceHelper.createOutputFile(filePath, fileName);

        TranslationController translatorController = TranslationController.builder()
                .addTranslationEngine(ProtobufTranslationEngine.builder()
                        .addTranslator(MaterialsTranslator.getTranslator())
                        .addTranslator(ReportsTranslator.getTranslator())
                        .addTranslator(PropertiesTranslator.getTranslator())
                        .addTranslator(ResourcesTranslator.getTranslator())
                        .addTranslator(RegionsTranslator.getTranslator())
                        .addTranslator(PeopleTranslator.getTranslator())
                        .build())
                .addInputFilePath(filePath.resolve(fileName), MaterialsProducerResourceReportPluginDataInput.class,
                        TranslationEngineType.PROTOBUF)
                .addOutputFilePath(filePath.resolve(fileName), MaterialsProducerResourceReportPluginData.class,
                        TranslationEngineType.PROTOBUF)
                .build();

        MaterialsProducerResourceReportPluginData.Builder builder = MaterialsProducerResourceReportPluginData.builder();

        ReportLabel reportLabel = new SimpleReportLabel("materials producer resource report label");

        builder.setReportLabel(reportLabel);

        MaterialsProducerResourceReportPluginData expectedPluginData = builder.build();

        translatorController.writeOutput(expectedPluginData);
        translatorController.readInput();

        MaterialsProducerResourceReportPluginData actualPluginData = translatorController
                .getFirstObject(MaterialsProducerResourceReportPluginData.class);

        assertEquals(expectedPluginData, actualPluginData);
        assertEquals(expectedPluginData.toString(), actualPluginData.toString());
    }

    @Test
    @UnitTestForCoverage
    public void testStageReportPluginDataTranslatorSpec() {
        String fileName = "stageReport.json";

        TestResourceHelper.createOutputFile(filePath, fileName);

        TranslationController translatorController = TranslationController.builder()
                .addTranslationEngine(ProtobufTranslationEngine.builder()
                        .addTranslator(MaterialsTranslator.getTranslator())
                        .addTranslator(ReportsTranslator.getTranslator())
                        .addTranslator(PropertiesTranslator.getTranslator())
                        .addTranslator(ResourcesTranslator.getTranslator())
                        .addTranslator(RegionsTranslator.getTranslator())
                        .addTranslator(PeopleTranslator.getTranslator())
                        .build())
                .addInputFilePath(filePath.resolve(fileName), StageReportPluginDataInput.class,
                        TranslationEngineType.PROTOBUF)
                .addOutputFilePath(filePath.resolve(fileName), StageReportPluginData.class,
                        TranslationEngineType.PROTOBUF)
                .build();

        StageReportPluginData.Builder builder = StageReportPluginData.builder();

        ReportLabel reportLabel = new SimpleReportLabel("stage report label");

        builder.setReportLabel(reportLabel);

        StageReportPluginData expectedPluginData = builder.build();

        translatorController.writeOutput(expectedPluginData);
        translatorController.readInput();

        StageReportPluginData actualPluginData = translatorController.getFirstObject(StageReportPluginData.class);

        assertEquals(expectedPluginData, actualPluginData);
        assertEquals(expectedPluginData.toString(), actualPluginData.toString());
    }

}
