package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.apache.commons.math3.random.RandomGenerator;
import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.plugins.people.datamanagers.PeoplePluginData;
import gov.hhs.aspr.ms.gcm.plugins.people.support.PersonRange;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.data.input.PeoplePluginDataInput;
import gov.hhs.aspr.ms.taskit.core.TranslationController;
import gov.hhs.aspr.ms.taskit.core.TranslationEngineType;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.random.RandomGeneratorProvider;
import gov.hhs.aspr.ms.util.resourcehelper.TestResourceHelper;

public class IT_PeopleTranslator {
    Path basePath = TestResourceHelper.getResourceDir(this.getClass());
    Path filePath = TestResourceHelper.makeOutputDir(basePath, "test-output");

    @Test
    @UnitTestForCoverage
    public void testPeopleTranslator() {
        String fileName = "peoplePluginData.json";

        TestResourceHelper.createOutputFile(filePath, fileName);

        TranslationController translatorController = TranslationController.builder()
                .addTranslationEngine(
                        ProtobufTranslationEngine.builder().addTranslator(PeopleTranslator.getTranslator()).build())
                .addInputFilePath(filePath.resolve(fileName), PeoplePluginDataInput.class,
                        TranslationEngineType.PROTOBUF)
                .addOutputFilePath(filePath.resolve(fileName), PeoplePluginData.class, TranslationEngineType.PROTOBUF)
                .build();

        PeoplePluginData.Builder builder = PeoplePluginData.builder();
        RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(6573670690105604419L);

        int numRanges = randomGenerator.nextInt(15);

        for (int i = 0; i < numRanges; i++) {
            PersonRange personRange = new PersonRange(i * 15, (i * 15) + 1);
            builder.addPersonRange(personRange);
        }

        PeoplePluginData expectedPluginData = builder.build();

        translatorController.writeOutput(expectedPluginData);
        translatorController.readInput();

        PeoplePluginData actualPluginData = translatorController.getFirstObject(PeoplePluginData.class);

        assertEquals(expectedPluginData, actualPluginData);
        assertEquals(expectedPluginData.toString(), actualPluginData.toString());
    }
}