package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.translation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.ReportLabel;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.SimpleReportLabel;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.support.input.ReportLabelInput;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitEngineManager;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngineId;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.resourcehelper.ResourceHelper;

public class IT_ReportsTranslator {
    Path basePath = ResourceHelper.getResourceDir(this.getClass());
    Path filePath = ResourceHelper.createDirectory(basePath, "test-output");

    @Test
    @UnitTestForCoverage
    public void testReportLabelTranslatorSpec() {
        String fileName = "reportLabel.json";

        ResourceHelper.createFile(filePath, fileName);

        TaskitEngineManager taskitEngineManager = TaskitEngineManager.builder()
                .addTaskitEngine(
                        ProtobufJsonTaskitEngine.builder().addTranslator(ReportsTranslator.getTranslator()).build())
                .build();

        ReportLabel expectedReportLabel = new SimpleReportLabel("report label");

        taskitEngineManager.translateAndWrite(filePath.resolve(fileName), expectedReportLabel,
                ProtobufTaskitEngineId.JSON_ENGINE_ID);

        ReportLabel actualReportLabel = taskitEngineManager.readAndTranslate(filePath.resolve(fileName),
                ReportLabelInput.class, ProtobufTaskitEngineId.JSON_ENGINE_ID);

        assertEquals(expectedReportLabel, actualReportLabel);

    }
}
