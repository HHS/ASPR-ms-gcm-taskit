package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions;

import java.util.ArrayList;
import java.util.List;

import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.PeopleTranslatorId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.PropertiesTranslatorId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.support.input.RegionMembershipInput.RegionPersonInfo;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translationSpecs.RegionFilterTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translationSpecs.RegionIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translationSpecs.RegionPropertyDimensionTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translationSpecs.RegionPropertyIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translationSpecs.RegionPropertyReportPluginDataTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translationSpecs.RegionTransferReportPluginDataTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translationSpecs.RegionsPluginDataTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translationSpecs.SimpleRegionIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translationSpecs.SimpleRegionPropertyIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translationSpecs.TestRegionIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translationSpecs.TestRegionPropertyIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslatorId;
import gov.hhs.aspr.ms.taskit.core.translation.Translator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.IProtobufTaskitEngineBuilder;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * Translator for the Regions Plugin. Using this Translator will add all the
 * necessary TanslationSpecs needed to read and write the RegionsPlugin
 */
public class RegionsTranslator {

    private RegionsTranslator() {
    }

    protected static List<ProtobufTranslationSpec<?, ?>> getTranslationSpecs() {
        List<ProtobufTranslationSpec<?, ?>> list = new ArrayList<>();

        list.add(new RegionFilterTranslationSpec());
        list.add(new RegionIdTranslationSpec());
        list.add(new RegionPropertyDimensionTranslationSpec());
        list.add(new RegionPropertyIdTranslationSpec());
        list.add(new RegionPropertyReportPluginDataTranslationSpec());
        list.add(new RegionsPluginDataTranslationSpec());
        list.add(new RegionTransferReportPluginDataTranslationSpec());
        list.add(new SimpleRegionIdTranslationSpec());
        list.add(new SimpleRegionPropertyIdTranslationSpec());
        list.add(new TestRegionIdTranslationSpec());
        list.add(new TestRegionPropertyIdTranslationSpec());

        return list;
    }

    /**
     * Returns a Translator Builder that already includes the necessary
     * TranslationSpecs needed to read and write the RegionsPlugin
     */
    private static Translator.Builder builder() {
        Translator.Builder builder = Translator.builder()
                .setTranslatorId(RegionsTranslatorId.TRANSLATOR_ID)
                .addDependency(PeopleTranslatorId.TRANSLATOR_ID)
                .addDependency(PropertiesTranslatorId.TRANSLATOR_ID)
                .addDependency(ReportsTranslatorId.TRANSLATOR_ID)
                .setInitializer((translatorContext) -> {
                    IProtobufTaskitEngineBuilder taskitEngineBuilder = translatorContext
                            .getTaskitEngineBuilder(IProtobufTaskitEngineBuilder.class);

                    for (ProtobufTranslationSpec<?, ?> translationSpec : getTranslationSpecs()) {
                        taskitEngineBuilder.addTranslationSpec(translationSpec);
                    }

                    if (taskitEngineBuilder instanceof ProtobufJsonTaskitEngine.Builder) {
                        ((ProtobufJsonTaskitEngine.Builder) taskitEngineBuilder).addFieldToIncludeDefaultValue(
                                RegionPersonInfo.getDescriptor().findFieldByName("personId"));
                    }

                });

        return builder;
    }

    /**
     * Returns a Translator that includes TranslationSpecs for the RegionsPlugin.
     */
    public static Translator getTranslator() {
        return builder().build();
    }
}
