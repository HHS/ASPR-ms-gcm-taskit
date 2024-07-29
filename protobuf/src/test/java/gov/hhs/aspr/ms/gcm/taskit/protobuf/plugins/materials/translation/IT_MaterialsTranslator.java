package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.materials.datamangers.MaterialsPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.materials.reports.BatchStatusReportPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.materials.reports.MaterialsProducerPropertyReportPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.materials.reports.MaterialsProducerResourceReportPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.materials.reports.StageReportPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.materials.testsupport.MaterialsTestPluginFactory;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.ReportLabel;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.SimpleReportLabel;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.data.input.MaterialsPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.reports.input.BatchStatusReportPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.reports.input.MaterialsProducerPropertyReportPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.reports.input.MaterialsProducerResourceReportPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.reports.input.StageReportPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translation.MaterialsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translation.RegionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translation.ResourcesTranslator;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitEngineManager;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitEngineId;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.resourcehelper.ResourceHelper;

public class IT_MaterialsTranslator {
    Path basePath = ResourceHelper.getResourceDir(this.getClass());
    Path filePath = ResourceHelper.createDirectory(basePath, "test-output");

    @Test
    @UnitTestForCoverage
    public void testMaterialsTranslator() {
        String fileName = "materialsPluginData.json";

        ResourceHelper.createFile(filePath, fileName);

        TaskitEngineManager taskitEngineManager = TaskitEngineManager.builder()
                .addTaskitEngine(ProtobufJsonTaskitEngine.builder()
                        .addTranslator(MaterialsTranslator.getTranslator())
                        .addTranslator(PropertiesTranslator.getTranslator())
                        .addTranslator(ResourcesTranslator.getTranslator())
                        .addTranslator(RegionsTranslator.getTranslator())
                        .addTranslator(PeopleTranslator.getTranslator())
                        .addTranslator(ReportsTranslator.getTranslator())
                        .build())
                .addInputFilePath(filePath.resolve(fileName), MaterialsPluginDataInput.class,
                        ProtobufTaskitEngineId.JSON_ENGINE_ID)
                .build();

        int numBatches = 50;
        int numStages = 10;
        int numBatchesInStage = 30;
        long seed = 524805676405822016L;

        MaterialsPluginData expectedPluginData = MaterialsTestPluginFactory.getStandardMaterialsPluginData(numBatches,
                numStages, numBatchesInStage, seed);
        taskitEngineManager.translateAndWrite(expectedPluginData, filePath.resolve(fileName),
                ProtobufTaskitEngineId.JSON_ENGINE_ID);

        taskitEngineManager.readInput();
        MaterialsPluginData actualPluginData = taskitEngineManager.getFirstObject(MaterialsPluginData.class);

        assertEquals(expectedPluginData, actualPluginData);
        assertEquals(expectedPluginData.toString(), actualPluginData.toString());
    }

    @Test
    @UnitTestForCoverage
    public void testBatchStatusReportPluginDataTranslatorSpec() {
        String fileName = "batchStatusReport.json";

        ResourceHelper.createFile(filePath, fileName);

        TaskitEngineManager taskitEngineManager = TaskitEngineManager.builder()
                .addTaskitEngine(ProtobufJsonTaskitEngine.builder()
                        .addTranslator(MaterialsTranslator.getTranslator())
                        .addTranslator(ReportsTranslator.getTranslator())
                        .addTranslator(PropertiesTranslator.getTranslator())
                        .addTranslator(ResourcesTranslator.getTranslator())
                        .addTranslator(RegionsTranslator.getTranslator())
                        .addTranslator(PeopleTranslator.getTranslator())
                        .build())
                .addInputFilePath(filePath.resolve(fileName), BatchStatusReportPluginDataInput.class,
                        ProtobufTaskitEngineId.JSON_ENGINE_ID)
                .build();

        BatchStatusReportPluginData.Builder builder = BatchStatusReportPluginData.builder();

        ReportLabel reportLabel = new SimpleReportLabel("batch status report label");

        builder.setReportLabel(reportLabel);

        BatchStatusReportPluginData expectedPluginData = builder.build();

        taskitEngineManager.translateAndWrite(expectedPluginData, filePath.resolve(fileName),
                ProtobufTaskitEngineId.JSON_ENGINE_ID);

        taskitEngineManager.readInput();

        BatchStatusReportPluginData actualPluginData = taskitEngineManager
                .getFirstObject(BatchStatusReportPluginData.class);

        assertEquals(expectedPluginData, actualPluginData);
        assertEquals(expectedPluginData.toString(), actualPluginData.toString());
    }

