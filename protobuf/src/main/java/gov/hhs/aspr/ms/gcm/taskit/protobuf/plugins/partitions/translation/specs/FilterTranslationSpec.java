package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translation.specs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.filters.Filter;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.support.filters.input.FilterInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between {@linkplain FilterInput}
 * and {@linkplain Filter}
 */
public class FilterTranslationSpec extends ProtobufTranslationSpec<FilterInput, Filter> {

    @Override
    protected Filter translateInputObject(FilterInput inputObject) {
        return this.taskitEngine.getObjectFromAny(inputObject.getFilter());
    }

    @Override
    protected FilterInput translateAppObject(Filter appObject) {
        return FilterInput.newBuilder().setFilter(this.taskitEngine.getAnyFromObject(appObject)).build();
    }

    @Override
    public Class<Filter> getAppObjectClass() {
        return Filter.class;
    }

    @Override
    public Class<FilterInput> getInputObjectClass() {
        return FilterInput.class;
    }

}
