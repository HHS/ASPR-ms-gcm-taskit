package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.translationSpecs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.globalproperties.reports.GlobalPropertyReportPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.globalproperties.support.GlobalPropertyId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.ReportLabel;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.reports.input.GlobalPropertyReportPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.support.input.GlobalPropertyIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.support.input.ReportLabelInput;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitError;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;
import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain GlobalPropertyReportPluginDataInput} and
 * {@linkplain GlobalPropertyReportPluginData}
 */
public class GlobalPropertyReportPluginDataTranslationSpec
        extends ProtobufTranslationSpec<GlobalPropertyReportPluginDataInput, GlobalPropertyReportPluginData> {

    @Override
    protected GlobalPropertyReportPluginData translateInputObject(GlobalPropertyReportPluginDataInput inputObject) {
        if (!GlobalPropertyReportPluginData.checkVersionSupported(inputObject.getVersion())) {
            throw new ContractException(TaskitError.UNSUPPORTED_VERSION);
        }

        GlobalPropertyReportPluginData.Builder builder = GlobalPropertyReportPluginData.builder();

        ReportLabel reportLabel = this.taskitEngine.translateObject(inputObject.getReportLabel());
        builder.setReportLabel(reportLabel);

        builder.setDefaultInclusion(inputObject.getDefaultInclusionPolicy());

        for (GlobalPropertyIdInput globalPropertyIdInput : inputObject.getIncludedPropertiesList()) {
            GlobalPropertyId globalPropertyId = this.taskitEngine.translateObject(globalPropertyIdInput);
            builder.includeGlobalProperty(globalPropertyId);
        }

        for (GlobalPropertyIdInput globalPropertyIdInput : inputObject.getExcludedPropertiesList()) {
            GlobalPropertyId globalPropertyId = this.taskitEngine.translateObject(globalPropertyIdInput);
            builder.excludeGlobalProperty(globalPropertyId);
        }

        return builder.build();
    }

    @Override
    protected GlobalPropertyReportPluginDataInput translateAppObject(GlobalPropertyReportPluginData appObject) {
        GlobalPropertyReportPluginDataInput.Builder builder = GlobalPropertyReportPluginDataInput.newBuilder();

        builder.setVersion(appObject.getVersion());

        ReportLabelInput reportLabelInput = this.taskitEngine.translateObjectAsClassSafe(appObject.getReportLabel(),
                ReportLabel.class);

        builder.setDefaultInclusionPolicy(appObject.getDefaultInclusionPolicy()).setReportLabel(reportLabelInput);

        for (GlobalPropertyId globalPropertyId : appObject.getIncludedProperties()) {
            GlobalPropertyIdInput globalPropertyIdInput = this.taskitEngine
                    .translateObjectAsClassSafe(globalPropertyId, GlobalPropertyId.class);
            builder.addIncludedProperties(globalPropertyIdInput);
        }

        for (GlobalPropertyId globalPropertyId : appObject.getExcludedProperties()) {
            GlobalPropertyIdInput globalPropertyIdInput = this.taskitEngine
                    .translateObjectAsClassSafe(globalPropertyId, GlobalPropertyId.class);
            builder.addExcludedProperties(globalPropertyIdInput);
        }

        return builder.build();
    }

    @Override
    public Class<GlobalPropertyReportPluginData> getAppObjectClass() {
        return GlobalPropertyReportPluginData.class;
    }

    @Override
    public Class<GlobalPropertyReportPluginDataInput> getInputObjectClass() {
        return GlobalPropertyReportPluginDataInput.class;
    }

}
