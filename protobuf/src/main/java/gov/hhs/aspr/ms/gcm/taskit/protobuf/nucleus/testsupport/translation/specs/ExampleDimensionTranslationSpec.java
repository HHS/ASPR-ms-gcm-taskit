package gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.testsupport.translation.specs;

import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.testsupport.ExampleDimension;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.testsupport.input.ExampleDimensionInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

public class ExampleDimensionTranslationSpec extends ProtobufTranslationSpec<ExampleDimensionInput, ExampleDimension> {

    @Override
    protected ExampleDimension translateInputObject(ExampleDimensionInput inputObject) {
        return new ExampleDimension(inputObject.getLevelName());
    }

    @Override
    protected ExampleDimensionInput translateAppObject(ExampleDimension appObject) {
        return ExampleDimensionInput.newBuilder().setLevelName(appObject.getLevelName()).build();
    }

    @Override
    public Class<ExampleDimension> getAppObjectClass() {
        return ExampleDimension.class;
    }

    @Override
    public Class<ExampleDimensionInput> getInputObjectClass() {
        return ExampleDimensionInput.class;
    }

}
