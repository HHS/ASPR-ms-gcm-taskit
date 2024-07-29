package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.materials.testsupport.TestMaterialId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.testsupport.input.TestMaterialIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translation.MaterialsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translation.specs.TestMaterialIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translation.RegionsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translation.ResourcesTranslator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_TestMaterialIdTranslationSpec {

    @Test
    @UnitTestConstructor(target = TestMaterialIdTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new TestMaterialIdTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testtranslateObject() {
        ProtobufTaskitEngine ProtobufTaskitEngine = IProtobufTaskitEngineBuilder()
                .addTranslator(MaterialsTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(ResourcesTranslator.getTranslator())
                .addTranslator(RegionsTranslator.getTranslator())
                .addTranslator(PeopleTranslator.getTranslator())
                .build();

        TestMaterialIdTranslationSpec translationSpec = new TestMaterialIdTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        TestMaterialId expectedAppValue = TestMaterialId.MATERIAL_1;

        TestMaterialIdInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        TestMaterialId actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = TestMaterialIdTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        TestMaterialIdTranslationSpec translationSpec = new TestMaterialIdTranslationSpec();

        assertEquals(TestMaterialId.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = TestMaterialIdTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        TestMaterialIdTranslationSpec translationSpec = new TestMaterialIdTranslationSpec();

        assertEquals(TestMaterialIdInput.class, translationSpec.getInputObjectClass());
    }
}
