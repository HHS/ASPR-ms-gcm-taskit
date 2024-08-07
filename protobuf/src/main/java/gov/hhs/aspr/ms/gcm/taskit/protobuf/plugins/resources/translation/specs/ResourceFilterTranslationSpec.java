package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translation.specs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.support.Equality;
import gov.hhs.aspr.ms.gcm.simulation.plugins.resources.support.ResourceFilter;
import gov.hhs.aspr.ms.gcm.simulation.plugins.resources.support.ResourceId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.support.input.EqualityInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.support.input.ResourceFilterInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.support.input.ResourceIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

public class ResourceFilterTranslationSpec extends ProtobufTranslationSpec<ResourceFilterInput, ResourceFilter> {

    @Override
    protected ResourceFilter translateInputObject(ResourceFilterInput inputObject) {
        ResourceId resourceId = this.taskitEngine.translateObject(inputObject.getResourceId());
        Equality equality = this.taskitEngine.translateObject(inputObject.getEquality());
        long resourceValue = inputObject.getResourceValue();

        return new ResourceFilter(resourceId, equality, resourceValue);
    }

    @Override
    protected ResourceFilterInput translateAppObject(ResourceFilter appObject) {
        ResourceIdInput resourceId = this.taskitEngine.translateObjectAsClassSafe(appObject.getResourceId(),
                ResourceId.class);
        EqualityInput equality = this.taskitEngine.translateObjectAsClassSafe(appObject.getEquality(), Equality.class);
        long resourceValue = appObject.getResourceValue();

        return ResourceFilterInput.newBuilder()
                .setResourceId(resourceId)
                .setEquality(equality)
                .setResourceValue(resourceValue)
                .build();
    }

    @Override
    public Class<ResourceFilter> getAppObjectClass() {
        return ResourceFilter.class;
    }

    @Override
    public Class<ResourceFilterInput> getInputObjectClass() {
        return ResourceFilterInput.class;
    }

}
