package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.people.support.PersonId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.resources.datamanagers.ResourcesPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.resources.testsupport.ResourcesTestPluginFactory;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translation.RegionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.data.input.ResourcesPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translation.ResourcesTranslator;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitError;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;
import gov.hhs.aspr.ms.util.errors.ContractException;

public class AT_ResourcesPluginDataTranslationSpec {

    @Test
    @UnitTestConstructor(target = ResourcesPluginDataTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new ResourcesPluginDataTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testTranslateObject() {
        ProtobufTaskitEngine protobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslator(ResourcesTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(PeopleTranslator.getTranslator())
                .addTranslator(RegionsTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .build();

        ResourcesPluginDataTranslationSpec translationSpec = new ResourcesPluginDataTranslationSpec();
        translationSpec.init(protobufTaskitEngine);

        long seed = 524805676405822016L;

        int initialPopulation = 100;
        List<PersonId> people = new ArrayList<>();

        for (int i = 0; i < initialPopulation; i++) {
            people.add(new PersonId(i));
        }

        ResourcesPluginData expectedAppValue = ResourcesTestPluginFactory.getStandardResourcesPluginData(people, seed);

        ResourcesPluginDataInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        ResourcesPluginData actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
        assertEquals(expectedAppValue.toString(), actualAppValue.toString());

        // preconditions
        // version is not supported
        ContractException contractException = assertThrows(ContractException.class, () -> {
            translationSpec.translateInputObject(ResourcesPluginDataInput.newBuilder().setVersion("badversion").build());
        });

        assertEquals(TaskitError.UNSUPPORTED_VERSION, contractException.getErrorType());
    }

    @Test
    @UnitTestMethod(target = ResourcesPluginDataTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        ResourcesPluginDataTranslationSpec translationSpec = new ResourcesPluginDataTranslationSpec();

        assertEquals(ResourcesPluginData.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = ResourcesPluginDataTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        ResourcesPluginDataTranslationSpec translationSpec = new ResourcesPluginDataTranslationSpec();

        assertEquals(ResourcesPluginDataInput.class, translationSpec.getInputObjectClass());
    }
}
