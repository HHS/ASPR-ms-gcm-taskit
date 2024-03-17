package gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus;

import java.util.ArrayList;
import java.util.List;

import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.input.SimulationStateInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.testsupport.translationSpecs.ExampleDimensionTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.translationSpecs.DimensionTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.translationSpecs.ExperimentParameterDataTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.translationSpecs.PlannerTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.translationSpecs.SimulationStateTranslationSpec;
import gov.hhs.aspr.ms.taskit.core.TranslationSpec;
import gov.hhs.aspr.ms.taskit.core.Translator;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationEngine;

/**
 * Translator for Nucleus Using this Translator will add all the necessary
 * TanslationSpecs needed to read and write the classes within Nucleus
 */
public class NucleusTranslator {

    private NucleusTranslator() {
    }

    protected static List<TranslationSpec<?, ?>> getTranslationSpecs() {
        List<TranslationSpec<?, ?>> list = new ArrayList<>();

        list.add(new SimulationStateTranslationSpec());
        list.add(new PlannerTranslationSpec());
        list.add(new DimensionTranslationSpec());
        list.add(new ExampleDimensionTranslationSpec());
        list.add(new ExperimentParameterDataTranslationSpec());

        return list;
    }

    /**
     * Returns a Translator Builder that already includes the necessary
     * TranslationSpecs needed to read and write the classes within Nucleus
     */
    private static Translator.Builder builder() {
        Translator.Builder builder = Translator.builder()
                .setTranslatorId(NucleusTranslatorId.TRANSLATOR_ID)
                .setInitializer((translatorContext) -> {
                    ProtobufTranslationEngine.Builder translationEngineBuilder = translatorContext
                            .getTranslationEngineBuilder(ProtobufTranslationEngine.Builder.class);

                    for (TranslationSpec<?, ?> translationSpec : getTranslationSpecs()) {
                        translationEngineBuilder.addTranslationSpec(translationSpec);
                    }

                    translationEngineBuilder.addFieldToIncludeDefaultValue(
                            SimulationStateInput.getDescriptor().findFieldByName("startTime"));
                });

        return builder;
    }

    /**
     * Returns a Translator that includes TranslationSpecs for the
     * classes within Nucleus
     */
    public static Translator getTranslator() {
        return builder().build();
    }
}
