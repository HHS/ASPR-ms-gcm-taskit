package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.support.GroupTypesForPersonFilter;
import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.Equality;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.support.input.GroupTypesForPersonFilterInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translation.GroupsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translation.PartitionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_GroupTypesForPersonFilterTranslationSpec {

    @Test
    @UnitTestConstructor(target = GroupTypesForPersonFilterTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new GroupTypesForPersonFilterTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testTranslateObject() {
        ProtobufTaskitEngine protobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslator(GroupsTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(PeopleTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .addTranslator(PartitionsTranslator.getTranslator())
                .build();

        GroupTypesForPersonFilterTranslationSpec translationSpec = new GroupTypesForPersonFilterTranslationSpec();
        translationSpec.init(protobufTaskitEngine);

        GroupTypesForPersonFilter expectedAppValue = new GroupTypesForPersonFilter(Equality.EQUAL, 10);

        GroupTypesForPersonFilterInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        GroupTypesForPersonFilter actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = GroupTypesForPersonFilterTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        GroupTypesForPersonFilterTranslationSpec translationSpec = new GroupTypesForPersonFilterTranslationSpec();

        assertEquals(GroupTypesForPersonFilter.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = GroupTypesForPersonFilterTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        GroupTypesForPersonFilterTranslationSpec translationSpec = new GroupTypesForPersonFilterTranslationSpec();

        assertEquals(GroupTypesForPersonFilterInput.class, translationSpec.getInputObjectClass());
    }
}
