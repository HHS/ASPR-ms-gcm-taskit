package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.datamanagers.GroupsPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.support.GroupId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.testsupport.GroupsTestPluginFactory;
import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.testsupport.TestGroupTypeId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.people.support.PersonId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.data.input.GroupsPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translation.GroupsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translation.specs.GroupsPluginDataTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitError;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;
import gov.hhs.aspr.ms.util.errors.ContractException;

public class AT_GroupsPluginDataTranslationSpec {

    @Test
    @UnitTestConstructor(target = GroupsPluginDataTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new GroupsPluginDataTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testTranslateObject() {
        ProtobufTaskitEngine ProtobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslator(GroupsTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(PeopleTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .build();

        GroupsPluginDataTranslationSpec translationSpec = new GroupsPluginDataTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        long seed = 524805676405822016L;
        int initialPopulation = 100;
        int expectedGroupsPerPerson = 5;
        int expectedPeoplePerGroup = 100;

        List<PersonId> people = new ArrayList<>();

        for (int i = 0; i < initialPopulation; i++) {
            people.add(new PersonId(i));
        }

        GroupsPluginData expectedAppValue = GroupsTestPluginFactory.getStandardGroupsPluginData(expectedGroupsPerPerson,
                expectedPeoplePerGroup, people, seed);

        GroupsPluginDataInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        GroupsPluginData actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
        assertEquals(expectedAppValue.toString(), actualAppValue.toString());

        expectedGroupsPerPerson = 0;
        expectedPeoplePerGroup = 0;
        GroupsPluginData.Builder builder = (GroupsPluginData.Builder) GroupsTestPluginFactory
                .getStandardGroupsPluginData(expectedGroupsPerPerson, expectedPeoplePerGroup, people, seed)
                .getCloneBuilder();
        builder.addGroup(new GroupId(100), TestGroupTypeId.GROUP_TYPE_1)
                .associatePersonToGroup(new GroupId(100), new PersonId(110))
                .setNextGroupIdValue(101);

        expectedAppValue = builder.build();
        inputValue = translationSpec.translateAppObject(expectedAppValue);

        actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
        assertEquals(expectedAppValue.toString(), actualAppValue.toString());

        // preconditions
        // version is not supported
        ContractException contractException = assertThrows(ContractException.class, () -> {
            translationSpec.translateInputObject(GroupsPluginDataInput.newBuilder().setVersion("badversion").build());
        });

        assertEquals(TaskitError.UNSUPPORTED_VERSION, contractException.getErrorType());
    }

    @Test
    @UnitTestMethod(target = GroupsPluginDataTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        GroupsPluginDataTranslationSpec translationSpec = new GroupsPluginDataTranslationSpec();

        assertEquals(GroupsPluginData.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = GroupsPluginDataTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        GroupsPluginDataTranslationSpec translationSpec = new GroupsPluginDataTranslationSpec();

        assertEquals(GroupsPluginDataInput.class, translationSpec.getInputObjectClass());
    }
}
