package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.support.GroupId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.support.GroupPropertyDimension;
import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.support.GroupPropertyDimensionData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.testsupport.TestGroupPropertyId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.support.input.GroupPropertyDimensionDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translation.GroupsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.translation.ReportsTranslator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_GroupPropertyDimensionTranslationSpec {

    @Test
    @UnitTestConstructor(target = GroupPropertyDimensionTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new GroupPropertyDimensionTranslationSpec());
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

        GroupPropertyDimensionTranslationSpec translationSpec = new GroupPropertyDimensionTranslationSpec();
        translationSpec.init(protobufTaskitEngine);

        GroupPropertyDimensionData expectedAppValue = GroupPropertyDimensionData.builder()
                .setGroupId(new GroupId(0))
                .setGroupPropertyId(TestGroupPropertyId.GROUP_PROPERTY_1_3_DOUBLE_MUTABLE_NO_TRACK)
                .addValue("1", 10.0)
                .addValue("2",1250.2)
                .addValue("3",15000.5)
                .build();

        GroupPropertyDimensionDataInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        GroupPropertyDimensionData actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = GroupPropertyDimensionTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        GroupPropertyDimensionTranslationSpec translationSpec = new GroupPropertyDimensionTranslationSpec();

        assertEquals(GroupPropertyDimension.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = GroupPropertyDimensionTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        GroupPropertyDimensionTranslationSpec translationSpec = new GroupPropertyDimensionTranslationSpec();

        assertEquals(GroupPropertyDimensionDataInput.class, translationSpec.getInputObjectClass());
    }
}
