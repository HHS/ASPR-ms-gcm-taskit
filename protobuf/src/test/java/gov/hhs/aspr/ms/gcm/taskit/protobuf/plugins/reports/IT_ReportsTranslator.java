package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.ReportLabel;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.SimpleReportLabel;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.support.input.ReportLabelInput;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitEngineManager;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitEngineId;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
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

        TaskitEngineManager translatorController = TaskitEngineManager.builder()
                .addTaskitEngine(
                        IProtobufTaskitEngineBuilder().addTranslator(ReportsTranslator.getTranslator()).build())
                .addInputFilePath(filePath.resolve(fileName), ReportLabelInput.class, TaskitEngineId.PROTOBUF)
                .build();

        ReportLabel expecetdReportLabel = new SimpleReportLabel("report label");

        translatorController.writeOutput(expecetdReportLabel, filePath.resolve(fileName),
                TaskitEngineId.PROTOBUF);

        translatorController.readInput();

        ReportLabel actualReportLabel = translatorController.getFirstObject(ReportLabel.class);

        assertEquals(expecetdReportLabel, actualReportLabel);

    }
}
