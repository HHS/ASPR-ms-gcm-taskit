package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.translation.specs;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.FastMath;

import com.google.protobuf.Any;
import com.google.protobuf.ByteString;

import gov.hhs.aspr.ms.gcm.simulation.plugins.people.support.PersonId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.personproperties.datamanagers.PersonPropertiesPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.personproperties.support.PersonPropertyId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.properties.support.PropertyDefinition;
import gov.hhs.aspr.ms.gcm.simulation.plugins.properties.support.arraycontainers.IntValueContainer;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.data.input.PersonPropertiesPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.data.input.PersonPropertyValueMapListInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.support.input.PersonPropertyIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.support.input.PersonPropertyTimeInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.support.input.PersonPropertyTimeMapInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.support.input.PersonPropertyValueInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.support.input.PersonPropertyValueListInput;
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

    private final int PEOPLE_CUT_OFF = 10_000;
    private final int BIG_PEOPLE_CUT_OFF = 100_000;
    private final boolean serializeProtoClasses = true;

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

        if (inputObject.hasValuesBytes()) {
            ByteString byteString = inputObject.getValuesBytes();

            byte[] bArr = byteString.toByteArray();

            ByteArrayInputStream b = new ByteArrayInputStream(bArr);

            try (ObjectInputStream ois = new ObjectInputStream(b)) {
                @SuppressWarnings("unchecked")
                List<PersonPropertyId> personPropertyIds = (List<PersonPropertyId>) ois.readObject();

                Object[] valueLookup = (Object[]) ois.readObject();
                int[][] allPropVals = (int[][]) ois.readObject();

                int numPeople = allPropVals.length;

                for (int pId = 0; pId < numPeople; pId++) {
                    int[] propVals = allPropVals[pId];

                    for (int propIndex = 0; propIndex < propVals.length; propIndex++) {
                        PersonPropertyId propId = personPropertyIds.get(propIndex);
                        int valueIndex = propVals[propIndex];
                        if (valueIndex != -1) {
                            Object val = valueLookup[valueIndex];

                            if (val != null) {
                                builder.setPersonPropertyValue(new PersonId(pId), propId, val);
                            }
                        }

                    }
                }
                // int numProperties = ois.readInt();

                // for (int i = 0; i < numProperties; i++) {
                // PersonPropertyId propId = (PersonPropertyId) ois.readObject();

                // boolean isPid = ois.readBoolean();

                // if (isPid) {
                // int numVals = ois.readInt();

                // for (int j = 0; j < numVals; j++) {
                // int pId = ois.readInt();
                // Object o = ois.readObject();

                // builder.setPersonPropertyValue(new PersonId(pId), propId, o);
                // }
                // } else {
                // int numPropVals = ois.readInt();

                // for (int j = 0; j < numPropVals; j++) {
                // Object o = ois.readObject();
                // @SuppressWarnings("unchecked")
                // List<Integer> pIds = (List<Integer>) ois.readObject();

                // for (Integer pId : pIds) {
                // builder.setPersonPropertyValue(new PersonId(pId), propId, o);
                // }
                // }
                // }

                // }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            for (PersonPropertyValueMapInput personPropertyValueMapInput : inputObject.getValuesList()
                    .getPersonPropertyValuesList()) {
                PersonPropertyId propertyId = personPropIdMap
                        .get(personPropertyValueMapInput.getPersonPropertyId().getId());

                if (personPropertyValueMapInput.hasValuesList()) {
                    for (PersonPropertyValueInput personPropertyValueInput : personPropertyValueMapInput.getValuesList()
                            .getValuesList()) {
                        Object value = TaskitObjectHelper.getValue(personPropertyValueInput.getValue());
                        for (int pId : personPropertyValueInput.getPIdList()) {
                            builder.setPersonPropertyValue(new PersonId(pId), propertyId, value);
                        }
                    }
                } else {
                    ByteString byteString = personPropertyValueMapInput.getValuesBytes();

                    byte[] bArr = byteString.toByteArray();

                    ByteArrayInputStream b = new ByteArrayInputStream(bArr);

                    try (ObjectInputStream o = new ObjectInputStream(b)) {
                        @SuppressWarnings("unchecked")
                        List<Object> values = (List<Object>) o.readObject();

                        for (int i = 0; i < values.size(); i++) {
                            if (values.get(i) != null) {
                                builder.setPersonPropertyValue(new PersonId(i), propertyId, values.get(i));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

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

        Map<PersonPropertyId, PersonPropertyIdInput> translatedObjectCache = new HashMap<>();

        // Person Prop Defs
        for (PersonPropertyId personPropertyId : personPropertyDefinitions.keySet()) {
            translatedObjectCache.put(personPropertyId, (PersonPropertyIdInput) this.taskitEngine
                    .translateObjectAsClassSafe(personPropertyId, PersonPropertyId.class));

            PropertyDefinition propertyDefinition = appObject.getPersonPropertyDefinition(personPropertyId);

            PropertyDefinitionInput propertyDefinitionInput = this.taskitEngine.translateObject(propertyDefinition);
            double propertyDefinitionTime = personPropertyDefinitionTimes.get(personPropertyId);
            boolean propertyTrackingPolicy = personPropertyTimeTrackingPolicies.get(personPropertyId);

            PropertyDefinitionMapInput propertyDefinitionMapInput = PropertyDefinitionMapInput.newBuilder()
                    .setPropertyDefinition(propertyDefinitionInput)
                    .setPropertyId(translatedObjectCache.get(personPropertyId).getId())
                    .setPropertyDefinitionTime(propertyDefinitionTime)
                    .setPropertyTrackingPolicy(propertyTrackingPolicy)
                    .build();

            builder.addPersonPropertyDefinitions(propertyDefinitionMapInput);

        }

        boolean bigPop = false;
        int numPeople = 0;
        for (List<Object> values : personPropertyValues.values()) {
            bigPop |= values.size() > BIG_PEOPLE_CUT_OFF;
            numPeople = FastMath.max(numPeople, values.size());
        }

        if (bigPop) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                ObjectOutputStream oos = new ObjectOutputStream(bos);

                int[][] propertyValuesOutput = new int[numPeople][];
                List<PersonPropertyId> propertyIds = new ArrayList<>();
                Map<Object, Integer> valueMap = new LinkedHashMap<>();

                for (PersonPropertyId id : personPropertyValues.keySet()) {
                    propertyIds.add(id);
                }

                oos.writeObject(propertyIds);
                for (int i = 0; i < numPeople; i++) {
                    propertyValuesOutput[i] = new int[propertyIds.size()];
                }

                for (int propIndex = 0; propIndex < propertyIds.size(); propIndex++) {
                    PersonPropertyId propertyId = propertyIds.get(propIndex);
                    PropertyDefinition propertyDefinition = personPropertyDefinitions.get(propertyId);
                    Class<?> propClass = propertyDefinition.getType();

                    List<Object> propVals = personPropertyValues.get(propertyId);

                    BitSet

                    for (int pId = 0; pId < numPeople; pId++) {
                        int[] vals = propertyValuesOutput[pId];
                        if (pId >= propVals.size()) {
                            vals[propIndex] = -1;
                            continue;
                        }
                        Object val = propVals.get(pId);

                        if (val == null) {
                            vals[propIndex] = -1;
                            continue;
                        }

                        if(propertyDefinition.getDefaultValue().isPresent() && val.equals(propertyDefinition.getDefaultValue().get())) {
                            vals[propIndex] = -1;
                            continue;
                        }

                        if (valueMap.get(val) == null) {
                            valueMap.put(propClass.cast(val), valueMap.size());
                        }

                        vals[propIndex] = valueMap.get(propClass.cast(val));
                    }
                }

                Object[] valueLookup = new Object[valueMap.keySet().size()];

                for (Object val : valueMap.keySet()) {
                    int index = valueMap.get(val);
                    valueLookup[index] = val;
                }

                oos.writeObject(valueLookup);
                oos.writeObject(propertyValuesOutput);

                // for (PersonPropertyId personPropertyId : personPropertyValues.keySet()) {
                // List<Object> propertyValues = personPropertyValues.get(personPropertyId);

                // PropertyDefinition propertyDefinition =
                // personPropertyDefinitions.get(personPropertyId);

                // Class<?> propClass = propertyDefinition.getType();

                // oos.writeObject(personPropertyId);

                // Map<Integer, Object> pIdToValMap = new HashMap<>();
                // Map<Object, List<Integer>> valToPidMap = new HashMap<>();

                // for (int i = 0; i < propertyValues.size(); i++) {
                // Object o = propertyValues.get(i);

                // if (o != null) {
                // pIdToValMap.put(i, propClass.cast(o));
                // if (valToPidMap.get(propClass.cast(o)) == null) {
                // valToPidMap.put(propClass.cast(o), new ArrayList<>());
                // }
                // valToPidMap.get(propClass.cast(o)).add(i);
                // }
                // }

                // if (pIdToValMap.keySet().size() < valToPidMap.keySet().size()) {
                // oos.writeBoolean(true);
                // oos.writeInt(pIdToValMap.keySet().size());

                // for (int i = 0; i < propertyValues.size(); i++) {
                // Object o = pIdToValMap.get(i);

                // if (o != null) {
                // oos.writeInt(i);
                // oos.writeObject(propClass.cast(o));
                // }

                // }
                // } else {
                // oos.writeBoolean(false);
                // oos.writeInt(valToPidMap.keySet().size());

                // for (Object o : valToPidMap.keySet()) {
                // Object castedO = propClass.cast(o);

                // oos.writeObject(castedO);
                // oos.writeObject(valToPidMap.get(o));
                // }
                // }

                // }

                oos.flush();
                oos.close();
                bos.close();

                builder.setValuesBytes(ByteString.copyFrom(bos.toByteArray()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!bigPop) {
            PersonPropertyValueMapListInput.Builder valueListBuilder = PersonPropertyValueMapListInput.newBuilder();
            // Person Prop Values
            for (PersonPropertyId personPropertyId : personPropertyValues.keySet()) {
                PersonPropertyValueMapInput.Builder valueMapInputBuilder = PersonPropertyValueMapInput.newBuilder()
                        .setPersonPropertyId(translatedObjectCache.get(personPropertyId));

                List<Object> propertyValues = personPropertyValues.get(personPropertyId);

                if (propertyValues.size() > PEOPLE_CUT_OFF) {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ObjectOutputStream oos;
                    try {
                        oos = new ObjectOutputStream(bos);
                        oos.writeObject(propertyValues);
                        oos.flush();

                        ByteString byteString = ByteString.copyFrom(bos.toByteArray());
                        valueMapInputBuilder.setValuesBytes(byteString);

                        oos.close();
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    Map<Object, PersonPropertyValueInput.Builder> personPropertyInputBuildersMap = new LinkedHashMap<>();

                    for (int i = 0; i < propertyValues.size(); i++) {
                        if (propertyValues.get(i) != null) {
                            Object propertyValue = propertyValues.get(i);
                            PersonPropertyValueInput.Builder personPropertyValueInputBuilder = personPropertyInputBuildersMap
                                    .get(propertyValue);

                            if (personPropertyValueInputBuilder == null) {
                                personPropertyValueInputBuilder = PersonPropertyValueInput.newBuilder();
                                TaskitObjectInput value = TaskitObjectHelper.getTaskitObjectInput(propertyValue,
                                        taskitEngine);

                                personPropertyValueInputBuilder.setValue(value);
                                personPropertyInputBuildersMap.put(propertyValue, personPropertyValueInputBuilder);
                            }

                            personPropertyValueInputBuilder.addPId(i);
                        }
                    }

                    PersonPropertyValueListInput.Builder valueListInput = PersonPropertyValueListInput.newBuilder();

                    for (PersonPropertyValueInput.Builder personPropertyValueInputBuilder : personPropertyInputBuildersMap
                            .values()) {
                        valueListInput.addValues(personPropertyValueInputBuilder);
                    }

                    if (serializeProtoClasses) {
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        ObjectOutputStream oos;
                        try {
                            oos = new ObjectOutputStream(bos);
                            oos.writeObject(valueListInput.build());
                            oos.flush();

                            ByteString byteString = ByteString.copyFrom(bos.toByteArray());
                            valueMapInputBuilder.setValuesBytes(byteString);

                            oos.close();
                            bos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        valueMapInputBuilder.setValuesList(valueListInput);
                    }
                }

                valueListBuilder.addPersonPropertyValues(valueMapInputBuilder.build());
            }

            builder.setValuesList(valueListBuilder);
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
