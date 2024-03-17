package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translationSpecs;

import gov.hhs.aspr.ms.gcm.plugins.regions.reports.RegionTransferReportPluginData;
import gov.hhs.aspr.ms.gcm.plugins.reports.support.ReportLabel;
import gov.hhs.aspr.ms.gcm.plugins.reports.support.ReportPeriod;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.reports.input.RegionTransferReportPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.support.input.ReportLabelInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.support.input.ReportPeriodInput;
import gov.hhs.aspr.ms.taskit.core.CoreTranslationError;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationSpec;
import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain RegionTransferReportPluginDataInput} and
 * {@linkplain RegionTransferReportPluginData}
 */
public class RegionTransferReportPluginDataTranslationSpec
        extends ProtobufTranslationSpec<RegionTransferReportPluginDataInput, RegionTransferReportPluginData> {

    @Override
    protected RegionTransferReportPluginData convertInputObject(RegionTransferReportPluginDataInput inputObject) {
        if (!RegionTransferReportPluginData.checkVersionSupported(inputObject.getVersion())) {
            throw new ContractException(CoreTranslationError.UNSUPPORTED_VERSION);
        }

        RegionTransferReportPluginData.Builder builder = RegionTransferReportPluginData.builder();

        ReportLabel reportLabel = this.translationEngine.convertObject(inputObject.getReportLabel());
        ReportPeriod reportPeriod = this.translationEngine.convertObject(inputObject.getReportPeriod());

        builder.setReportLabel(reportLabel).setReportPeriod(reportPeriod);

        return builder.build();
    }

    @Override
    protected RegionTransferReportPluginDataInput convertAppObject(RegionTransferReportPluginData appObject) {
        RegionTransferReportPluginDataInput.Builder builder = RegionTransferReportPluginDataInput.newBuilder();

        builder.setVersion(appObject.getVersion());

        ReportLabelInput reportLabelInput = this.translationEngine.convertObjectAsSafeClass(appObject.getReportLabel(),
                ReportLabel.class);

        ReportPeriodInput reportPeriodInput = this.translationEngine.convertObject(appObject.getReportPeriod());
        builder.setReportLabel(reportLabelInput).setReportPeriod(reportPeriodInput);

        return builder.build();
    }

    @Override
    public Class<RegionTransferReportPluginData> getAppObjectClass() {
        return RegionTransferReportPluginData.class;
    }

    @Override
    public Class<RegionTransferReportPluginDataInput> getInputObjectClass() {
        return RegionTransferReportPluginDataInput.class;
    }

}
