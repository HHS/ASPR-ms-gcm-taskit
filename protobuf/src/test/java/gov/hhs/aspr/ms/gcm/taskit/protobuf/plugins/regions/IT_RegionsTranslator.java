package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions;

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
import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.datamanagers.RegionsPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.reports.RegionPropertyReportPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.reports.RegionTransferReportPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.support.RegionPropertyId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.testsupport.RegionsTestPluginFactory;
import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.testsupport.TestRegionPropertyId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.ReportLabel;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.ReportPeriod;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.SimpleReportLabel;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.data.input.RegionsPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.reports.input.RegionPropertyReportPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.reports.input.RegionTransferReportPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translation.RegionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitEngineManager;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitEngineId;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.random.RandomGeneratorProvider;
import gov.hhs.aspr.ms.util.resourcehelper.ResourceHelper;

public class IT_RegionsTranslator {
    Path basePath = ResourceHelper.getResourceDir(this.getClass());
    Path filePath = ResourceHelper.createDirectory(basePath, "test-output");

    @Test
    @UnitTestForCoverage
    public void testRegionsTranslator() {
        String fileName = "regionsPluginData.json";

        ResourceHelper.createFile(filePath, fileName);

        TaskitEngineManager translatorController = TaskitEngineManager.builder()
                .addTaskitEngine(IProtobufTaskitEngineBuilder()
                        .addTranslator(RegionsTranslator.getTranslator())
                        .addTranslator(PropertiesTranslator.getTranslator())
                        .addTranslator(PeopleTranslator.getTranslator())
                        .addTranslator(ReportsTranslator.getTranslator())
                        .build())
                .addInputFilePath(filePath.resolve(fileName), RegionsPluginDataInput.class,
                        TaskitEngineId.PROTOBUF)
                .build();

        long seed = 524805676405822016L;
        int initialPopulation = 100;
        List<PersonId> people = new ArrayList<>();

        for (int i = 0; i < initialPopulation; i++) {
            people.add(new PersonId(i));
        }

        RegionsPluginData expectedPluginData = RegionsTestPluginFactory.getStandardRegionsPluginData(people, true,
                seed);

        translatorController.writeOutput(expectedPluginData, filePath.resolve(fileName),
                TaskitEngineId.PROTOBUF);
        translatorController.readInput();

        RegionsPluginData actualPluginData = translatorController.getFirstObject(RegionsPluginData.class);

        assertEquals(expectedPluginData, actualPluginData);
        assertEquals(expectedPluginData.toString(), actualPluginData.toString());
    }

    @Test
    @UnitTestForCoverage
    public void testRegionPropertyReportTranslatorSpec() {
        String fileName = "propertyReport.json";

        ResourceHelper.createFile(filePath, fileName);

        TaskitEngineManager translatorController = TaskitEngineManager.builder()
                .addTaskitEngine(IProtobufTaskitEngineBuilder()
                        .addTranslator(RegionsTranslator.getTranslator())
                        .addTranslator(PropertiesTranslator.getTranslator())
                        .addTranslator(PeopleTranslator.getTranslator())
                        .addTranslator(ReportsTranslator.getTranslator())
                        .build())
                .addInputFilePath(filePath.resolve(fileName), RegionPropertyReportPluginDataInput.class,
                        TaskitEngineId.PROTOBUF)
                .build();

        long seed = 524805676405822016L;
        ReportLabel reportLabel = new SimpleReportLabel("region property report label");

        RegionPropertyReportPluginData.Builder builder = RegionPropertyReportPluginData.builder()
                .setReportLabel(reportLabel)
                .setDefaultInclusion(false);

        Set<TestRegionPropertyId> expectedRegionPropertyIds = EnumSet.allOf(TestRegionPropertyId.class);
        assertFalse(expectedRegionPropertyIds.isEmpty());

        RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(seed);

        for (RegionPropertyId regionPropertyId : TestRegionPropertyId.values()) {
            if (randomGenerator.nextBoolean()) {
                builder.includeRegionProperty(regionPropertyId);
            } else {
                builder.excludeRegionProperty(regionPropertyId);
            }
        }

        RegionPropertyReportPluginData expectedPluginData = builder.build();

        translatorController.writeOutput(expectedPluginData, filePath.resolve(fileName),
                TaskitEngineId.PROTOBUF);
        translatorController.readInput();

        RegionPropertyReportPluginData actualPluginData = translatorController
                .getFirstObject(RegionPropertyReportPluginData.class);

        assertEquals(expectedPluginData, actualPluginData);
        assertEquals(expectedPluginData.toString(), actualPluginData.toString());
    }

    @Test
    @UnitTestForCoverage
    public void testRegionTransferReportTranslatorSpec() {
        String fileName = "transferReport.json";

        ResourceHelper.createFile(filePath, fileName);

        TaskitEngineManager translatorController = TaskitEngineManager.builder()
                .addTaskitEngine(IProtobufTaskitEngineBuilder()
                        .addTranslator(RegionsTranslator.getTranslator())
                        .addTranslator(PropertiesTranslator.getTranslator())
                        .addTranslator(PeopleTranslator.getTranslator())
                        .addTranslator(ReportsTranslator.getTranslator())
                        .build())
                .addInputFilePath(filePath.resolve(fileName), RegionTransferReportPluginDataInput.class,
                        TaskitEngineId.PROTOBUF)
                .build();

        ReportLabel reportLabel = new SimpleReportLabel("region transfer report label");
        ReportPeriod reportPeriod = ReportPeriod.DAILY;

        RegionTransferReportPluginData.Builder builder = RegionTransferReportPluginData.builder();

        builder.setReportLabel(reportLabel).setReportPeriod(reportPeriod);

        RegionTransferReportPluginData expectedPluginData = builder.build();

        translatorController.writeOutput(expectedPluginData, filePath.resolve(fileName),
                TaskitEngineId.PROTOBUF);
        translatorController.readInput();

        RegionTransferReportPluginData actualPluginData = translatorController
                .getFirstObject(RegionTransferReportPluginData.class);

        assertEquals(expectedPluginData, actualPluginData);
        assertEquals(expectedPluginData.toString(), actualPluginData.toString());
    }
}
