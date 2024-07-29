package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.materials.testsupport.TestMaterialsProducerId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.testsupport.input.TestMaterialsProducerIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translation.MaterialsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translation.RegionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translation.ResourcesTranslator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_TestMaterialsProducerIdTranslationSpec {

    @Test
    @UnitTestConstructor(target = TestMaterialsProducerIdTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new TestMaterialsProducerIdTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testTranslateObject() {
        ProtobufTaskitEngine protobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslator(MaterialsTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(ResourcesTranslator.getTranslator())
                .addTranslator(RegionsTranslator.getTranslator())
                .addTranslator(PeopleTranslator.getTranslator())
                .build();

        TestMaterialsProducerIdTranslationSpec translationSpec = new TestMaterialsProducerIdTranslationSpec();
        translationSpec.init(protobufTaskitEngine);

        TestMaterialsProducerId expectedAppValue = TestMaterialsProducerId.MATERIALS_PRODUCER_1;

        TestMaterialsProducerIdInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        TestMaterialsProducerId actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = TestMaterialsProducerIdTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        TestMaterialsProducerIdTranslationSpec translationSpec = new TestMaterialsProducerIdTranslationSpec();

        assertEquals(TestMaterialsProducerId.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = TestMaterialsProducerIdTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        TestMaterialsProducerIdTranslationSpec translationSpec = new TestMaterialsProducerIdTranslationSpec();

        assertEquals(TestMaterialsProducerIdInput.class, translationSpec.getInputObjectClass());
    }
}
