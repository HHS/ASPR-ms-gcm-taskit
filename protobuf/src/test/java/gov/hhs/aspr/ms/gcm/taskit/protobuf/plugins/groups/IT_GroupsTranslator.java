package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups;

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
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.taskit.core.TranslationController;
import gov.hhs.aspr.ms.taskit.core.TranslationEngineType;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.random.RandomGeneratorProvider;
import gov.hhs.aspr.ms.util.resourcehelper.ResourceHelper;

public class IT_GroupsTranslator {
    Path basePath = ResourceHelper.getResourceDir(this.getClass());
    Path filePath = ResourceHelper.makeOutputDir(basePath, "test-output");

    @Test
    @UnitTestForCoverage
    public void testGroupsTranslator() {
        String fileName = "groupsPluginData.json";

        ResourceHelper.createOutputFile(filePath, fileName);

        ProtobufTranslationEngine protobufTranslationEngine = ProtobufTranslationEngine.builder()
                .addTranslator(GroupsTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(PeopleTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .build();

        TranslationController translatorController = TranslationController.builder()
                .addTranslationEngine(protobufTranslationEngine)
                .addInputFilePath(filePath.resolve(fileName), GroupsPluginDataInput.class,
                        TranslationEngineType.PROTOBUF)
                .build();

        long seed = 524805676405822016L;
        int initialPopulation = 100;
        int groupCount = 5;
        int membershipCount = 100;

        List<PersonId> people = new ArrayList<>();

        for (int i = 0; i < initialPopulation; i++) {
            people.add(new PersonId(i));
        }

        List<GroupsPluginData> expectedPluginDatas = new ArrayList<>();

        Random random = new Random(seed);

        for (int i = 0; i < 10; i++) {
            Collections.shuffle(people, new Random(random.nextLong()));

            GroupsPluginData expectedPluginData = GroupsTestPluginFactory.getStandardGroupsPluginData(groupCount,
                    membershipCount, people, seed);

            expectedPluginDatas.add(expectedPluginData);

            translatorController.writeOutput(expectedPluginData, filePath.resolve(fileName),
                    TranslationEngineType.PROTOBUF);
            translatorController.readInput();
        }

        List<GroupsPluginData> actualPluginDatas = translatorController.getObjects(GroupsPluginData.class);

        assertEquals(expectedPluginDatas.size(), actualPluginDatas.size());

        for (int i = 0; i < expectedPluginDatas.size(); i++) {
            assertEquals(expectedPluginDatas.get(i), actualPluginDatas.get(i));
            assertEquals(expectedPluginDatas.get(i).toString(), actualPluginDatas.get(i).toString());
        }
    }

    @Test
    @UnitTestForCoverage
    public void testGroupPropertyReportTranslatorSpec() {
        String fileName = "propertyReport.json";

        ResourceHelper.createOutputFile(filePath, fileName);

        ProtobufTranslationEngine protobufTranslationEngine = ProtobufTranslationEngine.builder()
                .addTranslator(GroupsTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(PeopleTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .build();

        TranslationController translatorController = TranslationController.builder()
                .addTranslationEngine(protobufTranslationEngine)
                .addInputFilePath(filePath.resolve(fileName), GroupPropertyReportPluginDataInput.class,
                        TranslationEngineType.PROTOBUF)
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

        translatorController.writeOutput(expectedPluginData, filePath.resolve(fileName),
                TranslationEngineType.PROTOBUF);

        translatorController.readInput();

        GroupPropertyReportPluginData actualPluginData = translatorController
                .getFirstObject(GroupPropertyReportPluginData.class);

        assertEquals(expectedPluginData, actualPluginData);
        assertEquals(expectedPluginData.toString(), actualPluginData.toString());
    }
}
