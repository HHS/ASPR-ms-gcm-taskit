package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics;

import java.util.ArrayList;
import java.util.List;

import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.translationSpecs.RandomNumberGeneratorIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.translationSpecs.SimpleRandomNumberGeneratorIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.translationSpecs.StochasticsPluginDataTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.translationSpecs.TestRandomGeneratorIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.translationSpecs.WellStateTranslationSpec;
import gov.hhs.aspr.ms.taskit.core.translation.Translator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.IProtobufTaskitEngineBuilder;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * Translator for the Stochastics Plugin. Using this Translator will add all the
 * necessary TanslationSpecs needed to read and write the StochasticsPlugin
 */
public class StochasticsTranslator {

    private StochasticsTranslator() {
    }

    protected static List<ProtobufTranslationSpec<?, ?>> getTranslationSpecs() {
        List<ProtobufTranslationSpec<?, ?>> list = new ArrayList<>();

        list.add(new StochasticsPluginDataTranslationSpec());
        list.add(new WellStateTranslationSpec());
        list.add(new RandomNumberGeneratorIdTranslationSpec());
        list.add(new TestRandomGeneratorIdTranslationSpec());
        list.add(new SimpleRandomNumberGeneratorIdTranslationSpec());
        

        return list;
    }

    /**
     * Returns a Translator Builder that already includes the necessary
     * TranslationSpecs needed to read and write the StochasticsPlugin
     */
    private static Translator.Builder builder() {
        Translator.Builder builder = Translator.builder()
                .setTranslatorId(StochasticsTranslatorId.TRANSLATOR_ID)
                .setInitializer((translatorContext) -> {
                    IProtobufTaskitEngineBuilder taskitEngineBuilder = translatorContext
                            .getTaskitEngineBuilder(IProtobufTaskitEngineBuilder.class);

                    for (ProtobufTranslationSpec<?, ?> translationSpec : getTranslationSpecs()) {
                        taskitEngineBuilder.addTranslationSpec(translationSpec);
                    }
                });

        return builder;
    }

    /**
     * Returns a Translator that includes TranslationSpecs for the
     * StochasticsPlugin.
     */
    public static Translator getTranslator() {
        return builder().build();
    }
}
