package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translationSpecs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.ReportLabel;
import gov.hhs.aspr.ms.gcm.simulation.plugins.resources.reports.ResourcePropertyReportPluginData;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.support.input.ReportLabelInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.reports.input.ResourcePropertyReportPluginDataInput;
import gov.hhs.aspr.ms.taskit.core.CoreTranslationError;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationSpec;
import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain ResourcePropertyReportPluginDataInput} and
 * {@linkplain ResourcePropertyReportPluginData}
 */
public class ResourcePropertyReportPluginDataTranslationSpec
        extends ProtobufTranslationSpec<ResourcePropertyReportPluginDataInput, ResourcePropertyReportPluginData> {

    @Override
    protected ResourcePropertyReportPluginData convertInputObject(ResourcePropertyReportPluginDataInput inputObject) {
        if (!ResourcePropertyReportPluginData.checkVersionSupported(inputObject.getVersion())) {
            throw new ContractException(CoreTranslationError.UNSUPPORTED_VERSION);
        }

        ResourcePropertyReportPluginData.Builder builder = ResourcePropertyReportPluginData.builder();

        ReportLabel reportLabel = this.translationEngine.convertObject(inputObject.getReportLabel());

        builder.setReportLabel(reportLabel);
        return builder.build();
    }

    @Override
    protected ResourcePropertyReportPluginDataInput convertAppObject(ResourcePropertyReportPluginData appObject) {
        ResourcePropertyReportPluginDataInput.Builder builder = ResourcePropertyReportPluginDataInput.newBuilder();

        builder.setVersion(appObject.getVersion());

        ReportLabelInput reportLabelInput = this.translationEngine.convertObjectAsSafeClass(appObject.getReportLabel(),
                ReportLabel.class);

        builder.setReportLabel(reportLabelInput);
        return builder.build();
    }

    @Override
    public Class<ResourcePropertyReportPluginData> getAppObjectClass() {
        return ResourcePropertyReportPluginData.class;
    }

    @Override
    public Class<ResourcePropertyReportPluginDataInput> getInputObjectClass() {
        return ResourcePropertyReportPluginDataInput.class;
    }

}
