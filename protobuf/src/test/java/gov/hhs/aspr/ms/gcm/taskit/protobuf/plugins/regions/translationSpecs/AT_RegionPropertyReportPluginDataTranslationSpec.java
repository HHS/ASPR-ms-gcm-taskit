package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.EnumSet;
import java.util.Set;

import org.apache.commons.math3.random.RandomGenerator;
import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.reports.RegionPropertyReportPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.support.RegionPropertyId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.testsupport.TestRegionPropertyId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.ReportLabel;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.SimpleReportLabel;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.RegionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.reports.input.RegionPropertyReportPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.taskit.core.CoreTranslationError;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;
import gov.hhs.aspr.ms.util.errors.ContractException;
import gov.hhs.aspr.ms.util.random.RandomGeneratorProvider;

public class AT_RegionPropertyReportPluginDataTranslationSpec {

    @Test
    @UnitTestConstructor(target = RegionPropertyReportPluginDataTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new RegionPropertyReportPluginDataTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testConvertObject() {
        ProtobufTranslationEngine protobufTranslationEngine = ProtobufTranslationEngine.builder()
                .addTranslator(RegionsTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(PeopleTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .build();

        RegionPropertyReportPluginDataTranslationSpec translationSpec = new RegionPropertyReportPluginDataTranslationSpec();
        translationSpec.init(protobufTranslationEngine);

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

        RegionPropertyReportPluginData expectedAppValue = builder.build();

        RegionPropertyReportPluginDataInput inputValue = translationSpec.convertAppObject(expectedAppValue);

        RegionPropertyReportPluginData actualAppValue = translationSpec.convertInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
        assertEquals(expectedAppValue.toString(), actualAppValue.toString());

        // preconditions
        // version is not supported
        ContractException contractException = assertThrows(ContractException.class, () -> {
            translationSpec.convertInputObject(RegionPropertyReportPluginDataInput.newBuilder().setVersion("badversion").build());
        });

        assertEquals(CoreTranslationError.UNSUPPORTED_VERSION, contractException.getErrorType());
    }

    @Test
    @UnitTestMethod(target = RegionPropertyReportPluginDataTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        RegionPropertyReportPluginDataTranslationSpec translationSpec = new RegionPropertyReportPluginDataTranslationSpec();

        assertEquals(RegionPropertyReportPluginData.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = RegionPropertyReportPluginDataTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        RegionPropertyReportPluginDataTranslationSpec translationSpec = new RegionPropertyReportPluginDataTranslationSpec();

        assertEquals(RegionPropertyReportPluginDataInput.class, translationSpec.getInputObjectClass());
    }
}
