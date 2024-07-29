package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.people.support.PersonRange;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.support.input.PersonRangeInput;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_PersonRangeTranslationSpec {

    @Test
    @UnitTestConstructor(target = PersonRangeTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new PersonRangeTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testtranslateObject() {
        ProtobufTaskitEngine ProtobufTaskitEngine = IProtobufTaskitEngineBuilder()
                .addTranslator(PeopleTranslator.getTranslator())
                .build();

        PersonRangeTranslationSpec translationSpec = new PersonRangeTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        PersonRange expectedAppValue = new PersonRange(1 * 15, (2 * 15) + 1);

        PersonRangeInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        PersonRange actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = PersonRangeTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        PersonRangeTranslationSpec translationSpec = new PersonRangeTranslationSpec();

        assertEquals(PersonRange.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = PersonRangeTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        PersonRangeTranslationSpec translationSpec = new PersonRangeTranslationSpec();

        assertEquals(PersonRangeInput.class, translationSpec.getInputObjectClass());
    }
}
