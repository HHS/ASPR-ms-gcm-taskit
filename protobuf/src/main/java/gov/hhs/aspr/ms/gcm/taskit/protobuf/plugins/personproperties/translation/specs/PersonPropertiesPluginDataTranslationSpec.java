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
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.data.input.PersonPropertiesPluginDataDenseInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.data.input.PersonPropertiesPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.data.input.PersonPropertiesPluginDataNonDenseInput;
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
    private final int BIG_PEOPLE_CUT_OFF = 10;

    @SuppressWarnings("unchecked")
    @Override
    protected PersonPropertiesPluginData translateInputObject(PersonPropertiesPluginDataInput inputObject) {
        if (!PersonPropertiesPluginData.checkVersionSupported(inputObject.getVersion())) {
            throw new ContractException(TaskitError.UNSUPPORTED_VERSION);
        }
        PersonPropertiesPluginData.Builder builder = PersonPropertiesPluginData.builder();

        if (inputObject.hasDensePluginData()) {
            ByteString byteString = inputObject.getDensePluginData().getDataBytes();

            byte[] bArr = byteString.toByteArray();

            ByteArrayInputStream b = new ByteArrayInputStream(bArr);

            try (ObjectInputStream ois = new ObjectInputStream(b)) {
                int numPeople = ois.readInt();
                int numPropIds = ois.readInt();

                for (int i = 0; i < numPropIds; i++) {
                    PersonPropertyId personPropertyId = (PersonPropertyId) ois.readObject();
                    PropertyDefinitionInput propertyDefinitionInput = (PropertyDefinitionInput) ois.readObject();

                    PropertyDefinition propertyDefinition = this.taskitEngine.translateObject(propertyDefinitionInput);

                    boolean timeTrackingPolicy = ois.readBoolean();
                    double definitionTime = ois.readDouble();

                    builder.definePersonProperty(personPropertyId, propertyDefinition, definitionTime,
                            timeTrackingPolicy);
                    int numValues = ois.readInt();
                    int numTimes = ois.readInt();

                    for (int pId = 0; pId < numPeople; pId++) {
                        PersonId personId = new PersonId(pId);

                        if (pId < numValues) {
                            Object o = ois.readObject();

                            if (o != null) {
                                builder.setPersonPropertyValue(personId, personPropertyId, o);
                            }
                        }

                        if (pId < numTimes) {
                            Double time = (Double) ois.readObject();

                            if (time != null) {
                                builder.setPersonPropertyTime(personId, personPropertyId, time);
                            }
                        }
                    }

                }

                return builder.build();
            } catch (IOException | ClassNotFoundException e) {
                System.err.print(e.getMessage());
            }
        }

        PersonPropertiesPluginDataNonDenseInput nonDenseInput = inputObject.getPluginData();

        Map<Any, PersonPropertyId> personPropIdMap = new LinkedHashMap<>();
        Map<PersonPropertyId, PropertyDefinition> propertyDefinitions = new HashMap<>();

        for (PropertyDefinitionMapInput propertyDefinitionMapInput : nonDenseInput.getPersonPropertyDefinitionsList()) {
            PersonPropertyId propertyId = this.taskitEngine
                    .getObjectFromAny(propertyDefinitionMapInput.getPropertyId());
            personPropIdMap.put(propertyDefinitionMapInput.getPropertyId(), propertyId);

            PropertyDefinition propertyDefinition = this.taskitEngine
                    .translateObject(propertyDefinitionMapInput.getPropertyDefinition());

            builder.definePersonProperty(propertyId, propertyDefinition,
                    propertyDefinitionMapInput.getPropertyDefinitionTime(),
                    propertyDefinitionMapInput.getPropertyTrackingPolicy());

            propertyDefinitions.put(propertyId, propertyDefinition);
        }

        if (nonDenseInput.hasValuesBytes()) {
            ByteString byteString = nonDenseInput.getValuesBytes();

            byte[] bArr = byteString.toByteArray();

            ByteArrayInputStream b = new ByteArrayInputStream(bArr);

            try (ObjectInputStream ois = new ObjectInputStream(b)) {
                List<PersonPropertyId> propertyIds = (List<PersonPropertyId>) ois.readObject();
                BitSet propValIsIndexed = (BitSet) ois.readObject();

                List<Object> indexedValues = (List<Object>) ois.readObject();

                int numPeople = ois.readInt();

                for (int propIndex = 0; propIndex < propertyIds.size(); propIndex++) {
                    PersonPropertyId personPropertyId = propertyIds.get(propIndex);

                    BitSet propValExists = (BitSet) ois.readObject();

                    if (propValIsIndexed.get(propIndex)) {
                        int[] vals = (int[]) ois.readObject();
                        for (int pId = 0; pId < numPeople; pId++) {
                            if (propValExists.get(pId)) {
                                int index = vals[pId];

                                Object value = indexedValues.get(index);
                                builder.setPersonPropertyValue(new PersonId(pId), personPropertyId, value);
                            }
                        }
                    } else {
                        Object[] vals = (Object[]) ois.readObject();
                        for (int pId = 0; pId < numPeople; pId++) {
                            if (propValExists.get(pId)) {
                                Object value = vals[pId];
                                builder.setPersonPropertyValue(new PersonId(pId), personPropertyId, value);
                            }
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            for (PersonPropertyValueMapInput personPropertyValueMapInput : nonDenseInput.getValuesList()
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

        for (PersonPropertyTimeMapInput personPropertyTimeMapInput : nonDenseInput.getPersonPropertyTimesList()) {
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
        PersonPropertiesPluginDataInput.Builder pluginDataBuilder = PersonPropertiesPluginDataInput.newBuilder();

        pluginDataBuilder.setVersion(appObject.getVersion());

        Map<PersonPropertyId, PropertyDefinition> personPropertyDefinitions = appObject.getPropertyDefinitions();
        Map<PersonPropertyId, Boolean> personPropertyTimeTrackingPolicies = appObject.getPropertyTrackingPolicies();
        Map<PersonPropertyId, Double> personPropertyDefinitionTimes = appObject.getPropertyDefinitionTimes();
        Map<PersonPropertyId, List<Object>> personPropertyValues = appObject.getPropertyValues();
        Map<PersonPropertyId, List<Double>> personPropertyTimes = appObject.getPropertyTimes();

        Map<PersonPropertyId, PersonPropertyIdInput> translatedObjectCache = new HashMap<>();

        int _numPeople = 0;
        for (List<Object> values : personPropertyValues.values()) {
            _numPeople = FastMath.max(_numPeople, values.size());
        }

        final int numPeople = _numPeople;

        if (numPeople > BIG_PEOPLE_CUT_OFF) {
            PersonPropertiesPluginDataDenseInput.Builder denseBuilder = PersonPropertiesPluginDataDenseInput
                    .newBuilder();

            ByteArrayOutputStream bos = new ByteArrayOutputStream(numPeople * 3);
            try {
                ObjectOutputStream oos = new ObjectOutputStream(bos);

                oos.writeInt(numPeople);
                oos.writeInt(personPropertyDefinitions.keySet().size());
                for (PersonPropertyId personPropertyId : personPropertyDefinitions.keySet()) {
                    PropertyDefinition propertyDefinition = appObject.getPersonPropertyDefinition(personPropertyId);

                    PropertyDefinitionInput propertyDefinitionInput = this.taskitEngine
                            .translateObject(propertyDefinition);

                    oos.writeObject(personPropertyId);
                    oos.writeObject(propertyDefinitionInput);
                    oos.writeBoolean(personPropertyTimeTrackingPolicies.get(personPropertyId));
                    oos.writeDouble(personPropertyDefinitionTimes.get(personPropertyId));

                    List<Object> values = personPropertyValues.getOrDefault(personPropertyId, new ArrayList<>());
                    List<Double> times = personPropertyTimes.getOrDefault(personPropertyId, new ArrayList<>());

                    Class<?> propClass = propertyDefinition.getType();

                    oos.writeInt(values.size());
                    oos.writeInt(times.size());
                    for (int pId = 0; pId < numPeople; pId++) {
                        if (pId < values.size()) {
                            Object o = values.get(pId);
                            oos.writeObject(propClass.cast(o));
                        }

                        if (pId < times.size()) {
                            Double time = times.get(pId);
                            oos.writeObject(time);
                        }
                    }
                }

                oos.flush();
                oos.close();
                bos.close();

                denseBuilder.setDataBytes(ByteString.copyFrom(bos.toByteArray()));

                pluginDataBuilder.setDensePluginData(denseBuilder);

                return pluginDataBuilder.build();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        PersonPropertiesPluginDataNonDenseInput.Builder builder = PersonPropertiesPluginDataNonDenseInput.newBuilder();

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

        // if (bigPop) {
        // ByteArrayOutputStream bos = new ByteArrayOutputStream(numPeople * 3);
        // try {
        // ObjectOutputStream oos = new ObjectOutputStream(bos);

        // List<PersonPropertyId> propertyIds = new
        // ArrayList<>(personPropertyValues.keySet());
        // List<Object> duplicateValues = new ArrayList<>();
        // List<Object> indexedValues = new ArrayList<>();
        // Map<Object, Integer> indexValueLookupMap = new HashMap<>();

        // oos.writeObject(propertyIds);

        // BitSet propValIsIndexed = new BitSet(propertyIds.size());

        // personPropertyValues.entrySet().parallelStream().forEach((entry) -> {
        // List<Object> distinctValues =
        // entry.getValue().parallelStream().distinct().toList();

        // double distinctValueSize = ((double) distinctValues.size() /
        // entry.getValue().size());

        // boolean shouldIndexValues = distinctValueSize <= 0.25;

        // if (shouldIndexValues) {
        // propValIsIndexed.set(propertyIds.indexOf(entry.getKey()));
        // synchronized (duplicateValues) {
        // duplicateValues.addAll(distinctValues);
        // }
        // }
        // });

        // for (Object o : duplicateValues) {
        // if (indexValueLookupMap.get(o) == null) {
        // indexedValues.add(o);
        // indexValueLookupMap.put(o, indexedValues.size() - 1);
        // }
        // }

        // oos.writeObject(propValIsIndexed);
        // oos.writeObject(duplicateValues);
        // oos.writeInt(numPeople);

        // for (int propIndex = 0; propIndex < propertyIds.size(); propIndex++) {
        // PersonPropertyId personPropertyId = propertyIds.get(propIndex);

        // BitSet propValExists = new BitSet(numPeople);
        // boolean shouldIndexValues = propValIsIndexed.get(propIndex);
        // List<Object> actualPropVals = personPropertyValues.get(personPropertyId);
        // Object[] outputVals = new Object[numPeople];
        // int[] indexedVals = new int[numPeople];

        // Map<Double, PersonPropertyTimeInput.Builder> personPropertyInputBuildersMap =
        // new LinkedHashMap<>();

        // List<Double> propertyTimes =
        // personPropertyTimes.getOrDefault(personPropertyId, new ArrayList<>());

        // for (int pId = 0; pId < numPeople; pId++) {
        // if (pId < actualPropVals.size()) {
        // Object value = actualPropVals.get(pId);

        // if (value != null) {
        // propValExists.set(pId);

        // if (shouldIndexValues) {
        // int index = indexValueLookupMap.get(value);

        // indexedVals[pId] = index;
        // continue;
        // }

        // outputVals[pId] = value;
        // }
        // }

        // if (pId < propertyTimes.size()) {
        // if (propertyTimes.get(pId) != null) {
        // PersonPropertyTimeInput.Builder personPropertyTimeInputBuilder =
        // personPropertyInputBuildersMap
        // .get(propertyTimes.get(pId));

        // if (personPropertyTimeInputBuilder == null) {
        // personPropertyTimeInputBuilder = PersonPropertyTimeInput.newBuilder()
        // .setPropertyValueTime(propertyTimes.get(pId));

        // personPropertyInputBuildersMap.put(propertyTimes.get(pId),
        // personPropertyTimeInputBuilder);
        // }

        // personPropertyTimeInputBuilder.addPId(pId);
        // }
        // }
        // }
        // oos.writeObject(propValExists);

        // if (shouldIndexValues) {
        // oos.writeObject(indexedVals);
        // } else {
        // oos.writeObject(outputVals);
        // }

        // PersonPropertyTimeMapInput.Builder timeMapInputBuilder =
        // PersonPropertyTimeMapInput.newBuilder()
        // .setPersonPropertyId(translatedObjectCache.get(personPropertyId));

        // for (PersonPropertyTimeInput.Builder personPropertyTimeInputBuilder :
        // personPropertyInputBuildersMap
        // .values()) {
        // timeMapInputBuilder.addPropertyTimes(personPropertyTimeInputBuilder.build());
        // }

        // builder.addPersonPropertyTimes(timeMapInputBuilder.build());
        // }

        // oos.flush();
        // oos.close();
        // bos.close();

        // builder.setValuesBytes(ByteString.copyFrom(bos.toByteArray()));
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // }

        PersonPropertyValueMapListInput.Builder valueListBuilder = PersonPropertyValueMapListInput.newBuilder();
        // Person Prop Values
        for (PersonPropertyId personPropertyId : personPropertyValues.keySet()) {
            PersonPropertyValueMapInput.Builder valueMapInputBuilder = PersonPropertyValueMapInput.newBuilder()
                    .setPersonPropertyId(translatedObjectCache.get(personPropertyId));

            PersonPropertyTimeMapInput.Builder timeMapInputBuilder = PersonPropertyTimeMapInput.newBuilder()
                    .setPersonPropertyId(translatedObjectCache.get(personPropertyId));

            List<Object> propertyValues = personPropertyValues.get(personPropertyId);
            List<Double> propertyTimes = personPropertyTimes.getOrDefault(personPropertyId, new ArrayList<>());

            Map<Object, PersonPropertyValueInput.Builder> personPropertyValInputBuildersMap = new LinkedHashMap<>();
            Map<Double, PersonPropertyTimeInput.Builder> personPropertyTimeInputBuildersMap = new LinkedHashMap<>();

            for (int pId = 0; pId < numPeople; pId++) {
                if (pId < propertyValues.size()) {
                    if (propertyValues.get(pId) != null) {
                        Object propertyValue = propertyValues.get(pId);
                        PersonPropertyValueInput.Builder personPropertyValueInputBuilder = personPropertyValInputBuildersMap
                                .get(propertyValue);

                        if (personPropertyValueInputBuilder == null) {
                            personPropertyValueInputBuilder = PersonPropertyValueInput.newBuilder();
                            TaskitObjectInput value = TaskitObjectHelper.getTaskitObjectInput(propertyValue,
                                    taskitEngine);

                            personPropertyValueInputBuilder.setValue(value);
                            personPropertyValInputBuildersMap.put(propertyValue, personPropertyValueInputBuilder);
                        }

                        personPropertyValueInputBuilder.addPId(pId);
                    }
                }

                if (pId < propertyTimes.size()) {
                    if (propertyTimes.get(pId) != null) {
                        PersonPropertyTimeInput.Builder personPropertyTimeInputBuilder = personPropertyTimeInputBuildersMap
                                .get(propertyTimes.get(pId));

                        if (personPropertyTimeInputBuilder == null) {
                            personPropertyTimeInputBuilder = PersonPropertyTimeInput.newBuilder()
                                    .setPropertyValueTime(propertyTimes.get(pId));

                            personPropertyTimeInputBuildersMap.put(propertyTimes.get(pId),
                                    personPropertyTimeInputBuilder);
                        }

                        personPropertyTimeInputBuilder.addPId(pId);
                    }
                }

            }

            PersonPropertyValueListInput.Builder valueListInput = PersonPropertyValueListInput.newBuilder();

            for (PersonPropertyValueInput.Builder personPropertyValueInputBuilder : personPropertyValInputBuildersMap
                    .values()) {
                valueListInput.addValues(personPropertyValueInputBuilder);
            }

            valueMapInputBuilder.setValuesList(valueListInput);

            for (PersonPropertyTimeInput.Builder personPropertyTimeInputBuilder : personPropertyTimeInputBuildersMap
                    .values()) {
                timeMapInputBuilder.addPropertyTimes(personPropertyTimeInputBuilder.build());
            }

            builder.addPersonPropertyTimes(timeMapInputBuilder.build());

            valueListBuilder.addPersonPropertyValues(valueMapInputBuilder.build());
        }

        builder.setValuesList(valueListBuilder);

        pluginDataBuilder.setPluginData(builder);

        return pluginDataBuilder.build();
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
