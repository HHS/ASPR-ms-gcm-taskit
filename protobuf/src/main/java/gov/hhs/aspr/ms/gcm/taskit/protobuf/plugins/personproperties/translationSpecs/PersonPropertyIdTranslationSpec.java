package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.translationSpecs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.personproperties.support.PersonPropertyId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.support.input.PersonPropertyIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain PersonPropertyIdInput} and {@linkplain PersonPropertyId}
 */
public class PersonPropertyIdTranslationSpec extends ProtobufTranslationSpec<PersonPropertyIdInput, PersonPropertyId> {

    @Override
    protected PersonPropertyId translateInputObject(PersonPropertyIdInput inputObject) {
        return this.taskitEngine.getObjectFromAny(inputObject.getId());
    }

    @Override
    protected PersonPropertyIdInput translateAppObject(PersonPropertyId appObject) {
        return PersonPropertyIdInput.newBuilder().setId(this.taskitEngine.getAnyFromObject(appObject)).build();
    }

    @Override
    public Class<PersonPropertyId> getAppObjectClass() {
        return PersonPropertyId.class;
    }

    @Override
    public Class<PersonPropertyIdInput> getInputObjectClass() {
        return PersonPropertyIdInput.class;
    }

}
