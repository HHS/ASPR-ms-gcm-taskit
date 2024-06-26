package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translationSpecs;

import gov.hhs.aspr.ms.gcm.simulation.plugins.partitions.datamanagers.PartitionsPluginData;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.data.input.PartitionsPluginDataInput;
import gov.hhs.aspr.ms.taskit.core.CoreTranslationError;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationSpec;
import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain PartitionsPluginDataInput} and {@linkplain PartitionsPluginData}
 */
public class PartitionsPluginDataTranslationSpec
        extends ProtobufTranslationSpec<PartitionsPluginDataInput, PartitionsPluginData> {

    @Override
    protected PartitionsPluginData convertInputObject(PartitionsPluginDataInput inputObject) {
        if (!PartitionsPluginData.checkVersionSupported(inputObject.getVersion())) {
            throw new ContractException(CoreTranslationError.UNSUPPORTED_VERSION);
        }

        PartitionsPluginData.Builder builder = PartitionsPluginData.builder();

        builder.setRunContinuitySupport(inputObject.getSupportRunContinuity());

        return builder.build();
    }

    @Override
    protected PartitionsPluginDataInput convertAppObject(PartitionsPluginData appObject) {
        PartitionsPluginDataInput.Builder builder = PartitionsPluginDataInput.newBuilder();

        builder.setVersion(appObject.getVersion()).setSupportRunContinuity(appObject.supportsRunContinuity());

        return builder.build();
    }

    @Override
    public Class<PartitionsPluginData> getAppObjectClass() {
        return PartitionsPluginData.class;
    }

    @Override
    public Class<PartitionsPluginDataInput> getInputObjectClass() {
        return PartitionsPluginDataInput.class;
    }

}
