package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translation.specs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.Labeler;
import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.Partition;
import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.filters.Filter;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.support.filters.input.FilterInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.support.input.LabelerInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.support.input.PartitionInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain PartitionInput} and {@linkplain Partition}
 */
public class PartitionTranslationSpec extends ProtobufTranslationSpec<PartitionInput, Partition> {

    @Override
    protected Partition translateInputObject(PartitionInput inputObject) {
        Partition.Builder builder = Partition.builder();

        builder.setRetainPersonKeys(inputObject.getRetainPersonKeys());

        if (inputObject.hasFilter()) {
            Filter filter = this.taskitEngine.translateObject(inputObject.getFilter());

            builder.setFilter(filter);
        }

        for (LabelerInput labelerInput : inputObject.getLabelersList()) {
            Labeler labeler = this.taskitEngine.translateObject(labelerInput);

            builder.addLabeler(labeler);
        }

        return builder.build();
    }

    @Override
    protected PartitionInput translateAppObject(Partition appObject) {
        PartitionInput.Builder builder = PartitionInput.newBuilder();

        builder.setRetainPersonKeys(appObject.retainPersonKeys());

        if (appObject.getFilter().isPresent()) {
            FilterInput filterInput = this.taskitEngine.translateObjectAsClassSafe(appObject.getFilter().get(),
                    Filter.class);

            builder.setFilter(filterInput);
        }

        for (Labeler labeler : appObject.getLabelers()) {
            LabelerInput labelerInput = this.taskitEngine.translateObjectAsClassSafe(labeler, Labeler.class);

            builder.addLabelers(labelerInput);
        }

        return builder.build();
    }

    @Override
    public Class<Partition> getAppObjectClass() {
        return Partition.class;
    }

    @Override
    public Class<PartitionInput> getInputObjectClass() {
        return PartitionInput.class;
    }

}
