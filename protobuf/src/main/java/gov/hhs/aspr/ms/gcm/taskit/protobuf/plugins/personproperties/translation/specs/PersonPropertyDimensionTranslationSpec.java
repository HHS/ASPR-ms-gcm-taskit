package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.translation.specs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.personproperties.support.PersonPropertyDimensionData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.personproperties.support.PersonPropertyId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.input.DimensionSingleValueInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.support.input.PersonPropertyDimensionDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.support.input.PersonPropertyIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.engine.TaskitObjectHelper;
import gov.hhs.aspr.ms.taskit.protobuf.objects.TaskitObjectInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

public class PersonPropertyDimensionTranslationSpec
        extends ProtobufTranslationSpec<PersonPropertyDimensionDataInput, PersonPropertyDimensionData> {

    @Override
    protected PersonPropertyDimensionData translateInputObject(PersonPropertyDimensionDataInput inputObject) {
        PersonPropertyDimensionData.Builder builder = PersonPropertyDimensionData.builder();

        PersonPropertyId personPropertyId = this.taskitEngine.translateObject(inputObject.getPersonPropertyId());

        builder.setPersonPropertyId(personPropertyId).setTrackTimes(inputObject.getTrackTimes());

         for (DimensionSingleValueInput dimDataInput : inputObject.getValuesList()) {
            String levelName = dimDataInput.getLevelName();
            Object value = TaskitObjectHelper.getValue(dimDataInput.getValue());

            builder.addValue(levelName, value);
        }

        return builder.build();
    }

    @Override
    protected PersonPropertyDimensionDataInput translateAppObject(PersonPropertyDimensionData appObject) {
        PersonPropertyDimensionDataInput.Builder builder = PersonPropertyDimensionDataInput.newBuilder();

        PersonPropertyIdInput personPropertyIdInput = this.taskitEngine
                .translateObjectAsClassSafe(appObject.getPersonPropertyId(), PersonPropertyId.class);

        builder.setPersonPropertyId(personPropertyIdInput).setTrackTimes(appObject.getTrackTimes());

        for (String levelName : appObject.getLevelNames()) {
            int level = appObject.getLevel(levelName);
            Object value = appObject.getValue(level);

            TaskitObjectInput inValue = TaskitObjectHelper.getTaskitObjectInput(value, taskitEngine);

            DimensionSingleValueInput dimInput = DimensionSingleValueInput.newBuilder()
                    .setLevelName(levelName)
                    .setValue(inValue)
                    .build();

            builder.addValues(dimInput);
        }

        return builder.build();
    }

    @Override
    public Class<PersonPropertyDimensionData> getAppObjectClass() {
        return PersonPropertyDimensionData.class;
    }

    @Override
    public Class<PersonPropertyDimensionDataInput> getInputObjectClass() {
        return PersonPropertyDimensionDataInput.class;
    }

}
