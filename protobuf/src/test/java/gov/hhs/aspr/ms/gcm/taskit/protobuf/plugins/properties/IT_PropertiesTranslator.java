package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.properties.support.PropertyDefinition;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.support.input.PropertyDefinitionInput;
import gov.hhs.aspr.ms.taskit.core.TranslationController;
import gov.hhs.aspr.ms.taskit.core.TranslationEngineType;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.resourcehelper.ResourceHelper;

public class IT_PropertiesTranslator {
    Path basePath = ResourceHelper.getResourceDir(this.getClass());
    Path filePath = ResourceHelper.createDirectory(basePath, "test-output");

    @Test
    @UnitTestForCoverage
    public void testPropertyValueMapTranslator() {
        String fileName = "propertyDefinition.json";

        ResourceHelper.createFile(filePath, fileName);

        TranslationController translatorController = TranslationController.builder()
                .addTranslationEngine(
                        ProtobufTranslationEngine.builder().addTranslator(PropertiesTranslator.getTranslator()).build())
                .addInputFilePath(filePath.resolve(fileName), PropertyDefinitionInput.class,
                        TranslationEngineType.PROTOBUF)
                .build();

        PropertyDefinition expectedPropertyDefinition = PropertyDefinition.builder()
                .setDefaultValue("defaultValue")
                .setPropertyValueMutability(true)
                .setType(String.class)
                .build();

        translatorController.writeOutput(expectedPropertyDefinition, filePath.resolve(fileName),
                TranslationEngineType.PROTOBUF);
        translatorController.readInput();

        PropertyDefinition actualPropertyDefiniton = translatorController.getFirstObject(PropertyDefinition.class);

        assertEquals(expectedPropertyDefinition, actualPropertyDefiniton);

    }
}
