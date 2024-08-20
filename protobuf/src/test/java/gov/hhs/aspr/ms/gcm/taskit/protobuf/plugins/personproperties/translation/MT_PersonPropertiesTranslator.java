package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.translation;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import gov.hhs.aspr.ms.gcm.simulation.plugins.people.support.PersonId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.personproperties.datamanagers.PersonPropertiesPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.personproperties.testsupport.PersonPropertiesTestPluginFactory;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.data.input.PersonPropertiesPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.translation.ReportsTranslator;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitEngineManager;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngineId;
import gov.hhs.aspr.ms.util.resourcehelper.ResourceHelper;
import gov.hhs.aspr.ms.util.time.TimeElapser;

public class MT_PersonPropertiesTranslator {

    private long seed = 4684903523797799712L;
    private String times = "";
    private Path basePath = ResourceHelper.getResourceDir(this.getClass());
    private Path filePath = ResourceHelper.createDirectory(basePath, "test-output");
    private ProtobufTaskitEngine protobufTaskitEngine;
    private TimeElapser timeElapser = new TimeElapser();
    private PersonPropertiesPluginData pluginData;
    private PersonPropertiesPluginDataInput inputPluginData;
    private TaskitEngineManager taskitEngineManager;

    private MT_PersonPropertiesTranslator() {
        this.protobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslator(PersonPropertiesTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(PeopleTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .build();
    }

    private void createPluginData(int population) {
        String fileName = "personPropertiesPluginData_mt-" + population + ".json";

        ResourceHelper.createFile(filePath, fileName);

        this.taskitEngineManager = TaskitEngineManager.builder().addTaskitEngine(this.protobufTaskitEngine).build();

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

    private void convertPluginDataToInput() {
        this.timeElapser.reset();
        // convert data
        this.inputPluginData = this.protobufTaskitEngine.translateObject(this.pluginData);
        double elapsedTime = this.timeElapser.getElapsedMilliSeconds();
        this.times.concat(elapsedTime + ",");

        this.pluginData = null;
        System.gc();
    }

    private void convertPluginDataToApp() {
        this.timeElapser.reset();
        // convert data
        this.pluginData = this.protobufTaskitEngine.translateObject(this.inputPluginData);
        double elapsedTime = this.timeElapser.getElapsedMilliSeconds();
        this.times.concat(elapsedTime + ",");

        this.pluginData = null;
        System.gc();
    }

    private void writeOutput(int population) {
        String fileName = "personPropertiesPluginData_mt-" + population + ".json";
        this.timeElapser.reset();

        this.taskitEngineManager.write(filePath.resolve(fileName), this.inputPluginData,
                ProtobufTaskitEngineId.JSON_ENGINE_ID);

        double elapsedTime = this.timeElapser.getElapsedMilliSeconds();
        this.times.concat(elapsedTime + ",");
        this.inputPluginData = null;
        System.gc();
    }

    private void readInput(int population) {
        String fileName = "personPropertiesPluginData_mt-" + population + ".json";
        this.timeElapser.reset();

        this.inputPluginData = this.taskitEngineManager.read(filePath.resolve(fileName),
                PersonPropertiesPluginDataInput.class, ProtobufTaskitEngineId.JSON_ENGINE_ID);

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

        System.out.println("Population,Generate,Translate,Write,Read,Translate");

        for (int i = 0; i < 1_000_000; i += 5000) {
            if (i == 0)
                continue;
            test.appendToTimeString(new String(i + ","));
            test.createPluginData(i);
            test.convertPluginDataToInput();
            test.writeOutput(i);
            test.readInput(i);
            test.convertPluginDataToApp();
            System.out.println(test.times);
            test.clearTimesString();
        }
    }

}
