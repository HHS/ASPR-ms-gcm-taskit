package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.testsupport.TestGroupTypeId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.GroupsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.testsupport.input.TestGroupTypeIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationEngine;
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
    public void testConvertObject() {
        ProtobufTranslationEngine protobufTranslationEngine = ProtobufTranslationEngine.builder()
                .addTranslator(GroupsTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(PeopleTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .build();

        TestGroupTypeIdTranslationSpec translationSpec = new TestGroupTypeIdTranslationSpec();
        translationSpec.init(protobufTranslationEngine);

        TestGroupTypeId expectedAppValue = TestGroupTypeId.GROUP_TYPE_1;

        TestGroupTypeIdInput inputValue = translationSpec.convertAppObject(expectedAppValue);

        TestGroupTypeId actualAppValue = translationSpec.convertInputObject(inputValue);

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
