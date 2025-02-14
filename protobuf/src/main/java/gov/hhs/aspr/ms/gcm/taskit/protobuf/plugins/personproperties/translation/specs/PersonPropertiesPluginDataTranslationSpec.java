package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.translation.specs;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.protobuf.Any;
import com.google.type.Date;

import gov.hhs.aspr.ms.gcm.simulation.plugins.people.support.PersonId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.personproperties.datamanagers.PersonPropertiesPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.personproperties.support.PersonPropertyId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.properties.support.PropertyDefinition;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.data.input.PersonPropertiesPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.support.input.PersonPropertyIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.support.input.PersonPropertyTimeInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.support.input.PersonPropertyTimeMapInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.support.input.PersonPropertyValueInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.support.input.PersonPropertyValueMapInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.support.input.PropertyDefinitionInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.support.input.PropertyDefinitionMapInput;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitError;
import gov.hhs.aspr.ms.taskit.protobuf.engine.TaskitObjectHelper;
import gov.hhs.aspr.ms.taskit.protobuf.objects.TaskitObjectInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;
import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain PersonPropertiesPluginDataInput} and
 * {@linkplain PersonPropertiesPluginData}
 */
public class PersonPropertiesPluginDataTranslationSpec
        extends ProtobufTranslationSpec<PersonPropertiesPluginDataInput, PersonPropertiesPluginData> {

    @Override
    protected PersonPropertiesPluginData translateInputObject(PersonPropertiesPluginDataInput inputObject) {
        if (!PersonPropertiesPluginData.checkVersionSupported(inputObject.getVersion())) {
            throw new ContractException(TaskitError.UNSUPPORTED_VERSION);
        }

        PersonPropertiesPluginData.Builder builder = PersonPropertiesPluginData.builder();

        Map<Any, PersonPropertyId> personPropIdMap = new LinkedHashMap<>();

        for (PropertyDefinitionMapInput propertyDefinitionMapInput : inputObject.getPersonPropertyDefinitionsList()) {
            PersonPropertyId propertyId = this.taskitEngine
                    .getObjectFromAny(propertyDefinitionMapInput.getPropertyId());
            personPropIdMap.put(propertyDefinitionMapInput.getPropertyId(), propertyId);

            PropertyDefinition propertyDefinition = this.taskitEngine
                    .translateObject(propertyDefinitionMapInput.getPropertyDefinition());

            builder.definePersonProperty(propertyId, propertyDefinition,
                    propertyDefinitionMapInput.getPropertyDefinitionTime(),
                    propertyDefinitionMapInput.getPropertyTrackingPolicy());
        }

        for (PersonPropertyValueMapInput personPropertyValueMapInput : inputObject.getPersonPropertyValuesList()) {
            PersonPropertyId propertyId = personPropIdMap
                    .get(personPropertyValueMapInput.getPersonPropertyId().getId());
            for (PersonPropertyValueInput personPropertyValueInput : personPropertyValueMapInput
                    .getPropertyValuesList()) {
                Object value = TaskitObjectHelper.getValue(personPropertyValueInput.getValue());
                for (int pId : personPropertyValueInput.getPIdList()) {
                    builder.setPersonPropertyValue(new PersonId(pId), propertyId, value);
                }
            }
        }

        for (PersonPropertyTimeMapInput personPropertyTimeMapInput : inputObject.getPersonPropertyTimesList()) {
            PersonPropertyId propertyId = personPropIdMap.get(personPropertyTimeMapInput.getPersonPropertyId().getId());
            for (PersonPropertyTimeInput personPropertyTimeInput : personPropertyTimeMapInput.getPropertyTimesList()) {
                for (int pId : personPropertyTimeInput.getPIdList()) {
                    builder.setPersonPropertyTime(new PersonId(pId), propertyId,
                            personPropertyTimeInput.getPropertyValueTime());
                }
            }

            personPropertyTimeMapInput = null;
        }

        return builder.build();
    }

    @Override
    protected PersonPropertiesPluginDataInput translateAppObject(PersonPropertiesPluginData appObject) {
        PersonPropertiesPluginDataInput.Builder builder = PersonPropertiesPluginDataInput.newBuilder();

        builder.setVersion(appObject.getVersion());

        Map<PersonPropertyId, PropertyDefinition> personPropertyDefinitions = appObject.getPropertyDefinitions();
        Map<PersonPropertyId, Boolean> personPropertyTimeTrackingPolicies = appObject.getPropertyTrackingPolicies();
        Map<PersonPropertyId, Double> personPropertyDefinitionTimes = appObject.getPropertyDefinitionTimes();
        Map<PersonPropertyId, List<Object>> personPropertyValues = appObject.getPropertyValues();
        Map<PersonPropertyId, List<Double>> personPropertyTimes = appObject.getPropertyTimes();

        // Person Prop Defs
        for (PersonPropertyId personPropertyId : personPropertyDefinitions.keySet()) {
            PropertyDefinition propertyDefinition = appObject.getPersonPropertyDefinition(personPropertyId);

            PropertyDefinitionInput propertyDefinitionInput = this.taskitEngine.translateObject(propertyDefinition);
            double propertyDefinitionTime = personPropertyDefinitionTimes.get(personPropertyId);
            boolean propertyTrackingPolicy = personPropertyTimeTrackingPolicies.get(personPropertyId);

            PropertyDefinitionMapInput propertyDefinitionMapInput = PropertyDefinitionMapInput.newBuilder()
                    .setPropertyDefinition(propertyDefinitionInput)
                    .setPropertyId(this.taskitEngine.getAnyFromObject(personPropertyId))
                    .setPropertyDefinitionTime(propertyDefinitionTime)
                    .setPropertyTrackingPolicy(propertyTrackingPolicy)
                    .build();

            builder.addPersonPropertyDefinitions(propertyDefinitionMapInput);
        }

        // Person Prop Values
        for (PersonPropertyId personPropertyId : personPropertyValues.keySet()) {
            Map<Object, PersonPropertyValueInput.Builder> personPropertyInputBuildersMap = new LinkedHashMap<>();

            List<Object> propertyValues = personPropertyValues.get(personPropertyId);

            for (int i = 0; i < propertyValues.size(); i++) {
                if (propertyValues.get(i) != null) {
                    Object propertyValue = propertyValues.get(i);
                    PersonPropertyValueInput.Builder personPropertyValueInputBuilder = personPropertyInputBuildersMap
                            .get(propertyValue);

                    if (personPropertyValueInputBuilder == null) {
                        personPropertyValueInputBuilder = PersonPropertyValueInput.newBuilder();
                        TaskitObjectInput value = TaskitObjectHelper.getTaskitObjectInput(propertyValue, taskitEngine);

                        personPropertyValueInputBuilder.setValue(value);
                        personPropertyInputBuildersMap.put(propertyValues.get(i), personPropertyValueInputBuilder);
                    }

                    personPropertyValueInputBuilder.addPId(i);
                }
            }

            PersonPropertyValueMapInput.Builder valueMapInputBuilder = PersonPropertyValueMapInput.newBuilder()
                    .setPersonPropertyId((PersonPropertyIdInput) this.taskitEngine
                            .translateObjectAsClassSafe(personPropertyId, PersonPropertyId.class));

            for (PersonPropertyValueInput.Builder personPropertyValueInputBuilder : personPropertyInputBuildersMap
                    .values()) {

                valueMapInputBuilder.addPropertyValues(personPropertyValueInputBuilder.build());

            }

            builder.addPersonPropertyValues(valueMapInputBuilder.build());
        }

        // Person Prop Times
        for (PersonPropertyId personPropertyId : personPropertyTimes.keySet()) {

            Map<Double, PersonPropertyTimeInput.Builder> personPropertyInputBuildersMap = new LinkedHashMap<>();

            List<Double> propertyTimes = personPropertyTimes.get(personPropertyId);

            for (int i = 0; i < propertyTimes.size(); i++) {
                if (propertyTimes.get(i) != null) {
                    PersonPropertyTimeInput.Builder personPropertyTimeInputBuilder = personPropertyInputBuildersMap
                            .get(propertyTimes.get(i));

                    if (personPropertyTimeInputBuilder == null) {
                        personPropertyTimeInputBuilder = PersonPropertyTimeInput.newBuilder()
                                .setPropertyValueTime(propertyTimes.get(i));

                        personPropertyInputBuildersMap.put(propertyTimes.get(i), personPropertyTimeInputBuilder);
                    }

                    personPropertyTimeInputBuilder.addPId(i);

                }
            }

            PersonPropertyTimeMapInput.Builder timeMapInputBuilder = PersonPropertyTimeMapInput.newBuilder()
                    .setPersonPropertyId((PersonPropertyIdInput) this.taskitEngine
                            .translateObjectAsClassSafe(personPropertyId, PersonPropertyId.class));

            for (PersonPropertyTimeInput.Builder personPropertyTimeInputBuilder : personPropertyInputBuildersMap
                    .values()) {

                timeMapInputBuilder.addPropertyTimes(personPropertyTimeInputBuilder.build());

            }

            builder.addPersonPropertyTimes(timeMapInputBuilder.build());
        }

        return builder.build();
    }

    @Override
    public Class<PersonPropertiesPluginData> getAppObjectClass() {
        return PersonPropertiesPluginData.class;
    }

    @Override
    public Class<PersonPropertiesPluginDataInput> getInputObjectClass() {
        return PersonPropertiesPluginDataInput.class;
    }

}
