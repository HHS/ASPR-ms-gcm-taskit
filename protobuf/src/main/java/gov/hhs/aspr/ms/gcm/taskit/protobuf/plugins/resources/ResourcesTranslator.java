package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources;

import java.util.ArrayList;
import java.util.List;

import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.PeopleTranslatorId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.PropertiesTranslatorId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.RegionsTranslatorId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslatorId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translation.specs.PersonResourceReportPluginDataTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translation.specs.ResourceFilterTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translation.specs.ResourceIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translation.specs.ResourceInitializationTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translation.specs.ResourcePropertyIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translation.specs.ResourcePropertyReportPluginDataTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translation.specs.ResourceReportPluginDataTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translation.specs.ResourcesPluginDataTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translation.specs.TestResourceIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translation.specs.TestResourcePropertyIdTranslationSpec;
import gov.hhs.aspr.ms.taskit.core.translation.Translator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.IProtobufTaskitEngineBuilder;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * Translator for the Resources Plugin. Using this Translator will add all the
 * necessary TanslationSpecs needed to read and write the ResourcesPlugin
 */
public class ResourcesTranslator {

    private ResourcesTranslator() {
    }

    protected static List<ProtobufTranslationSpec<?, ?>> getTranslationSpecs() {
        List<ProtobufTranslationSpec<?, ?>> list = new ArrayList<>();

        list.add(new PersonResourceReportPluginDataTranslationSpec());
        list.add(new ResourceFilterTranslationSpec());
        list.add(new ResourceIdTranslationSpec());
        list.add(new ResourceInitializationTranslationSpec());
        list.add(new ResourcePropertyIdTranslationSpec());
        list.add(new ResourcePropertyReportPluginDataTranslationSpec());
        list.add(new ResourceReportPluginDataTranslationSpec());
        list.add(new ResourcesPluginDataTranslationSpec());
        list.add(new TestResourceIdTranslationSpec());
        list.add(new TestResourcePropertyIdTranslationSpec());

        return list;
    }

    /**
     * Returns a Translator Builder that already includes the necessary
     * TranslationSpecs needed to read and write the ResourcesPlugin
     */
    private static Translator.Builder builder() {
        Translator.Builder builder = Translator.builder()
                .setTranslatorId(ResourcesTranslatorId.TRANSLATOR_ID)
                .addDependency(PeopleTranslatorId.TRANSLATOR_ID)
                .addDependency(PropertiesTranslatorId.TRANSLATOR_ID)
                .addDependency(RegionsTranslatorId.TRANSLATOR_ID)
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
     * ResourcesPlugin.
     */
    public static Translator getTranslator() {
        return builder().build();
    }
}
