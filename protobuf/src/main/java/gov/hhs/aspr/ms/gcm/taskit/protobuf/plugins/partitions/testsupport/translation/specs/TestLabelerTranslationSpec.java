package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.translation.specs;

import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.TestLabeler;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.testsupport.input.TestLabelerInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain TestLabelerInput} and {@linkplain TestLabeler}
 */
public class TestLabelerTranslationSpec extends ProtobufTranslationSpec<TestLabelerInput, TestLabeler> {

    @Override
    protected TestLabeler translateInputObject(TestLabelerInput inputObject) {
        return new TestLabeler(inputObject.getId());
    }

    @Override
    protected TestLabelerInput translateAppObject(TestLabeler appObject) {
        return TestLabelerInput.newBuilder().setId(appObject.getId().toString()).build();
    }

    @Override
    public Class<TestLabeler> getAppObjectClass() {
        return TestLabeler.class;
    }

    @Override
    public Class<TestLabelerInput> getInputObjectClass() {
        return TestLabelerInput.class;
    }

}
