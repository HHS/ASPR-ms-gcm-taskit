package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translation.specs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.Labeler;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.support.input.LabelerInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between {@linkplain LabelerInput}
 * and {@linkplain Labeler}
 */
public class LabelerTranslationSpec extends ProtobufTranslationSpec<LabelerInput, Labeler> {

    @Override
    protected Labeler translateInputObject(LabelerInput inputObject) {
        return this.taskitEngine.getObjectFromAny(inputObject.getLabeler());
    }

    @Override
    protected LabelerInput translateAppObject(Labeler appObject) {
        return LabelerInput.newBuilder().setLabeler(this.taskitEngine.getAnyFromObject(appObject)).build();
    }

    @Override
    public Class<Labeler> getAppObjectClass() {
        return Labeler.class;
    }

    @Override
    public Class<LabelerInput> getInputObjectClass() {
        return LabelerInput.class;
    }

}
