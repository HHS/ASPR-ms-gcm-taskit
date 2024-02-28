package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.math3.random.RandomGenerator;
import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.plugins.people.support.PersonId;
import gov.hhs.aspr.ms.gcm.plugins.personproperties.datamanagers.PersonPropertiesPluginData;
import gov.hhs.aspr.ms.gcm.plugins.personproperties.reports.PersonPropertyInteractionReportPluginData;
import gov.hhs.aspr.ms.gcm.plugins.personproperties.reports.PersonPropertyReportPluginData;
import gov.hhs.aspr.ms.gcm.plugins.personproperties.support.PersonPropertyId;
import gov.hhs.aspr.ms.gcm.plugins.personproperties.testsupport.PersonPropertiesTestPluginFactory;
import gov.hhs.aspr.ms.gcm.plugins.personproperties.testsupport.TestPersonPropertyId;
import gov.hhs.aspr.ms.gcm.plugins.reports.support.ReportLabel;
import gov.hhs.aspr.ms.gcm.plugins.reports.support.ReportPeriod;
import gov.hhs.aspr.ms.gcm.plugins.reports.support.SimpleReportLabel;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.data.input.PersonPropertiesPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.reports.input.PersonPropertyInteractionReportPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.reports.input.PersonPropertyReportPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.taskit.core.TranslationController;
import gov.hhs.aspr.ms.taskit.core.TranslationEngineType;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.random.RandomGeneratorProvider;
import gov.hhs.aspr.ms.util.resourcehelper.ResourceHelper;

public class IT_PersonPropertiesTranslator {
    Path basePath = ResourceHelper.getResourceDir(this.getClass());
    Path filePath = ResourceHelper.makeOutputDir(basePath, "test-output");

    @Test
    @UnitTestForCoverage
    public void testPersonPropertiesTranslator() {
        String fileName = "personPropertiesPluginData.json";

        ResourceHelper.createOutputFile(filePath, fileName);

        TranslationController translatorController = TranslationController.builder()
                .addTranslationEngine(ProtobufTranslationEngine.builder()
                        .addTranslator(PersonPropertiesTranslator.getTranslator())
                        .addTranslator(PropertiesTranslator.getTranslator())
                        .addTranslator(PeopleTranslator.getTranslator())
                        .addTranslator(ReportsTranslator.getTranslator())
                        .build())
                .addInputFilePath(filePath.resolve(fileName), PersonPropertiesPluginDataInput.class,
                        TranslationEngineType.PROTOBUF)
                .addOutputFilePath(filePath.resolve(fileName), PersonPropertiesPluginData.class,
                        TranslationEngineType.PROTOBUF)
                .build();

        long seed = 4684903523797799712L;
        int initialPoptulation = 100;

        List<PersonId> people = new ArrayList<>();
        for (int i = 0; i < initialPoptulation; i++) {
            people.add(new PersonId(i));
        }

        PersonPropertiesPluginData expectedPluginData = PersonPropertiesTestPluginFactory
                .getStandardPersonPropertiesPluginData(people, seed);

        translatorController.writeOutput(expectedPluginData);
        translatorController.readInput();

        PersonPropertiesPluginData actualPluginData = translatorController
                .getFirstObject(PersonPropertiesPluginData.class);

        assertEquals(expectedPluginData, actualPluginData);
        assertEquals(expectedPluginData.toString(), actualPluginData.toString());
    }

