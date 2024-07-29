package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people;

import java.util.ArrayList;
import java.util.List;

import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.support.input.PersonIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.specs.PeoplePluginDataTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.specs.PersonIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.specs.PersonRangeTranslationSpec;
import gov.hhs.aspr.ms.taskit.core.translation.Translator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.IProtobufTaskitEngineBuilder;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * Translator for the People Plugin. Using this Translator will add all the
 * necessary TanslationSpecs needed to read and write the PeoplePlugin
 */
public class PeopleTranslator {

    private PeopleTranslator() {
    }

    protected static List<ProtobufTranslationSpec<?, ?>> getTranslationSpecs() {
        List<ProtobufTranslationSpec<?, ?>> list = new ArrayList<>();

        list.add(new PeoplePluginDataTranslationSpec());
        list.add(new PersonIdTranslationSpec());
        list.add(new PersonRangeTranslationSpec());

        return list;
    }

    /**
     * Returns a Translator Builder that already includes the necessary
     * TranslationSpecs needed to read and write the PeoplePlugin
     */
    private static Translator.Builder builder() {
        return Translator.builder()
                .setTranslatorId(PeopleTranslatorId.TRANSLATOR_ID)
                .setInitializer((translatorContext) -> {
                    IProtobufTaskitEngineBuilder taskitEngineBuilder = translatorContext
                            .getTaskitEngineBuilder(IProtobufTaskitEngineBuilder.class);

                    for (ProtobufTranslationSpec<?, ?> translationSpec : getTranslationSpecs()) {
                        taskitEngineBuilder.addTranslationSpec(translationSpec);
                    }

                    if (taskitEngineBuilder instanceof ProtobufJsonTaskitEngine.Builder) {
                        ((ProtobufJsonTaskitEngine.Builder) taskitEngineBuilder)
                                .addFieldToIncludeDefaultValue(PersonIdInput.getDescriptor().findFieldByName("id"));
                    }
                });

    }

    /**
     * Returns a Translator that includes TranslationSpecs for the PeoplePlugin.
     */
    public static Translator getTranslator() {
        return builder().build();
    }
}
