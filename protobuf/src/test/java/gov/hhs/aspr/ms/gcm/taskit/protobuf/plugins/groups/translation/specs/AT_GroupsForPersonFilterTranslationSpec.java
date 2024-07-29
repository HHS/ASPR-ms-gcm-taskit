package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.support.GroupsForPersonFilter;
import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.Equality;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.support.input.GroupsForPersonFilterInput;
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

public class AT_GroupsForPersonFilterTranslationSpec {

    @Test
    @UnitTestConstructor(target = GroupsForPersonFilterTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new GroupsForPersonFilterTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testTranslateObject() {
        ProtobufTaskitEngine ProtobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslator(GroupsTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(PeopleTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .addTranslator(PartitionsTranslator.getTranslator())
                .build();

        GroupsForPersonFilterTranslationSpec translationSpec = new GroupsForPersonFilterTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        GroupsForPersonFilter expectedAppValue = new GroupsForPersonFilter(Equality.EQUAL, 10);

        GroupsForPersonFilterInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        GroupsForPersonFilter actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = GroupsForPersonFilterTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        GroupsForPersonFilterTranslationSpec translationSpec = new GroupsForPersonFilterTranslationSpec();

        assertEquals(GroupsForPersonFilter.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = GroupsForPersonFilterTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        GroupsForPersonFilterTranslationSpec translationSpec = new GroupsForPersonFilterTranslationSpec();

        assertEquals(GroupsForPersonFilterInput.class, translationSpec.getInputObjectClass());
    }
}