    @Test
    @UnitTestForCoverage
    public void testMaterialsProducerPropertyReportPluginDataTranslatorSpec() {
        String fileName = "materialsProducerPropertyReport.json";

        ResourceHelper.createFile(filePath, fileName);

        TaskitEngineManager taskitEngineManager = TaskitEngineManager.builder()
                .addTaskitEngine(ProtobufJsonTaskitEngine.builder()
                        .addTranslator(MaterialsTranslator.getTranslator())
                        .addTranslator(ReportsTranslator.getTranslator())
                        .addTranslator(PropertiesTranslator.getTranslator())
                        .addTranslator(ResourcesTranslator.getTranslator())
                        .addTranslator(RegionsTranslator.getTranslator())
                        .addTranslator(PeopleTranslator.getTranslator())
                        .build())
                .addInputFilePath(filePath.resolve(fileName), MaterialsProducerPropertyReportPluginDataInput.class,
                        ProtobufTaskitEngineId.JSON_ENGINE_ID)
                .build();

        MaterialsProducerPropertyReportPluginData.Builder builder = MaterialsProducerPropertyReportPluginData.builder();
        ReportLabel reportLabel = new SimpleReportLabel("materials producer property report report label");

        builder.setReportLabel(reportLabel);

        MaterialsProducerPropertyReportPluginData expectedPluginData = builder.build();

        taskitEngineManager.translateAndWrite(expectedPluginData, filePath.resolve(fileName),
                ProtobufTaskitEngineId.JSON_ENGINE_ID);

        taskitEngineManager.readInput();

        MaterialsProducerPropertyReportPluginData actualPluginData = taskitEngineManager
                .getFirstObject(MaterialsProducerPropertyReportPluginData.class);

        assertEquals(expectedPluginData, actualPluginData);
        assertEquals(expectedPluginData.toString(), actualPluginData.toString());
    }

    @Test
    @UnitTestForCoverage
    public void testMaterialsProducerResourceReportPluginDataTranslatorSpec() {
        String fileName = "materialsProducerResourceReport.json";

        ResourceHelper.createFile(filePath, fileName);

        TaskitEngineManager taskitEngineManager = TaskitEngineManager.builder()
                .addTaskitEngine(ProtobufJsonTaskitEngine.builder()
                        .addTranslator(MaterialsTranslator.getTranslator())
                        .addTranslator(ReportsTranslator.getTranslator())
                        .addTranslator(PropertiesTranslator.getTranslator())
                        .addTranslator(ResourcesTranslator.getTranslator())
                        .addTranslator(RegionsTranslator.getTranslator())
                        .addTranslator(PeopleTranslator.getTranslator())
                        .build())
                .addInputFilePath(filePath.resolve(fileName), MaterialsProducerResourceReportPluginDataInput.class,
                        ProtobufTaskitEngineId.JSON_ENGINE_ID)
                .build();

        MaterialsProducerResourceReportPluginData.Builder builder = MaterialsProducerResourceReportPluginData.builder();

        ReportLabel reportLabel = new SimpleReportLabel("materials producer resource report label");

        builder.setReportLabel(reportLabel);

        MaterialsProducerResourceReportPluginData expectedPluginData = builder.build();

        taskitEngineManager.translateAndWrite(expectedPluginData, filePath.resolve(fileName),
                ProtobufTaskitEngineId.JSON_ENGINE_ID);
        taskitEngineManager.readInput();

        MaterialsProducerResourceReportPluginData actualPluginData = taskitEngineManager
                .getFirstObject(MaterialsProducerResourceReportPluginData.class);

        assertEquals(expectedPluginData, actualPluginData);
        assertEquals(expectedPluginData.toString(), actualPluginData.toString());
    }

    @Test
    @UnitTestForCoverage
    public void testStageReportPluginDataTranslatorSpec() {
        String fileName = "stageReport.json";

        ResourceHelper.createFile(filePath, fileName);

        TaskitEngineManager taskitEngineManager = TaskitEngineManager.builder()
                .addTaskitEngine(ProtobufJsonTaskitEngine.builder()
                        .addTranslator(MaterialsTranslator.getTranslator())
                        .addTranslator(ReportsTranslator.getTranslator())
                        .addTranslator(PropertiesTranslator.getTranslator())
                        .addTranslator(ResourcesTranslator.getTranslator())
                        .addTranslator(RegionsTranslator.getTranslator())
                        .addTranslator(PeopleTranslator.getTranslator())
                        .build())
                .addInputFilePath(filePath.resolve(fileName), StageReportPluginDataInput.class,
                        ProtobufTaskitEngineId.JSON_ENGINE_ID)
                .build();

        StageReportPluginData.Builder builder = StageReportPluginData.builder();

        ReportLabel reportLabel = new SimpleReportLabel("stage report label");

        builder.setReportLabel(reportLabel);

        StageReportPluginData expectedPluginData = builder.build();

        taskitEngineManager.translateAndWrite(expectedPluginData, filePath.resolve(fileName),
                ProtobufTaskitEngineId.JSON_ENGINE_ID);
        taskitEngineManager.readInput();

        StageReportPluginData actualPluginData = taskitEngineManager.getFirstObject(StageReportPluginData.class);

        assertEquals(expectedPluginData, actualPluginData);
        assertEquals(expectedPluginData.toString(), actualPluginData.toString());
    }

}
