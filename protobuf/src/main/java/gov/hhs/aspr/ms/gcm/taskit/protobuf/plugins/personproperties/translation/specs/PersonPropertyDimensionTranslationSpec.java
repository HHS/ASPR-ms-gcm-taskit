package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.translation.specs;

import com.google.protobuf.Any;

import gov.hhs.aspr.ms.gcm.simulation.plugins.personproperties.support.PersonPropertyDimension;
import gov.hhs.aspr.ms.gcm.simulation.plugins.personproperties.support.PersonPropertyId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.support.input.PersonPropertyDimensionInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.support.input.PersonPropertyIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

public class PersonPropertyDimensionTranslationSpec
        extends ProtobufTranslationSpec<PersonPropertyDimensionInput, PersonPropertyDimension> {

    @Override
    protected PersonPropertyDimension translateInputObject(PersonPropertyDimensionInput inputObject) {
        PersonPropertyDimension.Builder builder = PersonPropertyDimension.builder();

        PersonPropertyId personPropertyId = this.taskitEngine.translateObject(inputObject.getPersonPropertyId());

        builder.setPersonPropertyId(personPropertyId).setTrackTimes(inputObject.getTrackTimes());

        for (Any anyValue : inputObject.getValuesList()) {
            Object value = this.taskitEngine.getObjectFromAny(anyValue);
            builder.addValue(value);
        }

        return builder.build();
    }

    @Override
    protected PersonPropertyDimensionInput translateAppObject(PersonPropertyDimension appObject) {
        PersonPropertyDimensionInput.Builder builder = PersonPropertyDimensionInput.newBuilder();

        PersonPropertyIdInput personPropertyIdInput = this.taskitEngine
                .translateObjectAsClassSafe(appObject.getPersonPropertyId(), PersonPropertyId.class);

        builder.setPersonPropertyId(personPropertyIdInput).setTrackTimes(appObject.getTrackTimes());

        for (Object objValue : appObject.getValues()) {
            builder.addValues(this.taskitEngine.getAnyFromObject(objValue));
        }

        return builder.build();
    }

    @Override
    public Class<PersonPropertyDimension> getAppObjectClass() {
        return PersonPropertyDimension.class;
    }

    @Override
    public Class<PersonPropertyDimensionInput> getInputObjectClass() {
        return PersonPropertyDimensionInput.class;
    }

}
