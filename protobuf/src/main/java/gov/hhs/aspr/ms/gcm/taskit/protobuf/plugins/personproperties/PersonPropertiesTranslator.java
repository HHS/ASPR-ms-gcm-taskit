package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties;

import java.util.ArrayList;
import java.util.List;

import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.PeopleTranslatorId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.support.input.PersonPropertyValueInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.translationSpecs.PersonPropertiesPluginDataTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.translationSpecs.PersonPropertyDimensionTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.translationSpecs.PersonPropertyFilterTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.translationSpecs.PersonPropertyIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.translationSpecs.PersonPropertyInteractionReportPluginDataTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.translationSpecs.PersonPropertyReportPluginDataTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.translationSpecs.TestPersonPropertyIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.PropertiesTranslatorId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslatorId;
import gov.hhs.aspr.ms.taskit.core.translation.Translator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.IProtobufTaskitEngineBuilder;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * Translator for the PersonProperties Plugin. Using this Translator will add
 * all the necessary TanslationSpecs needed to read and write the
 * PersonPropertiesPluginData
 */
public class PersonPropertiesTranslator {

    private PersonPropertiesTranslator() {
    }

    protected static List<ProtobufTranslationSpec<?, ?>> getTranslationSpecs() {
        List<ProtobufTranslationSpec<?, ?>> list = new ArrayList<>();

        list.add(new PersonPropertiesPluginDataTranslationSpec());
        list.add(new PersonPropertyDimensionTranslationSpec());
        list.add(new PersonPropertyFilterTranslationSpec());
        list.add(new PersonPropertyIdTranslationSpec());
        list.add(new PersonPropertyInteractionReportPluginDataTranslationSpec());
        list.add(new PersonPropertyReportPluginDataTranslationSpec());
        list.add(new TestPersonPropertyIdTranslationSpec());

        return list;
    }

    /**
     * Returns a Translator Builder that already includes the necessary
     * TranslationSpecs needed to read and write the PersonPropertiesPlugin
     */
    private static Translator.Builder builder() {
        Translator.Builder builder = Translator.builder()
                .setTranslatorId(PersonPropertiesTranslatorId.TRANSLATOR_ID)
                .addDependency(PropertiesTranslatorId.TRANSLATOR_ID)
                .addDependency(PeopleTranslatorId.TRANSLATOR_ID)
                .addDependency(ReportsTranslatorId.TRANSLATOR_ID)
                .setInitializer((translatorContext) -> {
                    IProtobufTaskitEngineBuilder taskitEngineBuilder = translatorContext
                            .getTaskitEngineBuilder(IProtobufTaskitEngineBuilder.class);

                    for (ProtobufTranslationSpec<?, ?> translationSpec : getTranslationSpecs()) {
                        taskitEngineBuilder.addTranslationSpec(translationSpec);
                    }

                    if (taskitEngineBuilder instanceof ProtobufJsonTaskitEngine.Builder) {
                        ((ProtobufJsonTaskitEngine.Builder) taskitEngineBuilder).addFieldToIncludeDefaultValue(
                                PersonPropertyValueInput.getDescriptor().findFieldByName("pId"));
                    }

                });

        return builder;
    }

    /**
     * Returns a Translator that includes TranslationSpecs for the
     * PersonPropertiesPlugin.
     */
    public static Translator getTranslator() {
        return builder().build();
    }
}
