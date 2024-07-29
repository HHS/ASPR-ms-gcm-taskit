package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.translationSpecs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.ReportLabel;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.support.input.ReportLabelInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain ReportLabelInput} and {@linkplain ReportLabel}
 */
public class ReportLabelTranslationSpec extends ProtobufTranslationSpec<ReportLabelInput, ReportLabel> {

    @Override
    protected ReportLabel translateInputObject(ReportLabelInput inputObject) {
        return this.taskitEngine.getObjectFromAny(inputObject.getLabel());
    }

    @Override
    protected ReportLabelInput translateAppObject(ReportLabel appObject) {
        return ReportLabelInput.newBuilder().setLabel(this.taskitEngine.getAnyFromObject(appObject)).build();
    }

    @Override
    public Class<ReportLabel> getAppObjectClass() {
        return ReportLabel.class;
    }

    @Override
    public Class<ReportLabelInput> getInputObjectClass() {
        return ReportLabelInput.class;
    }

}
