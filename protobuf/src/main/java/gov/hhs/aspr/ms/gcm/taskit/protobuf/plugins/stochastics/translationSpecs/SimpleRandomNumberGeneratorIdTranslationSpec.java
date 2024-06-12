package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.translationSpecs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.SimpleReportLabel;
import gov.hhs.aspr.ms.gcm.simulation.plugins.stochastics.support.SimpleRandomNumberGeneratorId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.support.input.SimpleReportLabelInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.support.input.SimpleRandomNumberGeneratorIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain SimpleReportLabelInput} and {@linkplain SimpleReportLabel}
 */
public class SimpleRandomNumberGeneratorIdTranslationSpec
        extends ProtobufTranslationSpec<SimpleRandomNumberGeneratorIdInput, SimpleRandomNumberGeneratorId> {

    @Override
    protected SimpleRandomNumberGeneratorId convertInputObject(SimpleRandomNumberGeneratorIdInput inputObject) {
        return new SimpleRandomNumberGeneratorId(this.translationEngine.getObjectFromAny(inputObject.getValue()));
    }

    @Override
    protected SimpleRandomNumberGeneratorIdInput convertAppObject(SimpleRandomNumberGeneratorId appObject) {
        return SimpleRandomNumberGeneratorIdInput.newBuilder()
                .setValue(this.translationEngine.getAnyFromObject(appObject.getValue()))
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
