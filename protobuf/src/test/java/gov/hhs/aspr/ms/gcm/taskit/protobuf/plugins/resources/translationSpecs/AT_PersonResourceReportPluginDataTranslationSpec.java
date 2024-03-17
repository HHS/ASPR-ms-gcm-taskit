package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.EnumSet;
import java.util.Set;

import org.apache.commons.math3.random.RandomGenerator;
import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.plugins.reports.support.ReportLabel;
import gov.hhs.aspr.ms.gcm.plugins.reports.support.ReportPeriod;
import gov.hhs.aspr.ms.gcm.plugins.reports.support.SimpleReportLabel;
import gov.hhs.aspr.ms.gcm.plugins.resources.reports.PersonResourceReportPluginData;
import gov.hhs.aspr.ms.gcm.plugins.resources.support.ResourceId;
import gov.hhs.aspr.ms.gcm.plugins.resources.testsupport.TestResourceId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.RegionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.ResourcesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.reports.input.PersonResourceReportPluginDataInput;
import gov.hhs.aspr.ms.taskit.core.CoreTranslationError;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;
import gov.hhs.aspr.ms.util.errors.ContractException;
import gov.hhs.aspr.ms.util.random.RandomGeneratorProvider;

public class AT_PersonResourceReportPluginDataTranslationSpec {

    @Test
    @UnitTestConstructor(target = PersonResourceReportPluginDataTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new PersonResourceReportPluginDataTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testConvertObject() {
        ProtobufTranslationEngine protobufTranslationEngine = ProtobufTranslationEngine.builder()
                .addTranslator(ResourcesTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(PeopleTranslator.getTranslator())
                .addTranslator(RegionsTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .build();

        PersonResourceReportPluginDataTranslationSpec translationSpec = new PersonResourceReportPluginDataTranslationSpec();
        translationSpec.init(protobufTranslationEngine);

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

        PersonResourceReportPluginData expectedAppValue = builder.build();

        PersonResourceReportPluginDataInput inputValue = translationSpec.convertAppObject(expectedAppValue);

        PersonResourceReportPluginData actualAppValue = translationSpec.convertInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
        assertEquals(expectedAppValue.toString(), actualAppValue.toString());

        // preconditions
        // version is not supported
        ContractException contractException = assertThrows(ContractException.class, () -> {
            translationSpec.convertInputObject(PersonResourceReportPluginDataInput.newBuilder().setVersion("badversion").build());
        });

        assertEquals(CoreTranslationError.UNSUPPORTED_VERSION, contractException.getErrorType());
    }

    @Test
    @UnitTestMethod(target = PersonResourceReportPluginDataTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        PersonResourceReportPluginDataTranslationSpec translationSpec = new PersonResourceReportPluginDataTranslationSpec();

        assertEquals(PersonResourceReportPluginData.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = PersonResourceReportPluginDataTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        PersonResourceReportPluginDataTranslationSpec translationSpec = new PersonResourceReportPluginDataTranslationSpec();

        assertEquals(PersonResourceReportPluginDataInput.class, translationSpec.getInputObjectClass());
    }
}
