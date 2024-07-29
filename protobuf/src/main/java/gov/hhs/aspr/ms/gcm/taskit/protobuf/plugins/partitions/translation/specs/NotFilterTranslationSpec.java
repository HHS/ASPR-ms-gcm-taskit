package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translation.specs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.filters.Filter;
import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.filters.NotFilter;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.support.filters.input.FilterInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.support.filters.input.NotFilterInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain NotFilterInput} and {@linkplain NotFilter}
 */
public class NotFilterTranslationSpec extends ProtobufTranslationSpec<NotFilterInput, NotFilter> {

    @Override
    protected NotFilter translateInputObject(NotFilterInput inputObject) {
        return new NotFilter(this.taskitEngine.translateObject(inputObject.getA()));
    }

    @Override
    protected NotFilterInput translateAppObject(NotFilter appObject) {
        FilterInput a = this.taskitEngine.translateObjectAsClassSafe(appObject.getSubFilter(), Filter.class);
        return NotFilterInput.newBuilder().setA(a).build();
    }

    @Override
    public Class<NotFilter> getAppObjectClass() {
        return NotFilter.class;
    }

    @Override
    public Class<NotFilterInput> getInputObjectClass() {
        return NotFilterInput.class;
    }

}
