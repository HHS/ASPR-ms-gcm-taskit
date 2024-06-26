package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports;

import java.util.ArrayList;
import java.util.List;

import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.ReportLabel;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.SimpleReportLabel;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.translationSpecs.ReportLabelTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.translationSpecs.ReportPeriodTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.translationSpecs.SimpleReportLabelTranslationSpec;
import gov.hhs.aspr.ms.taskit.core.TranslationSpec;
import gov.hhs.aspr.ms.taskit.core.Translator;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationEngine;

/**
 * Translator for the Reports Plugin. Using this Translator will add all the
 * necessary TanslationSpecs needed to read and write the ReportsPlugin
 */
public class ReportsTranslator {

    private ReportsTranslator() {
    }

    protected static List<TranslationSpec<?, ?>> getTranslationSpecs() {
        List<TranslationSpec<?, ?>> list = new ArrayList<>();

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
                    ProtobufTranslationEngine.Builder translationEngineBuilder = translatorContext
                            .getTranslationEngineBuilder(ProtobufTranslationEngine.Builder.class);

                    for (TranslationSpec<?, ?> translationSpec : getTranslationSpecs()) {
                        translationEngineBuilder.addTranslationSpec(translationSpec);
                    }

                    translationEngineBuilder.addParentChildClassRelationship(SimpleReportLabel.class,
                            ReportLabel.class);
                });

        return builder;
    }

    /**
     * Returns a Translator that includes TranslationSpecs for the
     * ReportsPlugin.
     */
    public static Translator getTranslator() {
        return builder().build();
    }
}
