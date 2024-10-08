package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.translation.specs;

import com.google.protobuf.Any;

import gov.hhs.aspr.ms.gcm.simulation.plugins.globalproperties.support.GlobalPropertyDimension;
import gov.hhs.aspr.ms.gcm.simulation.plugins.globalproperties.support.GlobalPropertyId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.support.input.GlobalPropertyDimensionInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.support.input.GlobalPropertyIdInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

public class GlobalPropertyDimensionTranslationSpec
        extends ProtobufTranslationSpec<GlobalPropertyDimensionInput, GlobalPropertyDimension> {

    @Override
    protected GlobalPropertyDimension translateInputObject(GlobalPropertyDimensionInput inputObject) {
        GlobalPropertyDimension.Builder builder = GlobalPropertyDimension.builder();

        GlobalPropertyId globalPropertyId = this.taskitEngine.translateObject(inputObject.getGlobalPropertyId());

        builder.setGlobalPropertyId(globalPropertyId).setAssignmentTime(inputObject.getAssignmentTime());

        for (Any anyValue : inputObject.getValuesList()) {
            Object value = this.taskitEngine.getObjectFromAny(anyValue);
            builder.addValue(value);
        }

        return builder.build();
    }

    @Override
    protected GlobalPropertyDimensionInput translateAppObject(GlobalPropertyDimension appObject) {
        GlobalPropertyDimensionInput.Builder builder = GlobalPropertyDimensionInput.newBuilder();

        GlobalPropertyIdInput globalPropertyIdInput = this.taskitEngine
                .translateObjectAsClassSafe(appObject.getGlobalPropertyId(), GlobalPropertyId.class);

        builder.setGlobalPropertyId(globalPropertyIdInput).setAssignmentTime(appObject.getAssignmentTime());

        for (Object objValue : appObject.getValues()) {
            builder.addValues(this.taskitEngine.getAnyFromObject(objValue));
        }

        return builder.build();
    }

    @Override
    public Class<GlobalPropertyDimension> getAppObjectClass() {
        return GlobalPropertyDimension.class;
    }

    @Override
    public Class<GlobalPropertyDimensionInput> getInputObjectClass() {
        return GlobalPropertyDimensionInput.class;
    }

}
