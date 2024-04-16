package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translationSpecs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.reports.GroupPopulationReportPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.ReportLabel;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.reports.input.GroupPopulationReportPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.support.input.ReportLabelInput;
import gov.hhs.aspr.ms.taskit.core.CoreTranslationError;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationSpec;
import gov.hhs.aspr.ms.util.errors.ContractException;

public class GroupPopulationReportPluginDataTranslationSpec
        extends ProtobufTranslationSpec<GroupPopulationReportPluginDataInput, GroupPopulationReportPluginData> {

    @Override
    protected GroupPopulationReportPluginData convertInputObject(GroupPopulationReportPluginDataInput inputObject) {
        if (!GroupPopulationReportPluginData.checkVersionSupported(inputObject.getVersion())) {
            throw new ContractException(CoreTranslationError.UNSUPPORTED_VERSION);
        }

        return GroupPopulationReportPluginData.builder()
                .setReportLabel(this.translationEngine.convertObject(inputObject.getReportLabel()))
                .setReportPeriod(this.translationEngine.convertObject(inputObject.getReportPeriod()))
                .build();
    }

    @Override
    protected GroupPopulationReportPluginDataInput convertAppObject(GroupPopulationReportPluginData appObject) {
        return GroupPopulationReportPluginDataInput.newBuilder()
                .setReportLabel((ReportLabelInput) this.translationEngine
                        .convertObjectAsSafeClass(appObject.getReportLabel(), ReportLabel.class))
                .setReportPeriod(this.translationEngine.convertObject(appObject.getReportPeriod()))
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
