package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.translationSpecs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.personproperties.reports.PersonPropertyInteractionReportPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.personproperties.support.PersonPropertyId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.ReportLabel;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.ReportPeriod;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.reports.input.PersonPropertyInteractionReportPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.support.input.PersonPropertyIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.support.input.ReportLabelInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.support.input.ReportPeriodInput;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitError;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;
import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain PersonPropertyInteractionReportPluginDataInput} and
 * {@linkplain PersonPropertyInteractionReportPluginData}
 */
public class PersonPropertyInteractionReportPluginDataTranslationSpec extends
        ProtobufTranslationSpec<PersonPropertyInteractionReportPluginDataInput, PersonPropertyInteractionReportPluginData> {

    @Override
    protected PersonPropertyInteractionReportPluginData translateInputObject(
            PersonPropertyInteractionReportPluginDataInput inputObject) {
        if (!PersonPropertyInteractionReportPluginData.checkVersionSupported(inputObject.getVersion())) {
            throw new ContractException(TaskitError.UNSUPPORTED_VERSION);
        }

        PersonPropertyInteractionReportPluginData.Builder builder = PersonPropertyInteractionReportPluginData.builder();

        ReportLabel reportLabel = this.taskitEngine.translateObject(inputObject.getReportLabel());
        builder.setReportLabel(reportLabel);

        ReportPeriod reportPeriod = this.taskitEngine.translateObject(inputObject.getReportPeriod());
        builder.setReportPeriod(reportPeriod);

        for (PersonPropertyIdInput personPropertyIdInput : inputObject.getPersonPropertyIdsList()) {
            PersonPropertyId personPropertyId = this.taskitEngine.translateObject(personPropertyIdInput);
            builder.addPersonPropertyId(personPropertyId);
        }

        return builder.build();
    }

    @Override
    protected PersonPropertyInteractionReportPluginDataInput translateAppObject(
            PersonPropertyInteractionReportPluginData appObject) {
        PersonPropertyInteractionReportPluginDataInput.Builder builder = PersonPropertyInteractionReportPluginDataInput
                .newBuilder();

        builder.setVersion(appObject.getVersion());

        ReportLabelInput reportLabelInput = this.taskitEngine.translateObjectAsClassSafe(appObject.getReportLabel(),
                ReportLabel.class);
        ReportPeriodInput reportPeriodInput = this.taskitEngine.translateObject(appObject.getReportPeriod());

        builder.setReportLabel(reportLabelInput).setReportPeriod(reportPeriodInput);

        for (PersonPropertyId personPropertyId : appObject.getPersonPropertyIds()) {
            PersonPropertyIdInput personPropertyIdInput = this.taskitEngine
                    .translateObjectAsClassSafe(personPropertyId, PersonPropertyId.class);
            builder.addPersonPropertyIds(personPropertyIdInput);
        }

        return builder.build();
    }

    @Override
    public Class<PersonPropertyInteractionReportPluginData> getAppObjectClass() {
        return PersonPropertyInteractionReportPluginData.class;
    }

    @Override
    public Class<PersonPropertyInteractionReportPluginDataInput> getInputObjectClass() {
        return PersonPropertyInteractionReportPluginDataInput.class;
    }

}
