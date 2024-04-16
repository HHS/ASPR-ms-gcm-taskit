package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.datamanagers.PartitionsPluginData;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.data.input.PartitionsPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.translationSpecs.TestFilterTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.translationSpecs.TestLabelerTranslationSpec;
import gov.hhs.aspr.ms.taskit.core.TranslationController;
import gov.hhs.aspr.ms.taskit.core.TranslationEngineType;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.resourcehelper.ResourceHelper;

public class IT_PartitionsTranslator {
    Path basePath = ResourceHelper.getResourceDir(this.getClass());
    Path filePath = ResourceHelper.makeOutputDir(basePath, "test-output");

    @Test
    @UnitTestForCoverage
    public void testGroupsTranslator() {
        String fileName = "partitionsPluginData.json";

        ResourceHelper.createOutputFile(filePath, fileName);

        TranslationController translatorController = TranslationController.builder()
                .addTranslationEngine(ProtobufTranslationEngine.builder()
                        .addTranslationSpec(new TestFilterTranslationSpec())
                        .addTranslationSpec(new TestLabelerTranslationSpec())
                        .addTranslator(PartitionsTranslator.getTranslator())
                        .build())
                .addInputFilePath(filePath.resolve(fileName), PartitionsPluginDataInput.class,
                        TranslationEngineType.PROTOBUF)
                .build();

        PartitionsPluginData expectedPluginData = PartitionsPluginData.builder().setRunContinuitySupport(true).build();

        translatorController.writeOutput(expectedPluginData, filePath.resolve(fileName),
                TranslationEngineType.PROTOBUF);

        translatorController.readInput();

        PartitionsPluginData actualPluginData = translatorController.getFirstObject(PartitionsPluginData.class);

        assertEquals(expectedPluginData, actualPluginData);
        assertEquals(expectedPluginData.toString(), actualPluginData.toString());
    }
}
