package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.translation;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import gov.hhs.aspr.ms.gcm.simulation.plugins.people.support.PersonId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.personproperties.datamanagers.PersonPropertiesPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.personproperties.testsupport.PersonPropertiesTestPluginFactory;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.data.input.PersonPropertiesPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.translation.PersonPropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitEngineManager;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitEngineId;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.resourcehelper.ResourceHelper;
import gov.hhs.aspr.ms.util.time.TimeElapser;

public class MT_PersonPropertiesTranslator {

    long seed = 4684903523797799712L;
    String times = "";
    Path basePath = ResourceHelper.getResourceDir(this.getClass());
    Path filePath = ResourceHelper.createDirectory(basePath, "test-output");
    ProtobufTaskitEngine ProtobufTaskitEngine;
    TimeElapser timeElapser = new TimeElapser();
    PersonPropertiesPluginData pluginData;
    PersonPropertiesPluginDataInput inputPluginData;
    TaskitEngineManager TaskitEngineManager;

    private MT_PersonPropertiesTranslator() {
        ProtobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslator(PersonPropertiesTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(PeopleTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .build();
    }

    private void createPluginData(int population) {
        String fileName = "personPropertiesPluginData_mt-" + population + ".json";

        ResourceHelper.createFile(filePath, fileName);

        this.TaskitEngineManager = TaskitEngineManager.builder()
                .addTaskitEngine(this.ProtobufTaskitEngine)
                .addInputFilePath(filePath.resolve(fileName), PersonPropertiesPluginDataInput.class,
                        ProtobufTaskitEngineId.JSON_ENGINE_ID)
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
        this.inputPluginData = this.ProtobufTaskitEngine.translateObject(this.pluginData);
        double elapsedTime = this.timeElapser.getElapsedMilliSeconds();
        this.times.concat(elapsedTime + ",");

        this.pluginData = null;
        System.gc();
    }

    private void writeOutput(int population) {
        String fileName = "personPropertiesPluginData_mt-" + population + ".json";
        this.timeElapser.reset();

        this.taskitEngineManager.translateAndWrite(this.inputPluginData, filePath.resolve(fileName),
                ProtobufTaskitEngineId.JSON_ENGINE_ID);

        double elapsedTime = this.timeElapser.getElapsedMilliSeconds();
        this.times.concat(elapsedTime + ",");
        this.inputPluginData = null;
        System.gc();
    }

    private void readInput() {
        this.timeElapser.reset();

        this.TaskitEngineManager.readInput();
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
            test.writeOutput(i);
            test.readInput();
            System.out.println(test.times);
            test.clearTimesString();
        }
    }

}
