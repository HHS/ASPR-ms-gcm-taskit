package gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.translation.specs;

import gov.hhs.aspr.ms.gcm.simulation.nucleus.Planner;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.input.PlannerInput;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;

/**
 * TranslationSpec that defines how to convert between {@linkplain PlannerInput}
 * and {@linkplain Planner}
 */
public class PlannerTranslationSpec extends ProtobufTranslationSpec<PlannerInput, Planner> {

    @Override
    protected Planner translateInputObject(PlannerInput inputObject) {
        return Planner.valueOf(inputObject.name());
    }

    @Override
    protected PlannerInput translateAppObject(Planner appObject) {
        return PlannerInput.valueOf(appObject.name());
    }

    @Override
    public Class<Planner> getAppObjectClass() {
        return Planner.class;
    }

    @Override
    public Class<PlannerInput> getInputObjectClass() {
        return PlannerInput.class;
    }

}
