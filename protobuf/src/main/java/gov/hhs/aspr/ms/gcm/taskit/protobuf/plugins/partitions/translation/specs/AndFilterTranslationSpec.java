package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translation.specs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.filters.AndFilter;
import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.filters.Filter;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.support.filters.input.AndFilterInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.support.filters.input.FilterInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain AndFilterInput} and {@linkplain AndFilter}
 */
public class AndFilterTranslationSpec extends ProtobufTranslationSpec<AndFilterInput, AndFilter> {

    @Override
    protected AndFilter translateInputObject(AndFilterInput inputObject) {
        return new AndFilter(this.taskitEngine.translateObject(inputObject.getA()),
                this.taskitEngine.translateObject(inputObject.getB()));
    }

    @Override
    protected AndFilterInput translateAppObject(AndFilter appObject) {
        FilterInput a = this.taskitEngine.translateObjectAsClassSafe(appObject.getFirstFilter(), Filter.class);
        FilterInput b = this.taskitEngine.translateObjectAsClassSafe(appObject.getSecondFilter(), Filter.class);
        return AndFilterInput.newBuilder().setA(a).setB(b).build();
    }

    @Override
    public Class<AndFilter> getAppObjectClass() {
        return AndFilter.class;
    }

    @Override
    public Class<AndFilterInput> getInputObjectClass() {
        return AndFilterInput.class;
    }

}
