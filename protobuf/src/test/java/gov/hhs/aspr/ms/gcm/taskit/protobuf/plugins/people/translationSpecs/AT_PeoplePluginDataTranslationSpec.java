package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.math3.random.RandomGenerator;
import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.people.datamanagers.PeoplePluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.people.support.PersonRange;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.data.input.PeoplePluginDataInput;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitError;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;
import gov.hhs.aspr.ms.util.errors.ContractException;
import gov.hhs.aspr.ms.util.random.RandomGeneratorProvider;

public class AT_PeoplePluginDataTranslationSpec {

    @Test
    @UnitTestConstructor(target = PeoplePluginDataTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new PeoplePluginDataTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testtranslateObject() {
        ProtobufTaskitEngine ProtobufTaskitEngine = IProtobufTaskitEngineBuilder()
                .addTranslator(PeopleTranslator.getTranslator())
                .build();

        PeoplePluginDataTranslationSpec translationSpec = new PeoplePluginDataTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        PeoplePluginData.Builder builder = PeoplePluginData.builder();
        RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(6573670690105604419L);

        int numRanges = randomGenerator.nextInt(15);

        for (int i = 0; i < numRanges; i++) {
            PersonRange personRange = new PersonRange(i * 15, (i * 15) + 1);
            builder.addPersonRange(personRange);
        }

        PeoplePluginData expectedAppValue = builder.build();

        PeoplePluginDataInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        PeoplePluginData actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
        assertEquals(expectedAppValue.toString(), actualAppValue.toString());

        inputValue = inputValue.toBuilder().clearPersonCount().build();

        actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
        assertEquals(expectedAppValue.toString(), actualAppValue.toString());

        // preconditions
        // version is not supported
        ContractException contractException = assertThrows(ContractException.class, () -> {
            translationSpec.translateInputObject(PeoplePluginDataInput.newBuilder().setVersion("badversion").build());
        });

        assertEquals(TaskitError.UNSUPPORTED_VERSION, contractException.getErrorType());
    }

    @Test
    @UnitTestMethod(target = PeoplePluginDataTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        PeoplePluginDataTranslationSpec translationSpec = new PeoplePluginDataTranslationSpec();

        assertEquals(PeoplePluginData.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = PeoplePluginDataTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        PeoplePluginDataTranslationSpec translationSpec = new PeoplePluginDataTranslationSpec();

        assertEquals(PeoplePluginDataInput.class, translationSpec.getInputObjectClass());
    }
}
