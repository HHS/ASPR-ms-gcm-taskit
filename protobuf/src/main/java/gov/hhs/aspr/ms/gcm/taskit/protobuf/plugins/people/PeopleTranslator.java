package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people;

import java.util.ArrayList;
import java.util.List;

import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.support.input.PersonIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translationSpecs.PeoplePluginDataTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translationSpecs.PersonIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translationSpecs.PersonRangeTranslationSpec;
import gov.hhs.aspr.ms.taskit.core.TranslationSpec;
import gov.hhs.aspr.ms.taskit.core.Translator;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationEngine;

/**
 * Translator for the People Plugin. Using this Translator will add all the
 * necessary TanslationSpecs needed to read and write the PeoplePlugin
 */
public class PeopleTranslator {

    private PeopleTranslator() {
    }

    protected static List<TranslationSpec<?, ?>> getTranslationSpecs() {
        List<TranslationSpec<?, ?>> list = new ArrayList<>();

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
                    ProtobufTranslationEngine.Builder translationEngineBuilder = translatorContext
                            .getTranslationEngineBuilder(ProtobufTranslationEngine.Builder.class);

                    translationEngineBuilder.addTranslationSpec(new PeoplePluginDataTranslationSpec())
                            .addTranslationSpec(new PersonIdTranslationSpec())
                            .addTranslationSpec(new PersonRangeTranslationSpec());

                    translationEngineBuilder
                            .addFieldToIncludeDefaultValue(PersonIdInput.getDescriptor().findFieldByName("id"));
                });

    }

    /**
     * Returns a Translator that includes TranslationSpecs for the
     * PeoplePlugin.
     */
    public static Translator getTranslator() {
        return builder().build();
    }
}
