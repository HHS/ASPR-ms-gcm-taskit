package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translationSpecs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.materials.reports.MaterialsProducerPropertyReportPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.reports.support.ReportLabel;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.reports.input.MaterialsProducerPropertyReportPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.support.input.ReportLabelInput;
import gov.hhs.aspr.ms.taskit.core.CoreTranslationError;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationSpec;
import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain MaterialsProducerPropertyReportPluginDataInput} and
 * {@linkplain MaterialsProducerPropertyReportPluginData}
 */
public class MaterialsProducerPropertyReportPluginDataTranslationSpec extends
        ProtobufTranslationSpec<MaterialsProducerPropertyReportPluginDataInput, MaterialsProducerPropertyReportPluginData> {

    @Override
    protected MaterialsProducerPropertyReportPluginData convertInputObject(
            MaterialsProducerPropertyReportPluginDataInput inputObject) {
        if (!MaterialsProducerPropertyReportPluginData.checkVersionSupported(inputObject.getVersion())) {
            throw new ContractException(CoreTranslationError.UNSUPPORTED_VERSION);
        }

        MaterialsProducerPropertyReportPluginData.Builder builder = MaterialsProducerPropertyReportPluginData.builder();

        ReportLabel reportLabel = this.translationEngine.convertObject(inputObject.getReportLabel());

        builder.setReportLabel(reportLabel);
        return builder.build();
    }

    @Override
    protected MaterialsProducerPropertyReportPluginDataInput convertAppObject(
            MaterialsProducerPropertyReportPluginData appObject) {
        MaterialsProducerPropertyReportPluginDataInput.Builder builder = MaterialsProducerPropertyReportPluginDataInput
                .newBuilder();

        builder.setVersion(appObject.getVersion());

        ReportLabelInput reportLabelInput = this.translationEngine.convertObjectAsSafeClass(appObject.getReportLabel(),
                ReportLabel.class);

        builder.setReportLabel(reportLabelInput);
        return builder.build();
    }

    @Override
    public Class<MaterialsProducerPropertyReportPluginData> getAppObjectClass() {
        return MaterialsProducerPropertyReportPluginData.class;
    }

    @Override
    public Class<MaterialsProducerPropertyReportPluginDataInput> getInputObjectClass() {
        return MaterialsProducerPropertyReportPluginDataInput.class;
    }

}
