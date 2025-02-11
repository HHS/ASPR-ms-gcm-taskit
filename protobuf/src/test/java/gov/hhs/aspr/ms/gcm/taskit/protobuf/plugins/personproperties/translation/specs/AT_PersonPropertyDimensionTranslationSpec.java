package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.personproperties.support.PersonPropertyDimensionData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.personproperties.testsupport.TestPersonPropertyId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.support.input.PersonPropertyDimensionDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.translation.PersonPropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.translation.ReportsTranslator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_PersonPropertyDimensionTranslationSpec {

    @Test
    @UnitTestConstructor(target = PersonPropertyDimensionTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new PersonPropertyDimensionTranslationSpec());
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

        PersonPropertyDimensionTranslationSpec translationSpec = new PersonPropertyDimensionTranslationSpec();
        translationSpec.init(protobufTaskitEngine);

        PersonPropertyDimensionData expectedAppValue = PersonPropertyDimensionData.builder()
                .setPersonPropertyId(TestPersonPropertyId.PERSON_PROPERTY_6_DOUBLE_MUTABLE_TRACK)
                .setTrackTimes(true)
                .addValue("1", 10.0)
                .addValue("2", 1250.2)
                .addValue("3", 15000.5)
                .build();

        PersonPropertyDimensionDataInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        PersonPropertyDimensionData actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = PersonPropertyDimensionTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        PersonPropertyDimensionTranslationSpec translationSpec = new PersonPropertyDimensionTranslationSpec();

        assertEquals(PersonPropertyDimensionData.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = PersonPropertyDimensionTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        PersonPropertyDimensionTranslationSpec translationSpec = new PersonPropertyDimensionTranslationSpec();

        assertEquals(PersonPropertyDimensionDataInput.class, translationSpec.getInputObjectClass());
    }
}