    @Test
    @UnitTestForCoverage
    public void testPersonPropertyReportTranslatorSpec() {
        String fileName = "propertyReport.json";

        ResourceHelper.createOutputFile(filePath, fileName);

        TranslationController translatorController = TranslationController.builder()
                .addTranslationEngine(ProtobufTranslationEngine.builder()
                        .addTranslator(PersonPropertiesTranslator.getTranslator())
                        .addTranslator(PropertiesTranslator.getTranslator())
                        .addTranslator(PeopleTranslator.getTranslator())
                        .addTranslator(ReportsTranslator.getTranslator())
                        .build())
                .addInputFilePath(filePath.resolve(fileName), PersonPropertyReportPluginDataInput.class,
                        TranslationEngineType.PROTOBUF)
                .addOutputFilePath(filePath.resolve(fileName), PersonPropertyReportPluginData.class,
                        TranslationEngineType.PROTOBUF)
                .build();

        long seed = 4684903523797799712L;

        RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(seed);

        ReportLabel reportLabel = new SimpleReportLabel("property report label");
        ReportPeriod reportPeriod = ReportPeriod.DAILY;
        Set<TestPersonPropertyId> expectedPersonPropertyIds = EnumSet.allOf(TestPersonPropertyId.class);

        PersonPropertyReportPluginData.Builder personPropertyReportPluginDataBuilder = //
                PersonPropertyReportPluginData.builder()//
                        .setReportPeriod(reportPeriod)//
                        .setReportLabel(reportLabel);//

        for (PersonPropertyId personPropertyId : expectedPersonPropertyIds) {
            if (randomGenerator.nextBoolean()) {
                personPropertyReportPluginDataBuilder.includePersonProperty(personPropertyId);
            } else {
                personPropertyReportPluginDataBuilder.excludePersonProperty(personPropertyId);
            }
        }

        PersonPropertyReportPluginData expectedPluginData = personPropertyReportPluginDataBuilder.build();

        translatorController.writeOutput(expectedPluginData);
        translatorController.readInput();

        PersonPropertyReportPluginData actualPluginData = translatorController
                .getFirstObject(PersonPropertyReportPluginData.class);

        assertEquals(expectedPluginData, actualPluginData);
        assertEquals(expectedPluginData.toString(), actualPluginData.toString());
    }

    @Test
    @UnitTestForCoverage
    public void testPersonInteractionReportTranslatorSpec() {
        String fileName = "interactionReport.json";

        ResourceHelper.createOutputFile(filePath, fileName);

        TranslationController translatorController = TranslationController.builder()
                .addTranslationEngine(ProtobufTranslationEngine.builder()
                        .addTranslator(PersonPropertiesTranslator.getTranslator())
                        .addTranslator(PropertiesTranslator.getTranslator())
                        .addTranslator(PeopleTranslator.getTranslator())
                        .addTranslator(ReportsTranslator.getTranslator())
                        .build())
                .addInputFilePath(filePath.resolve(fileName), PersonPropertyInteractionReportPluginDataInput.class,
                        TranslationEngineType.PROTOBUF)
                .addOutputFilePath(filePath.resolve(fileName), PersonPropertyInteractionReportPluginData.class,
                        TranslationEngineType.PROTOBUF)
                .build();

        long seed = 4684903523797799712L;
        RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(seed);

        ReportLabel reportLabel = new SimpleReportLabel("interaction report label");
        ReportPeriod reportPeriod = ReportPeriod.DAILY;

        PersonPropertyInteractionReportPluginData.Builder builder = PersonPropertyInteractionReportPluginData.builder()
                .setReportLabel(reportLabel)
                .setReportPeriod(reportPeriod);

        Set<TestPersonPropertyId> expectedPersonPropertyIds = EnumSet.allOf(TestPersonPropertyId.class);

        for (PersonPropertyId personPropertyId : expectedPersonPropertyIds) {
            if (randomGenerator.nextBoolean()) {
                builder.addPersonPropertyId(personPropertyId);
            }
        }

        PersonPropertyInteractionReportPluginData expectedPluginData = builder.build();

        translatorController.writeOutput(expectedPluginData);
        translatorController.readInput();

        PersonPropertyInteractionReportPluginData actualPluginData = translatorController
                .getFirstObject(PersonPropertyInteractionReportPluginData.class);

        assertEquals(expectedPluginData, actualPluginData);
        assertEquals(expectedPluginData.toString(), actualPluginData.toString());
    }
}
