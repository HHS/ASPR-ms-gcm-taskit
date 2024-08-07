package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.translation.specs;

import com.google.protobuf.Any;

import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.Equality;
import gov.hhs.aspr.ms.gcm.simulation.plugins.personproperties.support.PersonPropertyFilter;
import gov.hhs.aspr.ms.gcm.simulation.plugins.personproperties.support.PersonPropertyId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.support.input.EqualityInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.support.input.PersonPropertyFilterInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.support.input.PersonPropertyIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

public class PersonPropertyFilterTranslationSpec
        extends ProtobufTranslationSpec<PersonPropertyFilterInput, PersonPropertyFilter> {

    @Override
    protected PersonPropertyFilter translateInputObject(PersonPropertyFilterInput inputObject) {
        PersonPropertyId personPropertyId = this.taskitEngine.translateObject(inputObject.getPersonPropertyId());
        Equality equality = this.taskitEngine.translateObject(inputObject.getEquality());
        Object personPropertyValue = this.taskitEngine.getObjectFromAny(inputObject.getPersonPropertyValue());

        return new PersonPropertyFilter(personPropertyId, equality, personPropertyValue);
    }

    @Override
    protected PersonPropertyFilterInput translateAppObject(PersonPropertyFilter appObject) {
        PersonPropertyIdInput personPropertyIdInput = this.taskitEngine
                .translateObjectAsClassSafe(appObject.getPersonPropertyId(), PersonPropertyId.class);
        EqualityInput equalityInput = this.taskitEngine.translateObjectAsClassSafe(appObject.getEquality(),
                Equality.class);
        Any personPropertyValue = this.taskitEngine.getAnyFromObject(appObject.getPersonPropertyValue());

        return PersonPropertyFilterInput.newBuilder()
                .setPersonPropertyId(personPropertyIdInput)
                .setEquality(equalityInput)
                .setPersonPropertyValue(personPropertyValue)
                .build();
    }

    @Override
    public Class<PersonPropertyFilter> getAppObjectClass() {
        return PersonPropertyFilter.class;
    }

    @Override
    public Class<PersonPropertyFilterInput> getInputObjectClass() {
        return PersonPropertyFilterInput.class;
    }

}
