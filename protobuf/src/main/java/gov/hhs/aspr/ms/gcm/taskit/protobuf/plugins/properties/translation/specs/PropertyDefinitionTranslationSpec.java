package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.specs;

import com.google.protobuf.Any;

import gov.hhs.aspr.ms.gcm.simulation.plugins.properties.support.PropertyDefinition;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.support.input.PropertyDefinitionInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain PropertyDefinitionInput} and {@linkplain PropertyDefinition}
 */
public class PropertyDefinitionTranslationSpec
        extends ProtobufTranslationSpec<PropertyDefinitionInput, PropertyDefinition> {

    @Override
    protected PropertyDefinition translateInputObject(PropertyDefinitionInput inputObject) {
        PropertyDefinition.Builder builder = PropertyDefinition.builder();

        builder.setPropertyValueMutability(inputObject.getPropertyValuesAreMutable());

        if (inputObject.hasDefaultValue()) {
            Object defaultValue = this.taskitEngine.translateObject(inputObject.getDefaultValue());
            builder.setDefaultValue(defaultValue);
            builder.setType(defaultValue.getClass());
        } else {
            String type = inputObject.getType();
            Class<?> classType;
            try {
                classType = Class.forName(type);
                builder.setType(classType);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Unable to find class for type: " + type, e);
            }
        }

        return builder.build();
    }

    @Override
    protected PropertyDefinitionInput translateAppObject(PropertyDefinition appObject) {
        PropertyDefinitionInput.Builder builder = PropertyDefinitionInput.newBuilder();
        if (appObject.getDefaultValue().isPresent()) {
            builder.setDefaultValue(
                    (Any) this.taskitEngine.translateObjectAsClassUnsafe(appObject.getDefaultValue().get(), Any.class));
        } else {
            builder.setType(appObject.getType().getName());
        }
        builder.setPropertyValuesAreMutable(appObject.propertyValuesAreMutable());

        return builder.build();
    }

    @Override
    public Class<PropertyDefinition> getAppObjectClass() {
        return PropertyDefinition.class;
    }

    @Override
    public Class<PropertyDefinitionInput> getInputObjectClass() {
        return PropertyDefinitionInput.class;
    }

}
