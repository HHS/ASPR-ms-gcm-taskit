package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translation;

import java.util.ArrayList;
import java.util.List;

import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.support.input.GroupIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translation.specs.GroupIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translation.specs.GroupMemberFilterTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translation.specs.GroupPopulationReportPluginDataTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translation.specs.GroupPropertyDimensionTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translation.specs.GroupPropertyIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translation.specs.GroupPropertyReportPluginDataTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translation.specs.GroupTypeIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translation.specs.GroupTypesForPersonFilterTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translation.specs.GroupsForPersonAndGroupTypeFilterTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translation.specs.GroupsForPersonFilterTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translation.specs.GroupsPluginDataTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translation.specs.TestGroupPropertyIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translation.specs.TestGroupTypeIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslatorId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslatorId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslatorId;
import gov.hhs.aspr.ms.taskit.core.translation.Translator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.IProtobufTaskitEngineBuilder;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * Translator for the Groups Plugin. Using this Translator will add all the
 * necessary TanslationSpecs needed to read and write the GroupsPlugin.
 */
public class GroupsTranslator {

    private GroupsTranslator() {
    }

    protected static List<ProtobufTranslationSpec<?, ?>> getTranslationSpecs() {
        List<ProtobufTranslationSpec<?, ?>> list = new ArrayList<>();

        list.add(new GroupsPluginDataTranslationSpec());
        list.add(new GroupIdTranslationSpec());
        list.add(new GroupTypeIdTranslationSpec());
        list.add(new GroupPropertyIdTranslationSpec());
        list.add(new TestGroupTypeIdTranslationSpec());
        list.add(new GroupMemberFilterTranslationSpec());
        list.add(new GroupsForPersonAndGroupTypeFilterTranslationSpec());
        list.add(new GroupsForPersonFilterTranslationSpec());
        list.add(new GroupTypesForPersonFilterTranslationSpec());
        list.add(new GroupPropertyDimensionTranslationSpec());
        list.add(new TestGroupPropertyIdTranslationSpec());
        list.add(new GroupPropertyReportPluginDataTranslationSpec());
        list.add(new GroupPopulationReportPluginDataTranslationSpec());

        return list;
    }

    /**
     * Returns a Translator Builder that already includes the necessary
     * TranslationSpecs needed to read and write the GroupsPlugin
     */
    private static Translator.Builder builder() {
        Translator.Builder builder = Translator.builder()
                .setTranslatorId(GroupsTranslatorId.TRANSLATOR_ID)
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
                        ((ProtobufJsonTaskitEngine.Builder) taskitEngineBuilder)
                                .addFieldToIncludeDefaultValue(GroupIdInput.getDescriptor().findFieldByName("id"));
                    }
                });

        return builder;
    }

    /**
     * Returns a Translator that includes TranslationSpecs for the GroupsPlugin.
     */
    public static Translator getTranslator() {
        return builder().build();
    }
}
