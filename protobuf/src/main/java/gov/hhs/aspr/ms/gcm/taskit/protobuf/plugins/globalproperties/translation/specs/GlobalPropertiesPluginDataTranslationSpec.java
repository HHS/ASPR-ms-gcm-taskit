package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.translation.specs;

import com.google.protobuf.Any;

import gov.hhs.aspr.ms.gcm.simulation.plugins.globalproperties.datamanagers.GlobalPropertiesPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.globalproperties.support.GlobalPropertyId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.properties.support.PropertyDefinition;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.data.input.GlobalPropertiesPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.support.input.PropertyDefinitionInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.support.input.PropertyDefinitionMapInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.support.input.PropertyValueMapInput;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitError;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;
import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain GlobalPropertiesPluginDataInput} and
 * {@linkplain GlobalPropertiesPluginData}
 */
public class GlobalPropertiesPluginDataTranslationSpec
        extends ProtobufTranslationSpec<GlobalPropertiesPluginDataInput, GlobalPropertiesPluginData> {

    @Override
    protected GlobalPropertiesPluginData translateInputObject(GlobalPropertiesPluginDataInput inputObject) {
        if (!GlobalPropertiesPluginData.checkVersionSupported(inputObject.getVersion())) {
            throw new ContractException(TaskitError.UNSUPPORTED_VERSION);
        }

        GlobalPropertiesPluginData.Builder builder = GlobalPropertiesPluginData.builder();

        for (PropertyDefinitionMapInput propertyDefinitionMapInput : inputObject.getGlobalPropertyDefinitinionsList()) {
            GlobalPropertyId propertyId = this.taskitEngine
                    .getObjectFromAny(propertyDefinitionMapInput.getPropertyId());
            PropertyDefinition propertyDefinition = this.taskitEngine
                    .translateObject(propertyDefinitionMapInput.getPropertyDefinition());

            builder.defineGlobalProperty(propertyId, propertyDefinition,
                    propertyDefinitionMapInput.getPropertyDefinitionTime());
        }

        for (PropertyValueMapInput propertyValueMapInput : inputObject.getGlobalPropertyValuesList()) {

            GlobalPropertyId propertyId = this.taskitEngine
                    .getObjectFromAny(propertyValueMapInput.getPropertyId());
            Object value = this.taskitEngine.getObjectFromAny(propertyValueMapInput.getPropertyValue());

            builder.setGlobalPropertyValue(propertyId, value, propertyValueMapInput.getPropertyValueTime());
        }

        return builder.build();
    }

    @Override
    protected GlobalPropertiesPluginDataInput translateAppObject(GlobalPropertiesPluginData appObject) {
        GlobalPropertiesPluginDataInput.Builder builder = GlobalPropertiesPluginDataInput.newBuilder();

        builder.setVersion(appObject.getVersion());

        for (GlobalPropertyId globalPropertyId : appObject.getGlobalPropertyDefinitions().keySet()) {
            PropertyDefinition propertyDefinition = appObject.getGlobalPropertyDefinition(globalPropertyId);

            PropertyDefinitionInput propertyDefinitionInput = this.taskitEngine.translateObject(propertyDefinition);

            PropertyDefinitionMapInput propertyDefinitionMapInput = PropertyDefinitionMapInput.newBuilder()
                    .setPropertyDefinition(propertyDefinitionInput)
                    .setPropertyId(this.taskitEngine.getAnyFromObject(globalPropertyId))
                    .setPropertyDefinitionTime(appObject.getGlobalPropertyDefinitionTime(globalPropertyId))
                    .setPropertyTrackingPolicy(true)
                    .build();

            builder.addGlobalPropertyDefinitinions(propertyDefinitionMapInput);
        }

        for (GlobalPropertyId globalPropertyId : appObject.getGlobalPropertyValues().keySet()) {
            Any propertyValueInput = this.taskitEngine
                    .getAnyFromObject(appObject.getGlobalPropertyValue(globalPropertyId).get());

            PropertyValueMapInput.Builder propertyValueMapInputBuilder = PropertyValueMapInput.newBuilder()
                    .setPropertyId(this.taskitEngine.getAnyFromObject(globalPropertyId))
                    .setPropertyValue(propertyValueInput)
                    .setPropertyValueTime(appObject.getGlobalPropertyTime(globalPropertyId).get());

            builder.addGlobalPropertyValues(propertyValueMapInputBuilder.build());
        }

        return builder.build();
    }

    @Override
    public Class<GlobalPropertiesPluginData> getAppObjectClass() {
        return GlobalPropertiesPluginData.class;
    }

    @Override
    public Class<GlobalPropertiesPluginDataInput> getInputObjectClass() {
        return GlobalPropertiesPluginDataInput.class;
    }

}
