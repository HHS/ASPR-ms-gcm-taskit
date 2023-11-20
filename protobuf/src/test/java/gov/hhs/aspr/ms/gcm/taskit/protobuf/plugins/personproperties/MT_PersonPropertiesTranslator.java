package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import gov.hhs.aspr.ms.gcm.plugins.people.support.PersonId;
import gov.hhs.aspr.ms.gcm.plugins.personproperties.datamanagers.PersonPropertiesPluginData;
import gov.hhs.aspr.ms.gcm.plugins.personproperties.testsupport.PersonPropertiesTestPluginFactory;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.data.input.PersonPropertiesPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.taskit.core.TranslationController;
import gov.hhs.aspr.ms.taskit.core.TranslationEngineType;
import gov.hhs.aspr.ms.taskit.core.testsupport.TestResourceHelper;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationEngine;
import util.time.TimeElapser;

public class MT_PersonPropertiesTranslator {

    Path basePath = TestResourceHelper.getResourceDir(this.getClass());
    Path filePath = TestResourceHelper.makeTestOutputDir(basePath);

    public void testPersonPropertiesTranslator(int population) {
        String fileName = "personPropertiesPluginData.json";

        TestResourceHelper.createTestOutputFile(filePath, fileName);
        ProtobufTranslationEngine translationEngine = ProtobufTranslationEngine.builder()
                .addTranslator(PersonPropertiesTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(PeopleTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .build();

        TranslationController translatorController = TranslationController.builder()
                .addTranslationEngine(translationEngine)
                .addInputFilePath(filePath.resolve(fileName), PersonPropertiesPluginDataInput.class,
                        TranslationEngineType.PROTOBUF)
                .addOutputFilePath(filePath.resolve(fileName), PersonPropertiesPluginDataInput.class,
                        TranslationEngineType.PROTOBUF)
                .build();

        long seed = 4684903523797799712L;
        int initialPoptulation = population;

        List<PersonId> people = new ArrayList<>();
        for (int i = 0; i < initialPoptulation; i++) {
            people.add(new PersonId(i));
        }

        TimeElapser timeElapser = new TimeElapser();
        PersonPropertiesPluginData expectedPluginData = PersonPropertiesTestPluginFactory
                .getStandardPersonPropertiesPluginData(people, seed);

        System.out.print(timeElapser.getElapsedMilliSeconds() + ",");
        timeElapser.reset();

        PersonPropertiesPluginDataInput inputData = translationEngine.convertObject(expectedPluginData);
        System.out.print(timeElapser.getElapsedMilliSeconds() + ",");
        timeElapser.reset();

        translatorController.writeOutput(inputData);
        System.out.print(timeElapser.getElapsedMilliSeconds() + ",");
        timeElapser.reset();

        translatorController.readInput();
        System.out.print(timeElapser.getElapsedMilliSeconds() + "\n");
        timeElapser.reset();
    }

    public static void main(String[] args) {
        MT_PersonPropertiesTranslator test = new MT_PersonPropertiesTranslator();

        System.out.println("Population,Generating Data,Converting Data,Writing Data,Reading and Converting Data");
        for (int i = 1; i <= 1000000; i *= 10) {
            System.out.print(i + ",");
            test.testPersonPropertiesTranslator(i);

            System.out.print(i * 2 + ",");
            test.testPersonPropertiesTranslator(i * 2);

            System.out.print(i * 4 + ",");
            test.testPersonPropertiesTranslator(i * 4);

            System.out.print(i * 6 + ",");
            test.testPersonPropertiesTranslator(i * 6);

            System.out.print(i * 8 + ",");
            test.testPersonPropertiesTranslator(i * 8);
        }
    }
}
