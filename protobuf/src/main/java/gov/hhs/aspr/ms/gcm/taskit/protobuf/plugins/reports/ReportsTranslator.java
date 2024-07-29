package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports;

import java.util.ArrayList;
import java.util.List;

import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.translation.specs.ReportLabelTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.translation.specs.ReportPeriodTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.translation.specs.SimpleReportLabelTranslationSpec;
import gov.hhs.aspr.ms.taskit.core.translation.Translator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.IProtobufTaskitEngineBuilder;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * Translator for the Reports Plugin. Using this Translator will add all the
 * necessary TanslationSpecs needed to read and write the ReportsPlugin
 */
public class ReportsTranslator {

    private ReportsTranslator() {
    }

    protected static List<ProtobufTranslationSpec<?, ?>> getTranslationSpecs() {
        List<ProtobufTranslationSpec<?, ?>> list = new ArrayList<>();

        list.add(new ReportLabelTranslationSpec());
        list.add(new ReportPeriodTranslationSpec());
        list.add(new SimpleReportLabelTranslationSpec());

        return list;
    }

    /**
     * Returns a Translator Builder that already includes the necessary
     * TranslationSpecs needed to read and write the ReportsPlugin
     */
    private static Translator.Builder builder() {
        Translator.Builder builder = Translator.builder()
                .setTranslatorId(ReportsTranslatorId.TRANSLATOR_ID)
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
     * Returns a Translator that includes TranslationSpecs for the ReportsPlugin.
     */
    public static Translator getTranslator() {
        return builder().build();
    }
}
