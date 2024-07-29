package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials;

import java.util.ArrayList;
import java.util.List;

import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.support.input.BatchIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.support.input.StageIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translationSpecs.BatchIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translationSpecs.BatchPropertyIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translationSpecs.BatchStatusReportPluginDataTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translationSpecs.MaterialIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translationSpecs.MaterialsPluginDataTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translationSpecs.MaterialsProducerIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translationSpecs.MaterialsProducerPropertyIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translationSpecs.MaterialsProducerPropertyReportPluginDataTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translationSpecs.MaterialsProducerResourceReportPluginDataTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translationSpecs.StageIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translationSpecs.StageReportPluginDataTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translationSpecs.TestBatchPropertyIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translationSpecs.TestMaterialIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translationSpecs.TestMaterialsProducerIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translationSpecs.TestMaterialsProducerPropertyIdTranslationSpec;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.PropertiesTranslatorId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.RegionsTranslatorId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslatorId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.ResourcesTranslatorId;
import gov.hhs.aspr.ms.taskit.core.translation.Translator;
import gov.hhs.aspr.ms.taskit.protobuf.engine.IProtobufTaskitEngineBuilder;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * Translator for the Materials Plugin. Using this Translator will add all the
 * necessary TanslationSpecs needed to read and write the MaterialsPlugin
 */
public class MaterialsTranslator {

    private MaterialsTranslator() {
    }

    protected static List<ProtobufTranslationSpec<?, ?>> getTranslationSpecs() {
        List<ProtobufTranslationSpec<?, ?>> list = new ArrayList<>();

        list.add(new MaterialsPluginDataTranslationSpec());
        list.add(new MaterialIdTranslationSpec());
        list.add(new MaterialsProducerIdTranslationSpec());
        list.add(new MaterialsProducerPropertyIdTranslationSpec());
        list.add(new BatchIdTranslationSpec());
        list.add(new StageIdTranslationSpec());
        list.add(new BatchPropertyIdTranslationSpec());
        list.add(new TestBatchPropertyIdTranslationSpec());
        list.add(new TestMaterialIdTranslationSpec());
        list.add(new TestMaterialsProducerIdTranslationSpec());
        list.add(new TestMaterialsProducerPropertyIdTranslationSpec());
        list.add(new BatchStatusReportPluginDataTranslationSpec());
        list.add(new MaterialsProducerPropertyReportPluginDataTranslationSpec());
        list.add(new MaterialsProducerResourceReportPluginDataTranslationSpec());
        list.add(new StageReportPluginDataTranslationSpec());

        return list;
    }

    /**
     * Returns a Translator Builder that already includes the necessary
     * TranslationSpecs needed to read and write the MaterialsPlugin
     */
    private static Translator.Builder builder() {
        Translator.Builder builder = Translator.builder()
                .setTranslatorId(MaterialsTranslatorId.TRANSLATOR_ID)
                .addDependency(PropertiesTranslatorId.TRANSLATOR_ID)
                .addDependency(ResourcesTranslatorId.TRANSLATOR_ID)
                .addDependency(RegionsTranslatorId.TRANSLATOR_ID)
                .addDependency(ReportsTranslatorId.TRANSLATOR_ID)
                .setInitializer((translatorContext) -> {
                    IProtobufTaskitEngineBuilder taskitEngineBuilder = translatorContext
                            .getTaskitEngineBuilder(IProtobufTaskitEngineBuilder.class);

                    for (ProtobufTranslationSpec<?, ?> translationSpec : getTranslationSpecs()) {
                        taskitEngineBuilder.addTranslationSpec(translationSpec);
                    }

                    if (taskitEngineBuilder instanceof ProtobufJsonTaskitEngine.Builder) {
                        ((ProtobufJsonTaskitEngine.Builder) taskitEngineBuilder)
                                .addFieldToIncludeDefaultValue(BatchIdInput.getDescriptor().findFieldByName("id"))
                                .addFieldToIncludeDefaultValue(StageIdInput.getDescriptor().findFieldByName("id"));
                    }

                });

        return builder;
    }

    /**
     * Returns a Translator that includes TranslationSpecs for the MaterialsPlugin.
     */
    public static Translator getTranslator() {
        return builder().build();
    }
}
