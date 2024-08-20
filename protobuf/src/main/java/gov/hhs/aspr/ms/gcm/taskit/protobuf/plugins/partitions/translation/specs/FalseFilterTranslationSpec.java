package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translation.specs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.filters.FalseFilter;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.support.filters.input.FalseFilterInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain FalseFilterInput} and {@linkplain FalseFilter}
 */
public class FalseFilterTranslationSpec extends ProtobufTranslationSpec<FalseFilterInput, FalseFilter> {

    @Override
    protected FalseFilter translateInputObject(FalseFilterInput inputObject) {
        return new FalseFilter();
    }

    @Override
    protected FalseFilterInput translateAppObject(FalseFilter appObject) {
        return FalseFilterInput.newBuilder().build();
    }

    @Override
    public Class<FalseFilter> getAppObjectClass() {
        return FalseFilter.class;
    }

    @Override
    public Class<FalseFilterInput> getInputObjectClass() {
        return FalseFilterInput.class;
    }

}
