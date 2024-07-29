package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.translationSpecs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.personproperties.reports.PersonPropertyReportPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.personproperties.support.PersonPropertyId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.ReportLabel;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.ReportPeriod;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.reports.input.PersonPropertyReportPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.support.input.PersonPropertyIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.support.input.ReportLabelInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.support.input.ReportPeriodInput;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitError;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;
import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain PersonPropertyReportPluginDataInput} and
 * {@linkplain PersonPropertyReportPluginData}
 */
public class PersonPropertyReportPluginDataTranslationSpec
        extends ProtobufTranslationSpec<PersonPropertyReportPluginDataInput, PersonPropertyReportPluginData> {

    @Override
    protected PersonPropertyReportPluginData translateInputObject(PersonPropertyReportPluginDataInput inputObject) {
        if (!PersonPropertyReportPluginData.checkVersionSupported(inputObject.getVersion())) {
            throw new ContractException(TaskitError.UNSUPPORTED_VERSION);
        }

        PersonPropertyReportPluginData.Builder builder = PersonPropertyReportPluginData.builder();

        ReportLabel reportLabel = this.taskitEngine.translateObject(inputObject.getReportLabel());
        builder.setReportLabel(reportLabel);

        ReportPeriod reportPeriod = this.taskitEngine.translateObject(inputObject.getReportPeriod());
        builder.setReportPeriod(reportPeriod);

        builder.setDefaultInclusion(inputObject.getDefaultInclusionPolicy());

        for (PersonPropertyIdInput personPropertyIdInput : inputObject.getIncludedPropertiesList()) {
            PersonPropertyId personPropertyId = this.taskitEngine.translateObject(personPropertyIdInput);
            builder.includePersonProperty(personPropertyId);
        }

        for (PersonPropertyIdInput personPropertyIdInput : inputObject.getExcludedPropertiesList()) {
            PersonPropertyId personPropertyId = this.taskitEngine.translateObject(personPropertyIdInput);
            builder.excludePersonProperty(personPropertyId);
        }

        return builder.build();
    }

    @Override
    protected PersonPropertyReportPluginDataInput translateAppObject(PersonPropertyReportPluginData appObject) {
        PersonPropertyReportPluginDataInput.Builder builder = PersonPropertyReportPluginDataInput.newBuilder();

        builder.setVersion(appObject.getVersion());

        ReportLabelInput reportLabelInput = this.taskitEngine.translateObjectAsClassSafe(appObject.getReportLabel(),
                ReportLabel.class);
        ReportPeriodInput reportPeriodInput = this.taskitEngine.translateObject(appObject.getReportPeriod());

        builder.setDefaultInclusionPolicy(appObject.getDefaultInclusionPolicy())
                .setReportLabel(reportLabelInput)
                .setReportPeriod(reportPeriodInput);

        for (PersonPropertyId personPropertyId : appObject.getIncludedProperties()) {
            PersonPropertyIdInput personPropertyIdInput = this.taskitEngine
                    .translateObjectAsClassSafe(personPropertyId, PersonPropertyId.class);
            builder.addIncludedProperties(personPropertyIdInput);
        }

        for (PersonPropertyId personPropertyId : appObject.getExcludedProperties()) {
            PersonPropertyIdInput personPropertyIdInput = this.taskitEngine
                    .translateObjectAsClassSafe(personPropertyId, PersonPropertyId.class);
            builder.addExcludedProperties(personPropertyIdInput);
        }

        return builder.build();
    }

    @Override
    public Class<PersonPropertyReportPluginData> getAppObjectClass() {
        return PersonPropertyReportPluginData.class;
    }

    @Override
    public Class<PersonPropertyReportPluginDataInput> getInputObjectClass() {
        return PersonPropertyReportPluginDataInput.class;
    }

}
