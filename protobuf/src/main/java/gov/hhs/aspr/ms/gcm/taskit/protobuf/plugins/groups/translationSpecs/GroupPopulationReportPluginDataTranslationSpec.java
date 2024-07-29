package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translationSpecs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.reports.GroupPopulationReportPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.ReportLabel;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.reports.input.GroupPopulationReportPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.support.input.ReportLabelInput;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitError;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;
import gov.hhs.aspr.ms.util.errors.ContractException;

public class GroupPopulationReportPluginDataTranslationSpec
        extends ProtobufTranslationSpec<GroupPopulationReportPluginDataInput, GroupPopulationReportPluginData> {

    @Override
    protected GroupPopulationReportPluginData translateInputObject(GroupPopulationReportPluginDataInput inputObject) {
        if (!GroupPopulationReportPluginData.checkVersionSupported(inputObject.getVersion())) {
            throw new ContractException(TaskitError.UNSUPPORTED_VERSION);
        }

        return GroupPopulationReportPluginData.builder()
                .setReportLabel(this.taskitEngine.translateObject(inputObject.getReportLabel()))
                .setReportPeriod(this.taskitEngine.translateObject(inputObject.getReportPeriod()))
                .build();
    }

    @Override
    protected GroupPopulationReportPluginDataInput translateAppObject(GroupPopulationReportPluginData appObject) {
        return GroupPopulationReportPluginDataInput.newBuilder()
                .setReportLabel((ReportLabelInput) this.taskitEngine
                        .translateObjectAsClassSafe(appObject.getReportLabel(), ReportLabel.class))
                .setReportPeriod(this.taskitEngine.translateObject(appObject.getReportPeriod()))
                .setVersion(appObject.getVersion())
                .build();
    }

    @Override
    public Class<GroupPopulationReportPluginData> getAppObjectClass() {
        return GroupPopulationReportPluginData.class;
    }

    @Override
    public Class<GroupPopulationReportPluginDataInput> getInputObjectClass() {
        return GroupPopulationReportPluginDataInput.class;
    }

}
