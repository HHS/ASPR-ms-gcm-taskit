package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.math3.random.RandomGenerator;
import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.people.support.PersonId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.ReportLabel;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.ReportPeriod;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.SimpleReportLabel;
import gov.hhs.aspr.ms.gcm.simulation.plugins.resources.datamanagers.ResourcesPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.resources.reports.PersonResourceReportPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.resources.reports.ResourcePropertyReportPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.resources.reports.ResourceReportPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.resources.support.ResourceId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.resources.testsupport.ResourcesTestPluginFactory;
import gov.hhs.aspr.ms.gcm.simulation.plugins.resources.testsupport.TestResourceId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translation.RegionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.data.input.ResourcesPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.reports.input.PersonResourceReportPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.reports.input.ResourcePropertyReportPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.reports.input.ResourceReportPluginDataInput;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitEngineManager;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngineId;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.random.RandomGeneratorProvider;
import gov.hhs.aspr.ms.util.resourcehelper.ResourceHelper;

public class IT_ResourcesTranslator {
    Path basePath = ResourceHelper.getResourceDir(this.getClass());
    Path filePath = ResourceHelper.createDirectory(basePath, "test-output");

    @Test
    @UnitTestForCoverage
    public void testResourcesTranslator() {
        String fileName = "resourcesPluginData.json";

        ResourceHelper.createFile(filePath, fileName);

        TaskitEngineManager taskitEngineManager = TaskitEngineManager.builder()
                .addTaskitEngine(ProtobufJsonTaskitEngine.builder()
                        .addTranslator(ResourcesTranslator.getTranslator())
                        .addTranslator(PropertiesTranslator.getTranslator())
                        .addTranslator(PeopleTranslator.getTranslator())
                        .addTranslator(RegionsTranslator.getTranslator())
                        .addTranslator(ReportsTranslator.getTranslator())
                        .build())
                .addInputFilePath(filePath.resolve(fileName), ResourcesPluginDataInput.class,
                        ProtobufTaskitEngineId.JSON_ENGINE_ID)
                .build();

        long seed = 524805676405822016L;
        int initialPopulation = 100;
        List<PersonId> people = new ArrayList<>();

        for (int i = 0; i < initialPopulation; i++) {
            people.add(new PersonId(i));
        }

        ResourcesPluginData expectedPluginData = ResourcesTestPluginFactory.getStandardResourcesPluginData(people,
                seed);

        taskitEngineManager.translateAndWrite(expectedPluginData, filePath.resolve(fileName),
                ProtobufTaskitEngineId.JSON_ENGINE_ID);
        taskitEngineManager.readInput();

        ResourcesPluginData actualPluginData = taskitEngineManager.getFirstObject(ResourcesPluginData.class);

        assertEquals(expectedPluginData, actualPluginData);
        assertEquals(expectedPluginData.toString(), actualPluginData.toString());
    }

    @Test
    @UnitTestForCoverage
    public void testPersonResourceReportTranslatorSpec() {
        String fileName = "personResourceReport.json";

        ResourceHelper.createFile(filePath, fileName);

        TaskitEngineManager taskitEngineManager = TaskitEngineManager.builder()
                .addTaskitEngine(ProtobufJsonTaskitEngine.builder()
                        .addTranslator(ResourcesTranslator.getTranslator())
                        .addTranslator(PropertiesTranslator.getTranslator())
                        .addTranslator(PeopleTranslator.getTranslator())
                        .addTranslator(RegionsTranslator.getTranslator())
                        .addTranslator(ReportsTranslator.getTranslator())
                        .build())
                .addInputFilePath(filePath.resolve(fileName), PersonResourceReportPluginDataInput.class,
                        ProtobufTaskitEngineId.JSON_ENGINE_ID)
                .build();

        long seed = 524805676405822016L;
        RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(seed);

        ReportLabel reportLabel = new SimpleReportLabel("person resource report label");
        ReportPeriod reportPeriod = ReportPeriod.DAILY;

        PersonResourceReportPluginData.Builder builder = PersonResourceReportPluginData.builder();

        builder.setReportLabel(reportLabel).setReportPeriod(reportPeriod).setDefaultInclusion(false);

        Set<TestResourceId> expectedResourceIds = EnumSet.allOf(TestResourceId.class);
        assertFalse(expectedResourceIds.isEmpty());

        for (ResourceId resourceId : expectedResourceIds) {
            if (randomGenerator.nextBoolean()) {
                builder.includeResource(resourceId);
            } else {
                builder.excludeResource(resourceId);
            }
        }

        PersonResourceReportPluginData expectedPluginData = builder.build();

        taskitEngineManager.translateAndWrite(expectedPluginData, filePath.resolve(fileName),
                ProtobufTaskitEngineId.JSON_ENGINE_ID);
        taskitEngineManager.readInput();

        PersonResourceReportPluginData actualPluginData = taskitEngineManager
                .getFirstObject(PersonResourceReportPluginData.class);

        assertEquals(expectedPluginData, actualPluginData);
        assertEquals(expectedPluginData.toString(), actualPluginData.toString());
    }

