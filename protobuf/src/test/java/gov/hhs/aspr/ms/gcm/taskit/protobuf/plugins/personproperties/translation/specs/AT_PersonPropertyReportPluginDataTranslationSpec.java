package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.EnumSet;
import java.util.Set;

import org.apache.commons.math3.random.RandomGenerator;
import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.personproperties.reports.PersonPropertyReportPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.personproperties.support.PersonPropertyId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.personproperties.testsupport.TestPersonPropertyId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.ReportLabel;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.ReportPeriod;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.SimpleReportLabel;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.reports.input.PersonPropertyReportPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.translation.PersonPropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.translation.specs.PersonPropertyReportPluginDataTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitError;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;
import gov.hhs.aspr.ms.util.errors.ContractException;
import gov.hhs.aspr.ms.util.random.RandomGeneratorProvider;

public class AT_PersonPropertyReportPluginDataTranslationSpec {

    @Test
    @UnitTestConstructor(target = PersonPropertyReportPluginDataTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new PersonPropertyReportPluginDataTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testTranslateObject() {
        ProtobufTaskitEngine ProtobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslator(PersonPropertiesTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(PeopleTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .build();

        PersonPropertyReportPluginDataTranslationSpec translationSpec = new PersonPropertyReportPluginDataTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        long seed = 4684903523797799712L;

        RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(seed);

        ReportLabel reportLabel = new SimpleReportLabel("property report label");
        ReportPeriod reportPeriod = ReportPeriod.DAILY;
        Set<TestPersonPropertyId> expectedPersonPropertyIds = EnumSet.allOf(TestPersonPropertyId.class);

        PersonPropertyReportPluginData.Builder builder = //
                PersonPropertyReportPluginData.builder()//
                        .setReportPeriod(reportPeriod)//
                        .setReportLabel(reportLabel);//

        for (PersonPropertyId personPropertyId : expectedPersonPropertyIds) {
            if (randomGenerator.nextBoolean()) {
                builder.includePersonProperty(personPropertyId);
            } else {
                builder.excludePersonProperty(personPropertyId);
            }
        }

        PersonPropertyReportPluginData expectedAppValue = builder.build();

        PersonPropertyReportPluginDataInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        PersonPropertyReportPluginData actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
        assertEquals(expectedAppValue.toString(), actualAppValue.toString());

        // preconditions
        // version is not supported
        ContractException contractException = assertThrows(ContractException.class, () -> {
            translationSpec.translateInputObject(
                    PersonPropertyReportPluginDataInput.newBuilder().setVersion("badversion").build());
        });

        assertEquals(TaskitError.UNSUPPORTED_VERSION, contractException.getErrorType());
    }

    @Test
    @UnitTestMethod(target = PersonPropertyReportPluginDataTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        PersonPropertyReportPluginDataTranslationSpec translationSpec = new PersonPropertyReportPluginDataTranslationSpec();

        assertEquals(PersonPropertyReportPluginData.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = PersonPropertyReportPluginDataTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        PersonPropertyReportPluginDataTranslationSpec translationSpec = new PersonPropertyReportPluginDataTranslationSpec();

        assertEquals(PersonPropertyReportPluginDataInput.class, translationSpec.getInputObjectClass());
    }
}
