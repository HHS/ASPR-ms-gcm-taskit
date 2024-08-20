package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translation.specs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.filters.TrueFilter;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.support.filters.input.TrueFilterInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain TrueFilterInput} and {@linkplain TrueFilter}
 */
public class TrueFilterTranslationSpec extends ProtobufTranslationSpec<TrueFilterInput, TrueFilter> {

    @Override
    protected TrueFilter translateInputObject(TrueFilterInput inputObject) {
        return new TrueFilter();
    }

    @Override
    protected TrueFilterInput translateAppObject(TrueFilter appObject) {
        return TrueFilterInput.newBuilder().build();
    }

    @Override
    public Class<TrueFilter> getAppObjectClass() {
        return TrueFilter.class;
    }

    @Override
    public Class<TrueFilterInput> getInputObjectClass() {
        return TrueFilterInput.class;
    }

}
