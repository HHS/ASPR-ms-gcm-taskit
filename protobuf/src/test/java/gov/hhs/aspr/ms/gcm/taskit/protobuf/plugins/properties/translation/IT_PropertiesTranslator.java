package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.properties.support.PropertyDefinition;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.support.input.PropertyDefinitionInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitEngineManager;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitEngineId;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngineId;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.resourcehelper.ResourceHelper;
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
                .addInputFilePath(filePath.resolve(fileName), PropertyDefinitionInput.class,
                        ProtobufTaskitEngineId.JSON_ENGINE_ID)
                .build();

        PropertyDefinition expectedPropertyDefinition = PropertyDefinition.builder()
                .setDefaultValue("defaultValue")
                .setPropertyValueMutability(true)
                .setType(String.class)
                .build();

        taskitEngineManager.translateAndWrite(expectedPropertyDefinition, filePath.resolve(fileName),
                ProtobufTaskitEngineId.JSON_ENGINE_ID);
        taskitEngineManager.readInput();

        PropertyDefinition actualPropertyDefiniton = taskitEngineManager.getFirstObject(PropertyDefinition.class);

        assertEquals(expectedPropertyDefinition, actualPropertyDefiniton);

    }
}
