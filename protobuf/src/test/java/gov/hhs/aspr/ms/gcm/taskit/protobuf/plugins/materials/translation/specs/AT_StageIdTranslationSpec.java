package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.materials.support.StageId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.support.input.StageIdInput;
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

public class AT_StageIdTranslationSpec {

    @Test
    @UnitTestConstructor(target = StageIdTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new StageIdTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testTranslateObject() {
        ProtobufTaskitEngine ProtobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslator(MaterialsTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(ResourcesTranslator.getTranslator())
                .addTranslator(RegionsTranslator.getTranslator())
                .addTranslator(PeopleTranslator.getTranslator())
                .build();

        StageIdTranslationSpec translationSpec = new StageIdTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        StageId expectedAppValue = new StageId(0);

        StageIdInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        StageId actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);
    }

    @Test
    @UnitTestMethod(target = StageIdTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        StageIdTranslationSpec translationSpec = new StageIdTranslationSpec();

        assertEquals(StageId.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = StageIdTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        StageIdTranslationSpec translationSpec = new StageIdTranslationSpec();

        assertEquals(StageIdInput.class, translationSpec.getInputObjectClass());
    }
}
