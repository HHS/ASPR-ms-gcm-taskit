package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties;

import java.util.ArrayList;
import java.util.List;

import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.translation.specs.GlobalPropertiesPluginDataTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.translation.specs.GlobalPropertyDimensionTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.translation.specs.GlobalPropertyIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.translation.specs.GlobalPropertyReportPluginDataTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.translation.specs.TestGlobalPropertyIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.PropertiesTranslatorId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslatorId;
import gov.hhs.aspr.ms.taskit.core.translation.Translator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.IProtobufTaskitEngineBuilder;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * Translator for the GlobalProperties Plugin. Using this Translator will add
 * all the necessary TanslationSpecs needed to read and write
 * the GlobalPropertiesPlugin
 */
public class GlobalPropertiesTranslator {

    private GlobalPropertiesTranslator() {
    }

    protected static List<ProtobufTranslationSpec<?, ?>> getTranslationSpecs() {
        List<ProtobufTranslationSpec<?, ?>> list = new ArrayList<>();

        list.add(new GlobalPropertiesPluginDataTranslationSpec());
        list.add(new GlobalPropertyIdTranslationSpec());
        list.add(new GlobalPropertyDimensionTranslationSpec());
        list.add(new TestGlobalPropertyIdTranslationSpec());
        list.add(new GlobalPropertyReportPluginDataTranslationSpec());

        return list;
    }

    /**
     * Returns a Translator Builder that already includes the necessary
     * TranslationSpecs needed to read and write the GlobalPropertiesPluginData and its
     * respective reports
     */
    private static Translator.Builder builder() {

        Translator.Builder builder = Translator.builder()
                .setTranslatorId(GlobalPropertiesTranslatorId.TRANSLATOR_ID)
                .addDependency(PropertiesTranslatorId.TRANSLATOR_ID)
                .addDependency(ReportsTranslatorId.TRANSLATOR_ID)
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
     * GlobalPropertiesPlugin.
     */
    public static Translator getTranslator() {
        return builder().build();
    }
}
