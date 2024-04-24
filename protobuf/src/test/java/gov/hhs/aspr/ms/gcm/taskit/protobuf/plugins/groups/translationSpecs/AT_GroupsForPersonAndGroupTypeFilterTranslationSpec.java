package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.support.GroupsForPersonAndGroupTypeFilter;
import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.testsupport.TestGroupTypeId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.Equality;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.GroupsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.support.input.GroupsForPersonAndGroupTypeFilterInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.PartitionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_GroupsForPersonAndGroupTypeFilterTranslationSpec {

    @Test
    @UnitTestConstructor(target = GroupsForPersonAndGroupTypeFilterTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new GroupsForPersonAndGroupTypeFilterTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testConvertObject() {
        ProtobufTranslationEngine protobufTranslationEngine = ProtobufTranslationEngine.builder()
                .addTranslator(GroupsTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(PeopleTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .addTranslator(PartitionsTranslator.getTranslator())
                .build();

        GroupsForPersonAndGroupTypeFilterTranslationSpec translationSpec = new GroupsForPersonAndGroupTypeFilterTranslationSpec();
        translationSpec.init(protobufTranslationEngine);

        GroupsForPersonAndGroupTypeFilter expectedAppValue = new GroupsForPersonAndGroupTypeFilter(
                TestGroupTypeId.GROUP_TYPE_1, Equality.EQUAL, 10);

        GroupsForPersonAndGroupTypeFilterInput inputValue = translationSpec.convertAppObject(expectedAppValue);

        GroupsForPersonAndGroupTypeFilter actualAppValue = translationSpec.convertInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = GroupsForPersonAndGroupTypeFilterTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        GroupsForPersonAndGroupTypeFilterTranslationSpec translationSpec = new GroupsForPersonAndGroupTypeFilterTranslationSpec();

        assertEquals(GroupsForPersonAndGroupTypeFilter.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = GroupsForPersonAndGroupTypeFilterTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        GroupsForPersonAndGroupTypeFilterTranslationSpec translationSpec = new GroupsForPersonAndGroupTypeFilterTranslationSpec();

        assertEquals(GroupsForPersonAndGroupTypeFilterInput.class, translationSpec.getInputObjectClass());
    }
}
