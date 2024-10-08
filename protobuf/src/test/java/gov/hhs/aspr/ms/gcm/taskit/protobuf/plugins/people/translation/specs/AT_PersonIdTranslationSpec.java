package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.people.support.PersonId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.support.input.PersonIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_PersonIdTranslationSpec {

    @Test
    @UnitTestConstructor(target = PersonIdTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new PersonIdTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testTranslateObject() {
        ProtobufTaskitEngine protobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslator(PeopleTranslator.getTranslator())
                .build();

        PersonIdTranslationSpec translationSpec = new PersonIdTranslationSpec();
        translationSpec.init(protobufTaskitEngine);

        PersonId expectedAppValue = new PersonId(0);

        PersonIdInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        PersonId actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = PersonIdTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        PersonIdTranslationSpec translationSpec = new PersonIdTranslationSpec();

        assertEquals(PersonId.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = PersonIdTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        PersonIdTranslationSpec translationSpec = new PersonIdTranslationSpec();

        assertEquals(PersonIdInput.class, translationSpec.getInputObjectClass());
    }
}
