package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.translation.specs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.SimpleReportLabel;
import gov.hhs.aspr.ms.gcm.simulation.plugins.stochastics.support.SimpleRandomNumberGeneratorId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.support.input.SimpleReportLabelInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.support.input.SimpleRandomNumberGeneratorIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain SimpleReportLabelInput} and {@linkplain SimpleReportLabel}
 */
public class SimpleRandomNumberGeneratorIdTranslationSpec
        extends ProtobufTranslationSpec<SimpleRandomNumberGeneratorIdInput, SimpleRandomNumberGeneratorId> {

    @Override
    protected SimpleRandomNumberGeneratorId translateInputObject(SimpleRandomNumberGeneratorIdInput inputObject) {
        return new SimpleRandomNumberGeneratorId(this.taskitEngine.getObjectFromAny(inputObject.getValue()));
    }

    @Override
    protected SimpleRandomNumberGeneratorIdInput translateAppObject(SimpleRandomNumberGeneratorId appObject) {
        return SimpleRandomNumberGeneratorIdInput.newBuilder()
                .setValue(this.taskitEngine.getAnyFromObject(appObject.getValue()))
                .build();
    }

    @Override
    public Class<SimpleRandomNumberGeneratorId> getAppObjectClass() {
        return SimpleRandomNumberGeneratorId.class;
    }

    @Override
    public Class<SimpleRandomNumberGeneratorIdInput> getInputObjectClass() {
        return SimpleRandomNumberGeneratorIdInput.class;
    }

}
