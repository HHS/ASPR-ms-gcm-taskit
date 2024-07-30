package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.properties.support.PropertyDefinition;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.support.input.PropertyDefinitionInput;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitEngineManager;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngineId;
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

        TaskitEngineManager taskitEngineManager = TaskitEngineManager.builder()
                .addTaskitEngine(
                        ProtobufJsonTaskitEngine.builder().addTranslator(PropertiesTranslator.getTranslator()).build())
                .build();

        PropertyDefinition expectedPropertyDefinition = PropertyDefinition.builder()
                .setDefaultValue("defaultValue")
                .setPropertyValueMutability(true)
                .setType(String.class)
                .build();

        taskitEngineManager.translateAndWrite(filePath.resolve(fileName), expectedPropertyDefinition,
                ProtobufTaskitEngineId.JSON_ENGINE_ID);

        PropertyDefinition actualPropertyDefinition = taskitEngineManager.readAndTranslate(filePath.resolve(fileName),
                PropertyDefinitionInput.class, ProtobufTaskitEngineId.JSON_ENGINE_ID);

        assertEquals(expectedPropertyDefinition, actualPropertyDefinition);

    }
}
