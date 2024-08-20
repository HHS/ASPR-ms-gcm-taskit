package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.specs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.people.support.PersonRange;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.support.input.PersonRangeInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain PersonRangeInput} and {@linkplain PersonRange}
 */
public class PersonRangeTranslationSpec extends ProtobufTranslationSpec<PersonRangeInput, PersonRange> {

    @Override
    protected PersonRange translateInputObject(PersonRangeInput inputObject) {
        return new PersonRange(inputObject.getLowPersonId(), inputObject.getHighPersonId());
    }

    @Override
    protected PersonRangeInput translateAppObject(PersonRange appObject) {
        return PersonRangeInput.newBuilder()
                .setLowPersonId(appObject.getLowPersonId())
                .setHighPersonId(appObject.getHighPersonId())
                .build();
    }

    @Override
    public Class<PersonRange> getAppObjectClass() {
        return PersonRange.class;
    }

    @Override
    public Class<PersonRangeInput> getInputObjectClass() {
        return PersonRangeInput.class;
    }

}
