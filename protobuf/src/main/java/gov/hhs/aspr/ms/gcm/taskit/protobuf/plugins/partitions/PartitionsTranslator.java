package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions;

import java.util.ArrayList;
import java.util.List;

import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translationSpecs.AndFilterTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translationSpecs.AttributeFilterTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translationSpecs.AttributeIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translationSpecs.EqualityTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translationSpecs.FalseFilterTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translationSpecs.FilterTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translationSpecs.LabelerTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translationSpecs.NotFilterTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translationSpecs.OrFilterTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translationSpecs.PartitionTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translationSpecs.PartitionsPluginDataTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translationSpecs.TestAttributeIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translationSpecs.TrueFilterTranslationSpec;
import gov.hhs.aspr.ms.taskit.core.translation.Translator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.IProtobufTaskitEngineBuilder;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * Translator for the Partitions Plugin. Using this Translator will add all the
 * necessary TanslationSpecs needed to read and write the PartitionsPlugin.
 */
public class PartitionsTranslator {

    private PartitionsTranslator() {
    }

    protected static List<ProtobufTranslationSpec<?, ?>> getTranslationSpecs() {
        List<ProtobufTranslationSpec<?, ?>> list = new ArrayList<>();

        list.add(new AttributeIdTranslationSpec());
        list.add(new TestAttributeIdTranslationSpec());
        list.add(new AndFilterTranslationSpec());
        list.add(new FalseFilterTranslationSpec());
        list.add(new NotFilterTranslationSpec());
        list.add(new OrFilterTranslationSpec());
        list.add(new TrueFilterTranslationSpec());
        list.add(new AttributeFilterTranslationSpec());
        list.add(new FilterTranslationSpec());
        list.add(new LabelerTranslationSpec());
        list.add(new EqualityTranslationSpec());
        list.add(new PartitionTranslationSpec());
        list.add(new PartitionsPluginDataTranslationSpec());

        return list;
    }

    /**
     * Returns a Translator Builder that already includes the necessary
     * TranslationSpecs needed to read and write the PartitionsPlugin
     */
    private static Translator.Builder builder() {
        Translator.Builder builder = Translator.builder()
                .setTranslatorId(PartitionsTranslatorId.TRANSLATOR_ID)
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
     * PartitionsPlugin.
     */
    public static Translator getTranslator() {
        return builder().build();
    }
}
