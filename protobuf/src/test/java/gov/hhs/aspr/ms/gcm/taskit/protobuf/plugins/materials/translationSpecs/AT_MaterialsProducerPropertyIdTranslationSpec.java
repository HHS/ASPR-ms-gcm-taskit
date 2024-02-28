package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.plugins.materials.support.MaterialsProducerPropertyId;
import gov.hhs.aspr.ms.gcm.plugins.materials.testsupport.TestMaterialsProducerPropertyId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.MaterialsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.support.input.MaterialsProducerPropertyIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.RegionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.ResourcesTranslator;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_MaterialsProducerPropertyIdTranslationSpec {

    @Test
    @UnitTestConstructor(target = MaterialsProducerPropertyIdTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new MaterialsProducerPropertyIdTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testConvertObject() {
        ProtobufTranslationEngine protobufTranslationEngine = ProtobufTranslationEngine.builder()
                .addTranslator(MaterialsTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(ResourcesTranslator.getTranslator())
                .addTranslator(RegionsTranslator.getTranslator())
                .addTranslator(PeopleTranslator.getTranslator())
                .build();

        MaterialsProducerPropertyIdTranslationSpec translationSpec = new MaterialsProducerPropertyIdTranslationSpec();
        translationSpec.init(protobufTranslationEngine);

        MaterialsProducerPropertyId expectedAppValue = TestMaterialsProducerPropertyId.MATERIALS_PRODUCER_PROPERTY_2_INTEGER_MUTABLE_NO_TRACK;

        MaterialsProducerPropertyIdInput inputValue = translationSpec.convertAppObject(expectedAppValue);

        MaterialsProducerPropertyId actualAppValue = translationSpec.convertInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = MaterialsProducerPropertyIdTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        MaterialsProducerPropertyIdTranslationSpec translationSpec = new MaterialsProducerPropertyIdTranslationSpec();

        assertEquals(MaterialsProducerPropertyId.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = MaterialsProducerPropertyIdTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        MaterialsProducerPropertyIdTranslationSpec translationSpec = new MaterialsProducerPropertyIdTranslationSpec();

        assertEquals(MaterialsProducerPropertyIdInput.class, translationSpec.getInputObjectClass());
    }
}
