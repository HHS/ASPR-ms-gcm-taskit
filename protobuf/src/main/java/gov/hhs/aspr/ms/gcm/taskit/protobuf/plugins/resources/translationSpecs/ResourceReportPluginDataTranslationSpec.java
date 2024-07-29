package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translationSpecs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.ReportLabel;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.ReportPeriod;
import gov.hhs.aspr.ms.gcm.simulation.plugins.resources.reports.ResourceReportPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.resources.support.ResourceId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.support.input.ReportLabelInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.support.input.ReportPeriodInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.reports.input.ResourceReportPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.support.input.ResourceIdInput;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitError;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;
import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain ResourceReportPluginDataInput} and
 * {@linkplain ResourceReportPluginData}
 */
public class ResourceReportPluginDataTranslationSpec
        extends ProtobufTranslationSpec<ResourceReportPluginDataInput, ResourceReportPluginData> {

    @Override
    protected ResourceReportPluginData translateInputObject(ResourceReportPluginDataInput inputObject) {
        if (!ResourceReportPluginData.checkVersionSupported(inputObject.getVersion())) {
            throw new ContractException(TaskitError.UNSUPPORTED_VERSION);
        }

        ResourceReportPluginData.Builder builder = ResourceReportPluginData.builder();

        ReportLabel reportLabel = this.taskitEngine.translateObject(inputObject.getReportLabel());
        ReportPeriod reportPeriod = this.taskitEngine.translateObject(inputObject.getReportPeriod());

        builder.setReportLabel(reportLabel)
                .setReportPeriod(reportPeriod)
                .setDefaultInclusion(inputObject.getDefaultInclusionPolicy());

        for (ResourceIdInput resourceIdInput : inputObject.getIncludedPropertiesList()) {
            ResourceId resourceId = this.taskitEngine.translateObject(resourceIdInput);
            builder.includeResource(resourceId);
        }

        for (ResourceIdInput resourceIdInput : inputObject.getExcludedPropertiesList()) {
            ResourceId resourceId = this.taskitEngine.translateObject(resourceIdInput);
            builder.excludeResource(resourceId);
        }

        return builder.build();
    }

    @Override
    protected ResourceReportPluginDataInput translateAppObject(ResourceReportPluginData appObject) {
        ResourceReportPluginDataInput.Builder builder = ResourceReportPluginDataInput.newBuilder();

        builder.setVersion(appObject.getVersion());

        ReportLabelInput reportLabelInput = this.taskitEngine.translateObjectAsClassSafe(appObject.getReportLabel(),
                ReportLabel.class);
        ReportPeriodInput reportPeriodInput = this.taskitEngine.translateObject(appObject.getReportPeriod());

        builder.setDefaultInclusionPolicy(appObject.getDefaultInclusionPolicy())
                .setReportPeriod(reportPeriodInput)
                .setReportLabel(reportLabelInput);

        for (ResourceId resourceId : appObject.getIncludedResourceIds()) {
            ResourceIdInput resourceIdInput = this.taskitEngine.translateObjectAsClassSafe(resourceId,
                    ResourceId.class);
            builder.addIncludedProperties(resourceIdInput);
        }

        for (ResourceId resourceId : appObject.getExcludedResourceIds()) {
            ResourceIdInput resourceIdInput = this.taskitEngine.translateObjectAsClassSafe(resourceId,
                    ResourceId.class);
            builder.addExcludedProperties(resourceIdInput);
        }

        return builder.build();
    }

    @Override
    public Class<ResourceReportPluginData> getAppObjectClass() {
        return ResourceReportPluginData.class;
    }

    @Override
    public Class<ResourceReportPluginDataInput> getInputObjectClass() {
        return ResourceReportPluginDataInput.class;
    }

}
