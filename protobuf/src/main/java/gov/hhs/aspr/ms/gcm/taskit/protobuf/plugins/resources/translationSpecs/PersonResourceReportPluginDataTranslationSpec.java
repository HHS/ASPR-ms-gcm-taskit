package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translationSpecs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.ReportLabel;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.ReportPeriod;
import gov.hhs.aspr.ms.gcm.simulation.plugins.resources.reports.PersonResourceReportPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.resources.support.ResourceId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.support.input.ReportLabelInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.support.input.ReportPeriodInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.reports.input.PersonResourceReportPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.support.input.ResourceIdInput;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitError;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;
import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain PersonResourceReportPluginDataInput} and
 * {@linkplain PersonResourceReportPluginData}
 */
public class PersonResourceReportPluginDataTranslationSpec
        extends ProtobufTranslationSpec<PersonResourceReportPluginDataInput, PersonResourceReportPluginData> {

    @Override
    protected PersonResourceReportPluginData translateInputObject(PersonResourceReportPluginDataInput inputObject) {
        if (!PersonResourceReportPluginData.checkVersionSupported(inputObject.getVersion())) {
            throw new ContractException(TaskitError.UNSUPPORTED_VERSION);
        }

        PersonResourceReportPluginData.Builder builder = PersonResourceReportPluginData.builder();

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
    protected PersonResourceReportPluginDataInput translateAppObject(PersonResourceReportPluginData appObject) {
        PersonResourceReportPluginDataInput.Builder builder = PersonResourceReportPluginDataInput.newBuilder();

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
    public Class<PersonResourceReportPluginData> getAppObjectClass() {
        return PersonResourceReportPluginData.class;
    }

    @Override
    public Class<PersonResourceReportPluginDataInput> getInputObjectClass() {
        return PersonResourceReportPluginDataInput.class;
    }

}
