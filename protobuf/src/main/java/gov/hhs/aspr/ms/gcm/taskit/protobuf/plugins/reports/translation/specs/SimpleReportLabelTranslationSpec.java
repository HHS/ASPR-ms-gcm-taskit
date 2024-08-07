package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.translation.specs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.SimpleReportLabel;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.support.input.SimpleReportLabelInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain SimpleReportLabelInput} and {@linkplain SimpleReportLabel}
 */
public class SimpleReportLabelTranslationSpec
        extends ProtobufTranslationSpec<SimpleReportLabelInput, SimpleReportLabel> {

    @Override
    protected SimpleReportLabel translateInputObject(SimpleReportLabelInput inputObject) {
        return new SimpleReportLabel(this.taskitEngine.getObjectFromAny(inputObject.getValue()));
    }

    @Override
    protected SimpleReportLabelInput translateAppObject(SimpleReportLabel appObject) {
        return SimpleReportLabelInput.newBuilder()
                .setValue(this.taskitEngine.getAnyFromObject(appObject.getValue()))
                .build();
    }

    @Override
    public Class<SimpleReportLabel> getAppObjectClass() {
        return SimpleReportLabel.class;
    }

    @Override
    public Class<SimpleReportLabelInput> getInputObjectClass() {
        return SimpleReportLabelInput.class;
    }

}
