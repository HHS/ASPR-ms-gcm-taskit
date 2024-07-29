package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.Equality;
import gov.hhs.aspr.ms.gcm.simulation.plugins.personproperties.support.PersonPropertyFilter;
import gov.hhs.aspr.ms.gcm.simulation.plugins.personproperties.testsupport.TestPersonPropertyId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translation.PartitionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.support.input.PersonPropertyFilterInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.translation.PersonPropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_PersonPropertyFilterTranslationSpec {

    @Test
    @UnitTestConstructor(target = PersonPropertyFilterTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new PersonPropertyFilterTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testTranslateObject() {
        ProtobufTaskitEngine ProtobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslator(PersonPropertiesTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(PeopleTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .addTranslator(PartitionsTranslator.getTranslator())
                .build();

        PersonPropertyFilterTranslationSpec translationSpec = new PersonPropertyFilterTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        Equality equality = Equality.LESS_THAN;
        PersonPropertyFilter expectedAppValue = new PersonPropertyFilter(
                TestPersonPropertyId.PERSON_PROPERTY_1_BOOLEAN_MUTABLE_NO_TRACK, equality, false);

        PersonPropertyFilterInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        PersonPropertyFilter actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = PersonPropertyFilterTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        PersonPropertyFilterTranslationSpec translationSpec = new PersonPropertyFilterTranslationSpec();

        assertEquals(PersonPropertyFilter.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = PersonPropertyFilterTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        PersonPropertyFilterTranslationSpec translationSpec = new PersonPropertyFilterTranslationSpec();

        assertEquals(PersonPropertyFilterInput.class, translationSpec.getInputObjectClass());
    }
}
