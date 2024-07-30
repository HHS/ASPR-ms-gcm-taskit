package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.personproperties.testsupport.TestPersonPropertyId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.testsupport.input.TestPersonPropertyIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.translation.PersonPropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.translation.ReportsTranslator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_TestPersonPropertyIdTranslationSpec {

    @Test
    @UnitTestConstructor(target = TestPersonPropertyIdTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new TestPersonPropertyIdTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testTranslateObject() {
        ProtobufTaskitEngine protobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslator(PersonPropertiesTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(PeopleTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .build();

        TestPersonPropertyIdTranslationSpec translationSpec = new TestPersonPropertyIdTranslationSpec();
        translationSpec.init(protobufTaskitEngine);

        TestPersonPropertyId expectedAppValue = TestPersonPropertyId.PERSON_PROPERTY_1_BOOLEAN_MUTABLE_NO_TRACK;

        TestPersonPropertyIdInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        TestPersonPropertyId actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = TestPersonPropertyIdTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        TestPersonPropertyIdTranslationSpec translationSpec = new TestPersonPropertyIdTranslationSpec();

        assertEquals(TestPersonPropertyId.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = TestPersonPropertyIdTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        TestPersonPropertyIdTranslationSpec translationSpec = new TestPersonPropertyIdTranslationSpec();

        assertEquals(TestPersonPropertyIdInput.class, translationSpec.getInputObjectClass());
    }
}
