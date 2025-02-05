package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.translation;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.util.FastMath;

import gov.hhs.aspr.ms.gcm.simulation.plugins.people.support.PersonId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.personproperties.datamanagers.PersonPropertiesPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.personproperties.testsupport.PersonPropertiesTestPluginFactory;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.data.input.PersonPropertiesPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.translation.ReportsTranslator;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitEngineManager;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufBinaryTaskitEngine;
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
        this.protobufTaskitEngine = ProtobufBinaryTaskitEngine.builder()
                .addTranslator(PersonPropertiesTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(PeopleTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .build();

        this.taskitEngineManager = TaskitEngineManager.builder()//
                .addTaskitEngine(ProtobufBinaryTaskitEngine.builder()
                        .addTranslator(PersonPropertiesTranslator.getTranslator())
                        .addTranslator(PropertiesTranslator.getTranslator())
                        .addTranslator(PeopleTranslator.getTranslator())
                        .addTranslator(ReportsTranslator.getTranslator())
                        .build())//
                .addTaskitEngine(ProtobufJsonTaskitEngine.builder()
                        .addTranslator(PersonPropertiesTranslator.getTranslator())
                        .addTranslator(PropertiesTranslator.getTranslator())
                        .addTranslator(PeopleTranslator.getTranslator())
                        .addTranslator(ReportsTranslator.getTranslator())
                        .build())//
                .build();
    }

    private void createPluginData(int population) {
        String fileName = "personPropertiesPluginData_mt-" + population + ".json";

        ResourceHelper.createFile(filePath, fileName);

        List<PersonId> people = new ArrayList<>();
        for (int i = 0; i < population; i++) {
            people.add(new PersonId(i));
        }

        this.timeElapser.reset();
        // generate data
        this.pluginData = PersonPropertiesTestPluginFactory.getStandardPersonPropertiesPluginData(people, seed);
        double elapsedTime = this.timeElapser.getElapsedMilliSeconds();
        this.appendTimeToTimeString(elapsedTime, false);
    }

    private void convertPluginDataToInput() {
        this.timeElapser.reset();
        // convert data
        this.inputPluginData = this.protobufTaskitEngine.translateObject(this.pluginData);
        double elapsedTime = this.timeElapser.getElapsedMilliSeconds();
        this.appendTimeToTimeString(elapsedTime, false);

        this.pluginData = null;
        System.gc();
    }

    private void convertPluginDataToApp() {
        this.timeElapser.reset();
        // convert data
        this.pluginData = this.protobufTaskitEngine.translateObject(this.inputPluginData);
        double elapsedTime = this.timeElapser.getElapsedMilliSeconds();
        this.appendTimeToTimeString(elapsedTime, true);

        this.pluginData = null;
        System.gc();
    }

    private void writeOutput(int population) {
        String fileName = "personPropertiesPluginData_mt-" + population + ".json";
        this.timeElapser.reset();

        this.taskitEngineManager.write(filePath.resolve(fileName), this.inputPluginData,
                ProtobufTaskitEngineId.BINARY_ENGINE_ID);

        double elapsedTime = this.timeElapser.getElapsedMilliSeconds();
        this.appendTimeToTimeString(elapsedTime, false);
        this.inputPluginData = null;
        System.gc();

        File file = filePath.resolve(fileName).toFile();

        long fileSize = file.length();

        String unit = "B";
        if (fileSize > 1024) {
            // KB
            fileSize = fileSize / 1024;
            unit = "KB";

            if (fileSize > 1024 * 100) {
                // 100MB
                fileSize = fileSize / 1024;
                unit = "MB";

                if (fileSize > 1024 * 5) {
                    // 5GB
                    fileSize = fileSize / 1024;
                    unit = "GB";
                }
            }
        }

        this.times = this.times.concat(Long.toString(fileSize) + unit + ",");
    }

    private void readInput(int population) {
        String fileName = "personPropertiesPluginData_mt-" + population + ".json";
        this.timeElapser.reset();

        this.inputPluginData = this.taskitEngineManager.read(filePath.resolve(fileName),
                PersonPropertiesPluginDataInput.class, ProtobufTaskitEngineId.BINARY_ENGINE_ID);

        double elapsedTime = this.timeElapser.getElapsedMilliSeconds();
        this.appendTimeToTimeString(elapsedTime, false);
    }

    private void appendTimeToTimeString(double time, boolean last) {
        long roundedTime = FastMath.round(time);
        this.times = this.times.concat(Long.toString(roundedTime));

        if (!last) {
            this.times = this.times.concat(",");
        }
    }

    private void appendToTimeString(Object object) {
        this.times = this.times.concat(object.toString());
    }

    private void clearTimesString() {
        this.times = "";
    }

    public static void main(String[] args) {
        MT_PersonPropertiesTranslator test = new MT_PersonPropertiesTranslator();

        System.out.println("Population,Generate,Translate,Write,Size,Read,Translate");

        for (int i = 0; i <= 10_000_000; i += 1_000_000) {
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
