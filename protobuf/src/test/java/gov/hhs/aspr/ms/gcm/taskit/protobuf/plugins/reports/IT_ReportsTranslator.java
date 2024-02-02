package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.plugins.reports.support.ReportLabel;
import gov.hhs.aspr.ms.gcm.plugins.reports.support.SimpleReportLabel;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.support.input.ReportLabelInput;
import gov.hhs.aspr.ms.taskit.core.TranslationController;
import gov.hhs.aspr.ms.taskit.core.TranslationEngineType;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationEngine;
import util.annotations.UnitTestForCoverage;
import util.resourcehelper.TestResourceHelper;

public class IT_ReportsTranslator {
    Path basePath = TestResourceHelper.getResourceDir(this.getClass());
    Path filePath = TestResourceHelper.makeOutputDir(basePath, "test-output");

    @Test
    @UnitTestForCoverage
    public void testReportLabelTranslatorSpec() {
        String fileName = "reportLabel.json";

        TestResourceHelper.createOutputFile(filePath, fileName);

        TranslationController translatorController = TranslationController.builder()
                .addTranslationEngine(
                        ProtobufTranslationEngine.builder().addTranslator(ReportsTranslator.getTranslator()).build())
                .addInputFilePath(filePath.resolve(fileName), ReportLabelInput.class, TranslationEngineType.PROTOBUF)
                .addOutputFilePath(filePath.resolve(fileName), ReportLabel.class, TranslationEngineType.PROTOBUF)
                .build();

        ReportLabel expecetdReportLabel = new SimpleReportLabel("report label");

        translatorController.writeOutput(expecetdReportLabel);

        translatorController.readInput();

        ReportLabel actualReportLabel = translatorController.getFirstObject(ReportLabel.class);

        assertEquals(expecetdReportLabel, actualReportLabel);

    }
}
