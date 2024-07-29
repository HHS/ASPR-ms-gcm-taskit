package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.math3.random.RandomGenerator;
import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.reports.GroupPropertyReportPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.testsupport.TestGroupPropertyId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.testsupport.TestGroupTypeId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.ReportLabel;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.ReportPeriod;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.SimpleReportLabel;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.reports.input.GroupPropertyReportPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translation.GroupsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslator;
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

public class AT_GroupPropertyReportPluginDataTranslationSpec {

    @Test
    @UnitTestConstructor(target = GroupPropertyReportPluginDataTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new GroupPropertyReportPluginDataTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testTranslateObject() {
        ProtobufTaskitEngine protobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslator(GroupsTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(PeopleTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .build();

        GroupPropertyReportPluginDataTranslationSpec translationSpec = new GroupPropertyReportPluginDataTranslationSpec();
        translationSpec.init(protobufTaskitEngine);

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

        GroupPropertyReportPluginData expectedAppValue = builder.build();

        GroupPropertyReportPluginDataInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        GroupPropertyReportPluginData actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
        assertEquals(expectedAppValue.toString(), actualAppValue.toString());

        // preconditions
        // version is not supported
        ContractException contractException = assertThrows(ContractException.class, () -> {
            translationSpec.translateInputObject(
                    GroupPropertyReportPluginDataInput.newBuilder().setVersion("badversion").build());
        });

        assertEquals(TaskitError.UNSUPPORTED_VERSION, contractException.getErrorType());
    }

    @Test
    @UnitTestMethod(target = GroupPropertyReportPluginDataTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        GroupPropertyReportPluginDataTranslationSpec translationSpec = new GroupPropertyReportPluginDataTranslationSpec();

        assertEquals(GroupPropertyReportPluginData.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = GroupPropertyReportPluginDataTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        GroupPropertyReportPluginDataTranslationSpec translationSpec = new GroupPropertyReportPluginDataTranslationSpec();

        assertEquals(GroupPropertyReportPluginDataInput.class, translationSpec.getInputObjectClass());
    }
}
