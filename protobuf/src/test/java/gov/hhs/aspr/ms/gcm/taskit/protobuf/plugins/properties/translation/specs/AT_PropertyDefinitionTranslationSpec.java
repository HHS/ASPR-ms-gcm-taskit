package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.properties.support.PropertyDefinition;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.support.input.PropertyDefinitionInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.specs.PropertyDefinitionTranslationSpec;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_PropertyDefinitionTranslationSpec {

    @Test
    @UnitTestConstructor(target = PropertyDefinitionTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new PropertyDefinitionTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testTranslateObject() {
        ProtobufTaskitEngine ProtobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslator(PropertiesTranslator.getTranslator())
                .build();

        PropertyDefinitionTranslationSpec translationSpec = new PropertyDefinitionTranslationSpec();
        translationSpec.init(ProtobufTaskitEngine);

        PropertyDefinition expectedAppValue = PropertyDefinition.builder()
                .setDefaultValue("defaultValue")
                .setPropertyValueMutability(true)
                .setType(String.class)
                .build();

        PropertyDefinitionInput inputValue = translationSpec.translateAppObject(expectedAppValue);

        PropertyDefinition actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);

        expectedAppValue = PropertyDefinition.builder().setPropertyValueMutability(true).setType(String.class).build();

        inputValue = translationSpec.translateAppObject(expectedAppValue);
        actualAppValue = translationSpec.translateInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);

        assertThrows(RuntimeException.class, () -> {
            translationSpec.translateInputObject(
                    PropertyDefinitionInput.getDefaultInstance().toBuilder().setType("Foo").build());
        });
    }

    @Test
    @UnitTestMethod(target = PropertyDefinitionTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        PropertyDefinitionTranslationSpec translationSpec = new PropertyDefinitionTranslationSpec();

        assertEquals(PropertyDefinition.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = PropertyDefinitionTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        PropertyDefinitionTranslationSpec translationSpec = new PropertyDefinitionTranslationSpec();

        assertEquals(PropertyDefinitionInput.class, translationSpec.getInputObjectClass());
    }
}