    @Test
    @UnitTestForCoverage
    public void testResourcePropertyReportTranslatorSpec() {
        String fileName = "resourcePropertyReport.json";

        ResourceHelper.createFile(filePath, fileName);

        TaskitEngineManager taskitEngineManager = TaskitEngineManager.builder()
                .addTaskitEngine(ProtobufJsonTaskitEngine.builder()
                        .addTranslator(ResourcesTranslator.getTranslator())
                        .addTranslator(PropertiesTranslator.getTranslator())
                        .addTranslator(PeopleTranslator.getTranslator())
                        .addTranslator(RegionsTranslator.getTranslator())
                        .addTranslator(ReportsTranslator.getTranslator())
                        .build())
                .addInputFilePath(filePath.resolve(fileName), ResourcePropertyReportPluginDataInput.class,
                        ProtobufTaskitEngineId.JSON_ENGINE_ID)
                .build();

        ReportLabel reportLabel = new SimpleReportLabel("resource property report label");

        ResourcePropertyReportPluginData.Builder builder = ResourcePropertyReportPluginData.builder();

        builder.setReportLabel(reportLabel);

        ResourcePropertyReportPluginData expectedPluginData = builder.build();

        taskitEngineManager.translateAndWrite(expectedPluginData, filePath.resolve(fileName),
                ProtobufTaskitEngineId.JSON_ENGINE_ID);
        taskitEngineManager.readInput();

        ResourcePropertyReportPluginData actualPluginData = taskitEngineManager
                .getFirstObject(ResourcePropertyReportPluginData.class);

        assertEquals(expectedPluginData, actualPluginData);
        assertEquals(expectedPluginData.toString(), actualPluginData.toString());
    }

    @Test
    @UnitTestForCoverage
    public void testResourceReportTranslatorSpec() {
        String fileName = "resourceReport.json";

        ResourceHelper.createFile(filePath, fileName);

        TaskitEngineManager taskitEngineManager = TaskitEngineManager.builder()
                .addTaskitEngine(ProtobufJsonTaskitEngine.builder()
                        .addTranslator(ResourcesTranslator.getTranslator())
                        .addTranslator(PropertiesTranslator.getTranslator())
                        .addTranslator(PeopleTranslator.getTranslator())
                        .addTranslator(RegionsTranslator.getTranslator())
                        .addTranslator(ReportsTranslator.getTranslator())
                        .build())
                .addInputFilePath(filePath.resolve(fileName), ResourceReportPluginDataInput.class,
                        ProtobufTaskitEngineId.JSON_ENGINE_ID)
                .build();

        long seed = 524805676405822016L;
        RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(seed);

        ReportLabel reportLabel = new SimpleReportLabel("resource report label");
        ReportPeriod reportPeriod = ReportPeriod.DAILY;

        ResourceReportPluginData.Builder builder = ResourceReportPluginData.builder();

        builder.setReportLabel(reportLabel).setReportPeriod(reportPeriod).setDefaultInclusion(false);

        Set<TestResourceId> expectedResourceIds = EnumSet.allOf(TestResourceId.class);
        assertFalse(expectedResourceIds.isEmpty());

        for (ResourceId resourceId : expectedResourceIds) {
            if (randomGenerator.nextBoolean()) {
                builder.includeResource(resourceId);
            } else {
                builder.excludeResource(resourceId);
            }
        }

        ResourceReportPluginData expectedPluginData = builder.build();

        taskitEngineManager.translateAndWrite(expectedPluginData, filePath.resolve(fileName),
                ProtobufTaskitEngineId.JSON_ENGINE_ID);
        taskitEngineManager.readInput();

        ResourceReportPluginData actualPluginData = taskitEngineManager.getFirstObject(ResourceReportPluginData.class);

        assertEquals(expectedPluginData, actualPluginData);
        assertEquals(expectedPluginData.toString(), actualPluginData.toString());
    }

}
