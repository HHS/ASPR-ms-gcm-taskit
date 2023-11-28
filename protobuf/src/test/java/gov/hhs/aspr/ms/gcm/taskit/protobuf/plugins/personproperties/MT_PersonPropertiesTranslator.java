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

    long seed = 4684903523797799712L;
    String times = "";
    Path basePath = TestResourceHelper.getResourceDir(this.getClass());
    Path filePath = TestResourceHelper.makeTestOutputDir(basePath);
    ProtobufTranslationEngine protobufTranslationEngine;
    TimeElapser timeElapser = new TimeElapser();
    PersonPropertiesPluginData pluginData;
    PersonPropertiesPluginDataInput inputPluginData;
    TranslationController translationController;

    private MT_PersonPropertiesTranslator() {
        protobufTranslationEngine = ProtobufTranslationEngine.builder()
                .addTranslator(PersonPropertiesTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(PeopleTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .build();
    }

    private void createPluginData(int population) {
        String fileName = "personPropertiesPluginData_mt-" + population + ".json";

        TestResourceHelper.createTestOutputFile(filePath, fileName);

        this.translationController = TranslationController.builder()
                .addTranslationEngine(this.protobufTranslationEngine)
                .addInputFilePath(filePath.resolve(fileName), PersonPropertiesPluginDataInput.class,
                        TranslationEngineType.PROTOBUF)
                .addOutputFilePath(filePath.resolve(fileName), PersonPropertiesPluginDataInput.class,
                        TranslationEngineType.PROTOBUF)
                .build();

        List<PersonId> people = new ArrayList<>();
        for (int i = 0; i < population; i++) {
            people.add(new PersonId(i));
        }

        this.timeElapser.reset();
        // generate data
        this.pluginData = PersonPropertiesTestPluginFactory.getStandardPersonPropertiesPluginData(people, seed);
        double elapsedTime = this.timeElapser.getElapsedMilliSeconds();
        this.times.concat(elapsedTime + ",");
    }

    public void convertPluginDataToInput() {
        this.timeElapser.reset();
        // convert data
        this.inputPluginData = this.protobufTranslationEngine.convertObject(this.pluginData);
        double elapsedTime = this.timeElapser.getElapsedMilliSeconds();
        this.times.concat(elapsedTime + ",");

        this.pluginData = null;
        System.gc();
    }

    private void writeOutput() {
        this.timeElapser.reset();

        this.translationController.writeOutput(this.inputPluginData);

        double elapsedTime = this.timeElapser.getElapsedMilliSeconds();
        this.times.concat(elapsedTime + ",");
        this.inputPluginData = null;
        System.gc();
    }

    private void readInput() {
        this.timeElapser.reset();

        this.translationController.readInput();
        double elapsedTime = this.timeElapser.getElapsedMilliSeconds();
        this.times.concat(Double.toString(elapsedTime));
    }

    private void appendToTimeString(Object object) {
        this.times.concat(object.toString());
    }

    private void clearTimesString() {
        this.times = "";
    }

    public static void main(String[] args) {
        MT_PersonPropertiesTranslator test = new MT_PersonPropertiesTranslator();

        System.out.println("Population,Generating Data,Converting Data,Writing Data,Reading and Converting Data");

        for (int i = 0; i < 1_000_000; i += 5000) {
            if (i == 0)
                continue;
            test.appendToTimeString(new String(i + ","));
            test.createPluginData(i);
            test.convertPluginDataToInput();
            test.writeOutput();
            test.readInput();
            System.out.println(test.times);
            test.clearTimesString();
        }
    }

}
