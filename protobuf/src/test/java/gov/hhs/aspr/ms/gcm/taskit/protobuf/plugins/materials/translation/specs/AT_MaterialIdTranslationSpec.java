package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.materials.support.MaterialId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.materials.testsupport.TestMaterialId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.support.input.MaterialIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translation.MaterialsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translation.RegionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.translation.ReportsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translation.ResourcesTranslator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_MaterialIdTranslationSpec {

    @Test
    @UnitTestConstructor(target = MaterialIdTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new MaterialIdTranslationSpec());
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

        MaterialIdTranslationSpec translationSpec = new MaterialIdTranslationSpec();
        translationSpec.init(protobufTaskitEngine);

        MaterialId expectedAppValue = TestMaterialId.MATERIAL_1;

        MaterialIdInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        MaterialId actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = MaterialIdTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        MaterialIdTranslationSpec translationSpec = new MaterialIdTranslationSpec();

        assertEquals(MaterialId.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = MaterialIdTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        MaterialIdTranslationSpec translationSpec = new MaterialIdTranslationSpec();

        assertEquals(MaterialIdInput.class, translationSpec.getInputObjectClass());
    }
}
