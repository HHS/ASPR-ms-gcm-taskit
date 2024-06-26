package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.properties.support.PropertyDefinition;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.support.input.PropertyDefinitionInput;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationEngine;
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
    public void testConvertObject() {
        ProtobufTranslationEngine protobufTranslationEngine = ProtobufTranslationEngine.builder()
                .addTranslator(PropertiesTranslator.getTranslator())
                .build();

        PropertyDefinitionTranslationSpec translationSpec = new PropertyDefinitionTranslationSpec();
        translationSpec.init(protobufTranslationEngine);

        PropertyDefinition expectedAppValue = PropertyDefinition.builder()
                .setDefaultValue("defaultValue")
                .setPropertyValueMutability(true)
                .setType(String.class)
                .build();

        PropertyDefinitionInput inputValue = translationSpec.convertAppObject(expectedAppValue);

        PropertyDefinition actualAppValue = translationSpec.convertInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);

        expectedAppValue = PropertyDefinition.builder().setPropertyValueMutability(true).setType(String.class).build();

        inputValue = translationSpec.convertAppObject(expectedAppValue);
        actualAppValue = translationSpec.convertInputObject(inputValue);

        assertEquals(expectedAppValue, actualAppValue);

        assertThrows(RuntimeException.class, () -> {
            translationSpec.convertInputObject(
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
