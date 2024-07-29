package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.random.RandomGenerator;
import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.datamanagers.GroupsPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.reports.GroupPropertyReportPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.testsupport.GroupsTestPluginFactory;
import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.testsupport.TestGroupPropertyId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.testsupport.TestGroupTypeId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.people.support.PersonId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.ReportLabel;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.ReportPeriod;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.SimpleReportLabel;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.data.input.GroupsPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.reports.input.GroupPropertyReportPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitEngineManager;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngineId;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.random.RandomGeneratorProvider;
import gov.hhs.aspr.ms.util.resourcehelper.ResourceHelper;

public class IT_GroupsTranslator {
    Path basePath = ResourceHelper.getResourceDir(this.getClass());
    Path filePath = ResourceHelper.createDirectory(basePath, "test-output");

    @Test
    @UnitTestForCoverage
    public void testGroupsTranslator() {
        String fileName = "groupsPluginData.json";

        ResourceHelper.createFile(filePath, fileName);

        ProtobufTaskitEngine protobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslator(GroupsTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(PeopleTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .build();

        TaskitEngineManager taskitEngineManager = TaskitEngineManager.builder()
                .addTaskitEngine(protobufTaskitEngine)
                .build();

        long seed = 524805676405822016L;
        int initialPopulation = 100;
        int groupCount = 5;
        int membershipCount = 100;

        List<PersonId> people = new ArrayList<>();

        for (int i = 0; i < initialPopulation; i++) {
            people.add(new PersonId(i));
        }

        Random random = new Random(seed);

        Collections.shuffle(people, new Random(random.nextLong()));

        GroupsPluginData expectedPluginData = GroupsTestPluginFactory.getStandardGroupsPluginData(groupCount,
                membershipCount, people, seed);

        taskitEngineManager.translateAndWrite(filePath.resolve(fileName), expectedPluginData,
                ProtobufTaskitEngineId.JSON_ENGINE_ID);

        GroupsPluginData actualPluginData = taskitEngineManager.readAndTranslate(filePath.resolve(fileName),
                GroupsPluginDataInput.class, ProtobufTaskitEngineId.JSON_ENGINE_ID);
        assertEquals(expectedPluginData, actualPluginData);
        assertEquals(expectedPluginData.toString(), actualPluginData.toString());
    }

    @Test
    @UnitTestForCoverage
    public void testGroupPropertyReportTranslatorSpec() {
        String fileName = "propertyReport.json";

        ResourceHelper.createFile(filePath, fileName);

        ProtobufTaskitEngine protobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslator(GroupsTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(PeopleTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .build();

        TaskitEngineManager taskitEngineManager = TaskitEngineManager.builder()
                .addTaskitEngine(protobufTaskitEngine)
                .build();

        RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(524805676405822016L);

        GroupPropertyReportPluginData.Builder builder = GroupPropertyReportPluginData.builder();

        ReportLabel reportLabel = new SimpleReportLabel("report label");

        builder.setReportLabel(reportLabel).setDefaultInclusion(false).setReportPeriod(ReportPeriod.DAILY);

        for (TestGroupTypeId testGroupTypeId : TestGroupTypeId.values()) {
            for (TestGroupPropertyId testGroupPropertyId : TestGroupPropertyId.values()) {
                if (randomGenerator.nextBoolean()) {
                    builder.includeGroupProperty(testGroupTypeId, testGroupPropertyId);
                } else {
                    builder.excludeGroupProperty(testGroupTypeId, testGroupPropertyId);
                }
            }
        }

        GroupPropertyReportPluginData expectedPluginData = builder.build();

        taskitEngineManager.translateAndWrite(filePath.resolve(fileName), expectedPluginData,
                ProtobufTaskitEngineId.JSON_ENGINE_ID);

        GroupPropertyReportPluginData actualPluginData = taskitEngineManager.readAndTranslate(filePath.resolve(fileName), GroupPropertyReportPluginDataInput.class, ProtobufTaskitEngineId.JSON_ENGINE_ID);

        assertEquals(expectedPluginData, actualPluginData);
        assertEquals(expectedPluginData.toString(), actualPluginData.toString());
    }
}
