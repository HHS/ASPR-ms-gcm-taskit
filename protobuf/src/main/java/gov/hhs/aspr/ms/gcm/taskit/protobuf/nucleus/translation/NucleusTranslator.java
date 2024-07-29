package gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.translation;

import java.util.ArrayList;
import java.util.List;

import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.input.ExperimentParameterDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.input.SimulationStateInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.testsupport.translation.specs.ExampleDimensionTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.translation.specs.DimensionTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.translation.specs.ExperimentParameterDataTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.translation.specs.PlannerTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.translation.specs.SimulationStateTranslationSpec;
import gov.hhs.aspr.ms.taskit.core.translation.Translator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.IProtobufTaskitEngineBuilder;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * Translator for Nucleus Using this Translator will add all the necessary
 * TanslationSpecs needed to read and write the classes within Nucleus
 */
public class NucleusTranslator {

    private NucleusTranslator() {
    }

    protected static List<ProtobufTranslationSpec<?, ?>> getTranslationSpecs() {
        List<ProtobufTranslationSpec<?, ?>> list = new ArrayList<>();

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
                    IProtobufTaskitEngineBuilder taskitEngineBuilder = translatorContext
                            .getTaskitEngineBuilder(IProtobufTaskitEngineBuilder.class);

                    for (ProtobufTranslationSpec<?, ?> translationSpec : getTranslationSpecs()) {
                        taskitEngineBuilder.addTranslationSpec(translationSpec);
                    }

                    if (taskitEngineBuilder instanceof ProtobufJsonTaskitEngine.Builder) {
                        ((ProtobufJsonTaskitEngine.Builder) taskitEngineBuilder).addFieldToIncludeDefaultValue(
                                SimulationStateInput.getDescriptor().findFieldByName("startTime"));

                        ((ProtobufJsonTaskitEngine.Builder) taskitEngineBuilder)
                                .addFieldToIncludeDefaultValue(
                                        ExperimentParameterDataInput.getDescriptor().findFieldByName("threadCount"))
                                .addFieldToIncludeDefaultValue(ExperimentParameterDataInput.getDescriptor()
                                        .findFieldByName("startRecordingIsScheduled"))
                                .addFieldToIncludeDefaultValue(ExperimentParameterDataInput.getDescriptor()
                                        .findFieldByName("simulationHaltTime"))
                                .addFieldToIncludeDefaultValue(
                                        ExperimentParameterDataInput.getDescriptor().findFieldByName("haltOnException"))
                                .addFieldToIncludeDefaultValue(ExperimentParameterDataInput.getDescriptor()
                                        .findFieldByName("experimentProgressLogPath"))
                                .addFieldToIncludeDefaultValue(ExperimentParameterDataInput.getDescriptor()
                                        .findFieldByName("continueFromProgressLog"));
                    }

                });

        return builder;
    }

    /**
     * Returns a Translator that includes TranslationSpecs for the classes within
     * Nucleus
     */
    public static Translator getTranslator() {
        return builder().build();
    }
}
