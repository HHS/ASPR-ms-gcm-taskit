package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translationSpecs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.reports.GroupPropertyReportPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.support.GroupPropertyId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.groups.support.GroupTypeId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.ReportLabel;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.reports.input.GroupPropertyReportPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.reports.input.GroupPropertyReportPropertyMap;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.support.input.GroupPropertyIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.support.input.GroupTypeIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.support.input.ReportLabelInput;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitError;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;
import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain GroupPropertyReportPluginDataInput} and
 * {@linkplain GroupPropertyReportPluginData}
 */
public class GroupPropertyReportPluginDataTranslationSpec
        extends ProtobufTranslationSpec<GroupPropertyReportPluginDataInput, GroupPropertyReportPluginData> {

    @Override
    protected GroupPropertyReportPluginData translateInputObject(GroupPropertyReportPluginDataInput inputObject) {
        if (!GroupPropertyReportPluginData.checkVersionSupported(inputObject.getVersion())) {
            throw new ContractException(TaskitError.UNSUPPORTED_VERSION);
        }

        GroupPropertyReportPluginData.Builder builder = GroupPropertyReportPluginData.builder();

        ReportLabel reportLabel = this.taskitEngine.translateObject(inputObject.getReportLabel());
        builder.setReportLabel(reportLabel);

        builder.setDefaultInclusion(inputObject.getDefaultInclusionPolicy());
        builder.setReportPeriod(this.taskitEngine.translateObject(inputObject.getReportPeriod()));

        for (GroupPropertyReportPropertyMap propertyMap : inputObject.getIncludedPropertiesList()) {
            GroupTypeId groupTypeId = this.taskitEngine.translateObject(propertyMap.getGroupTypeId());
            for (GroupPropertyIdInput groupPropertyIdInput : propertyMap.getGroupPropertiesList()) {
                GroupPropertyId groupPropertyId = this.taskitEngine.translateObject(groupPropertyIdInput);

                builder.includeGroupProperty(groupTypeId, groupPropertyId);
            }
        }

        for (GroupPropertyReportPropertyMap propertyMap : inputObject.getExcludedPropertiesList()) {
            GroupTypeId groupTypeId = this.taskitEngine.translateObject(propertyMap.getGroupTypeId());
            for (GroupPropertyIdInput groupPropertyIdInput : propertyMap.getGroupPropertiesList()) {
                GroupPropertyId groupPropertyId = this.taskitEngine.translateObject(groupPropertyIdInput);

                builder.excludeGroupProperty(groupTypeId, groupPropertyId);
            }
        }

        return builder.build();
    }

    @Override
    protected GroupPropertyReportPluginDataInput translateAppObject(GroupPropertyReportPluginData appObject) {
        GroupPropertyReportPluginDataInput.Builder builder = GroupPropertyReportPluginDataInput.newBuilder();

        builder.setVersion(appObject.getVersion());

        ReportLabelInput reportLabelInput = this.taskitEngine.translateObjectAsClassSafe(appObject.getReportLabel(),
                ReportLabel.class);

        builder.setDefaultInclusionPolicy(appObject.getDefaultInclusionPolicy())
                .setReportLabel(reportLabelInput)
                .setReportPeriod(this.taskitEngine.translateObject(appObject.getReportPeriod()));

        for (GroupTypeId groupTypeId : appObject.getGroupTypeIds()) {
            GroupTypeIdInput groupTypeIdInput = this.taskitEngine.translateObjectAsClassSafe(groupTypeId,
                    GroupTypeId.class);

            GroupPropertyReportPropertyMap.Builder groupPropertyReportBuilder = GroupPropertyReportPropertyMap
                    .newBuilder()
                    .setGroupTypeId(groupTypeIdInput);
            for (GroupPropertyId groupPropertyId : appObject.getIncludedProperties(groupTypeId)) {
                GroupPropertyIdInput groupPropertyIdInput = this.taskitEngine
                        .translateObjectAsClassSafe(groupPropertyId, GroupPropertyId.class);
                groupPropertyReportBuilder.addGroupProperties(groupPropertyIdInput);
            }
            builder.addIncludedProperties(groupPropertyReportBuilder.build());

            groupPropertyReportBuilder = GroupPropertyReportPropertyMap.newBuilder().setGroupTypeId(groupTypeIdInput);
            for (GroupPropertyId groupPropertyId : appObject.getExcludedProperties(groupTypeId)) {
                GroupPropertyIdInput groupPropertyIdInput = this.taskitEngine
                        .translateObjectAsClassSafe(groupPropertyId, GroupPropertyId.class);
                groupPropertyReportBuilder.addGroupProperties(groupPropertyIdInput);
            }
            builder.addExcludedProperties(groupPropertyReportBuilder.build());
        }

        return builder.build();
    }

    @Override
    public Class<GroupPropertyReportPluginData> getAppObjectClass() {
        return GroupPropertyReportPluginData.class;
    }

    @Override
    public Class<GroupPropertyReportPluginDataInput> getInputObjectClass() {
        return GroupPropertyReportPluginDataInput.class;
    }

}
