package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translation.specs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.filters.Filter;
import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.filters.OrFilter;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.support.filters.input.FilterInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.support.filters.input.OrFilterInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain OrFilterInput} and {@linkplain OrFilter}
 */
public class OrFilterTranslationSpec extends ProtobufTranslationSpec<OrFilterInput, OrFilter> {

    @Override
    protected OrFilter translateInputObject(OrFilterInput inputObject) {
        return new OrFilter(this.taskitEngine.translateObject(inputObject.getA()),
                this.taskitEngine.translateObject(inputObject.getB()));
    }

    @Override
    protected OrFilterInput translateAppObject(OrFilter appObject) {
        FilterInput a = this.taskitEngine.translateObjectAsClassSafe(appObject.getFirstFilter(), Filter.class);
        FilterInput b = this.taskitEngine.translateObjectAsClassSafe(appObject.getSecondFilter(), Filter.class);
        return OrFilterInput.newBuilder().setA(a).setB(b).build();
    }

    @Override
    public Class<OrFilter> getAppObjectClass() {
        return OrFilter.class;
    }

    @Override
    public Class<OrFilterInput> getInputObjectClass() {
        return OrFilterInput.class;
    }

}
