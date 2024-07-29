package gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.translationSpecs;

import gov.hhs.aspr.ms.gcm.simulation.nucleus.Dimension;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.input.DimensionInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

public class DimensionTranslationSpec extends ProtobufTranslationSpec<DimensionInput, Dimension> {

    @Override
    protected Dimension translateInputObject(DimensionInput inputObject) {
        return this.taskitEngine.getObjectFromAny(inputObject.getDimension());
    }

    @Override
    protected DimensionInput translateAppObject(Dimension appObject) {
        return DimensionInput.newBuilder().setDimension(this.taskitEngine.getAnyFromObject(appObject)).build();
    }

    @Override
    public Class<Dimension> getAppObjectClass() {
        return Dimension.class;
    }

    @Override
    public Class<DimensionInput> getInputObjectClass() {
        return DimensionInput.class;
    }

}
