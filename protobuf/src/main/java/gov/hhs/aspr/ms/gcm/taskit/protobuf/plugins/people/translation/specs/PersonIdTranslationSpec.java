package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.specs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.people.support.PersonId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.support.input.PersonIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain PersonIdInput} and {@linkplain PersonId}
 */
public class PersonIdTranslationSpec extends ProtobufTranslationSpec<PersonIdInput, PersonId> {

    @Override
    protected PersonId translateInputObject(PersonIdInput inputObject) {
        return new PersonId(inputObject.getId());
    }

    @Override
    protected PersonIdInput translateAppObject(PersonId appObject) {
        return PersonIdInput.newBuilder().setId(appObject.getValue()).build();
    }

    @Override
    public Class<PersonId> getAppObjectClass() {
        return PersonId.class;
    }

    @Override
    public Class<PersonIdInput> getInputObjectClass() {
        return PersonIdInput.class;
    }

}
