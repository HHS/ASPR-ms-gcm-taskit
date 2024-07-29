package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.testsupport.TestGroupTypeId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.testsupport.input.TestGroupTypeIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translation.GroupsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translation.specs.TestGroupTypeIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_TestGroupTypeIdTranslationSpec {

    @Test
    @UnitTestConstructor(target = TestGroupTypeIdTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new TestGroupTypeIdTranslationSpec());
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

        TestGroupTypeIdTranslationSpec translationSpec = new TestGroupTypeIdTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        TestGroupTypeId expectedAppValue = TestGroupTypeId.GROUP_TYPE_1;

        TestGroupTypeIdInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        TestGroupTypeId actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = TestGroupTypeIdTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        TestGroupTypeIdTranslationSpec translationSpec = new TestGroupTypeIdTranslationSpec();

        assertEquals(TestGroupTypeId.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = TestGroupTypeIdTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        TestGroupTypeIdTranslationSpec translationSpec = new TestGroupTypeIdTranslationSpec();

        assertEquals(TestGroupTypeIdInput.class, translationSpec.getInputObjectClass());
    }
}
