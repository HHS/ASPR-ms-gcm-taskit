package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translation.specs;

import java.util.Map;
import java.util.Set;

import gov.hhs.aspr.ms.gcm.simulation.plugins.materials.datamangers.MaterialsPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.materials.support.BatchId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.materials.support.BatchPropertyId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.materials.support.MaterialId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.materials.support.MaterialsProducerId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.materials.support.MaterialsProducerPropertyId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.materials.support.StageId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.properties.support.PropertyDefinition;
import gov.hhs.aspr.ms.gcm.simulation.plugins.resources.support.ResourceId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.data.input.MaterialsPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.support.input.BatchIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.support.input.BatchInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.support.input.BatchPropertyDefinitionMapInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.support.input.BatchPropertyValueInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.support.input.MaterialIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.support.input.MaterialsProducerIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.support.input.MaterialsProducerInventoryBatchesInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.support.input.MaterialsProducerPropertyValueMapInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.support.input.MaterialsProducerResourceLevelMapInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.support.input.MaterialsProducerStagesInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.support.input.StageBatchInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.support.input.StageIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.support.input.StageInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.support.input.PropertyDefinitionInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.support.input.PropertyDefinitionMapInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.support.input.PropertyValueMapInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.support.input.ResourceInitializationInput;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitError;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;
import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain MaterialsPluginDataInput} and {@linkplain MaterialsPluginData}
 */
public class MaterialsPluginDataTranslationSpec
        extends ProtobufTranslationSpec<MaterialsPluginDataInput, MaterialsPluginData> {

    @Override
    protected MaterialsPluginData translateInputObject(MaterialsPluginDataInput inputObject) {
        if (!MaterialsPluginData.checkVersionSupported(inputObject.getVersion())) {
            throw new ContractException(TaskitError.UNSUPPORTED_VERSION);
        }

        MaterialsPluginData.Builder builder = MaterialsPluginData.builder();

        builder.setNextBatchRecordId(inputObject.getNextBatchRecordId());
        builder.setNextStageRecordId(inputObject.getNextStageRecordId());

        // material ids
        for (MaterialIdInput materialIdInput : inputObject.getMaterialIdsList()) {
            MaterialId materialId = this.taskitEngine.translateObject(materialIdInput);
            builder.addMaterial(materialId);
        }

        // material producer ids
        for (MaterialsProducerIdInput materialsProducerIdInput : inputObject.getMaterialsProducerIdsList()) {
            MaterialsProducerId materialsProducerId = this.taskitEngine.translateObject(materialsProducerIdInput);
            builder.addMaterialsProducerId(materialsProducerId);
        }

        // batch property defintions
        for (BatchPropertyDefinitionMapInput batchPropertyDefinitionMapInput : inputObject
                .getBatchPropertyDefinitionsList()) {
            MaterialId materialId = this.taskitEngine.translateObject(batchPropertyDefinitionMapInput.getMaterialId());
            for (PropertyDefinitionMapInput propertyDefinitionMapInput : batchPropertyDefinitionMapInput
                    .getPropertyDefinitionsList()) {
                BatchPropertyId batchPropertyId = this.taskitEngine
                        .getObjectFromAny(propertyDefinitionMapInput.getPropertyId());
                PropertyDefinition propertyDefinition = this.taskitEngine
                        .translateObject(propertyDefinitionMapInput.getPropertyDefinition());

                builder.defineBatchProperty(materialId, batchPropertyId, propertyDefinition);
            }
        }

        // material producer property definition
        for (PropertyDefinitionMapInput propertyDefinitionMapInput : inputObject
                .getMaterialsProducerPropertyDefinitionsList()) {
            MaterialsProducerPropertyId materialsProducerPropertyId = this.taskitEngine
                    .getObjectFromAny(propertyDefinitionMapInput.getPropertyId());
            PropertyDefinition propertyDefinition = this.taskitEngine
                    .translateObject(propertyDefinitionMapInput.getPropertyDefinition());
            builder.defineMaterialsProducerProperty(materialsProducerPropertyId, propertyDefinition);
        }

        // material producer property value
        for (MaterialsProducerPropertyValueMapInput materialsProducerPropertyValueMapInput : inputObject
                .getMaterialsProducerPropertyValuesList()) {
            MaterialsProducerId materialsProducerId = this.taskitEngine
                    .translateObject(materialsProducerPropertyValueMapInput.getMaterialsProducerId());
            for (PropertyValueMapInput propertyValueMapInput : materialsProducerPropertyValueMapInput
                    .getPropertyValuesList()) {
                MaterialsProducerPropertyId materialsProducerPropertyId = this.taskitEngine
                        .getObjectFromAny(propertyValueMapInput.getPropertyId());
                Object value = this.taskitEngine.getObjectFromAny(propertyValueMapInput.getPropertyValue());

                builder.setMaterialsProducerPropertyValue(materialsProducerId, materialsProducerPropertyId, value);
            }
        }

        // material producer resource level
        for (MaterialsProducerResourceLevelMapInput materialsProducerResourceLevelMapInput : inputObject
                .getMaterialsProducerResourceLevelsList()) {
            MaterialsProducerId materialsProducerId = this.taskitEngine
                    .translateObject(materialsProducerResourceLevelMapInput.getMaterialsProducerId());
            for (ResourceInitializationInput resourceInitializationInput : materialsProducerResourceLevelMapInput
                    .getResourceLevelsList()) {
                ResourceId resourceId = this.taskitEngine.translateObject(resourceInitializationInput.getResourceId());
                long amount = resourceInitializationInput.getAmount();

                builder.setMaterialsProducerResourceLevel(materialsProducerId, resourceId, amount);
            }
        }

        // batch ids
        for (BatchInput batchMapInput : inputObject.getBatchIdsList()) {
            BatchId batchId = this.taskitEngine.translateObject(batchMapInput.getBatchId());
            MaterialId materialId = this.taskitEngine.translateObject(batchMapInput.getMaterialId());
            double amount = batchMapInput.getAmount();

            builder.addBatch(batchId, materialId, amount);
        }

        // batch property values
        for (BatchPropertyValueInput batchPropertyValueInput : inputObject.getBatchPropertyValuesList()) {
            BatchId batchId = this.taskitEngine.translateObject(batchPropertyValueInput.getBatchId());

            for (PropertyValueMapInput propertyValueMapInput : batchPropertyValueInput.getPropertyValuesList()) {
                BatchPropertyId batchPropertyId = this.taskitEngine
                        .getObjectFromAny(propertyValueMapInput.getPropertyId());
                Object propertyValue = this.taskitEngine.getObjectFromAny(propertyValueMapInput.getPropertyValue());

                builder.setBatchPropertyValue(batchId, batchPropertyId, propertyValue);
            }
        }

        // material producer inventory batches
        for (MaterialsProducerInventoryBatchesInput inventoryBatchesInput : inputObject
                .getMaterialsProducerInverntoryBatchesList()) {
            MaterialsProducerId materialsProducerId = this.taskitEngine
                    .translateObject(inventoryBatchesInput.getMaterialsProducerId());

            for (BatchIdInput batchIdInput : inventoryBatchesInput.getBatchesList()) {
                builder.addBatchToMaterialsProducerInventory(this.taskitEngine.translateObject(batchIdInput),
                        materialsProducerId);
            }
        }

        // stage ids
        for (StageInput stageMapInput : inputObject.getStageIdsList()) {
            StageId stageId = this.taskitEngine.translateObject(stageMapInput.getStageId());
            boolean offered = stageMapInput.getOffered();

            builder.addStage(stageId, offered);
        }

        // stage batches
        for (StageBatchInput stageBatchInput : inputObject.getStageBatchesList()) {
            StageId stageId = this.taskitEngine.translateObject(stageBatchInput.getStageId());
            for (BatchIdInput batchIdInput : stageBatchInput.getBatchesInStageList()) {
                BatchId batchId = this.taskitEngine.translateObject(batchIdInput);

                builder.addBatchToStage(stageId, batchId);
            }
        }

        // material producer stages
        for (MaterialsProducerStagesInput materialsStagesInput : inputObject.getMaterialsProducerStagesList()) {
            MaterialsProducerId materialsProducerId = this.taskitEngine
                    .translateObject(materialsStagesInput.getMaterialsProducerId());

            for (StageIdInput stageIdInput : materialsStagesInput.getStagesList()) {
                builder.addStageToMaterialProducer(this.taskitEngine.translateObject(stageIdInput),
                        materialsProducerId);
            }
        }

        return builder.build();
    }

    @Override
    protected MaterialsPluginDataInput translateAppObject(MaterialsPluginData appObject) {
        MaterialsPluginDataInput.Builder builder = MaterialsPluginDataInput.newBuilder();

        builder.setVersion(appObject.getVersion());

        builder.setNextBatchRecordId(appObject.getNextBatchRecordId());
        builder.setNextStageRecordId(appObject.getNextStageRecordId());

        Set<MaterialId> materialIds = appObject.getMaterialIds();
        Map<MaterialId, Map<BatchPropertyId, PropertyDefinition>> batchPropertyDefinitions = appObject
                .getBatchPropertyDefinitions();
        Set<MaterialsProducerId> materialsProducerIds = appObject.getMaterialsProducerIds();
        Map<MaterialsProducerPropertyId, PropertyDefinition> materialsProducerPropertyDefinitions = appObject
                .getMaterialsProducerPropertyDefinitions();
        Map<MaterialsProducerId, Map<MaterialsProducerPropertyId, Object>> materialsProducerPropertyValues = appObject
                .getMaterialsProducerPropertyValues();
        Map<MaterialsProducerId, Map<ResourceId, Long>> materialsProducerResourceLevels = appObject
                .getMaterialsProducerResourceLevels();
        Set<BatchId> batchIds = appObject.getBatchIds();
        Map<BatchId, MaterialId> batchMaterials = appObject.getBatchMaterials();
        Map<BatchId, Double> batchAmounts = appObject.getBatchAmounts();
        Map<BatchId, Map<BatchPropertyId, Object>> batchPropertyValues = appObject.getBatchPropertyValues();
        Map<MaterialsProducerId, Set<BatchId>> materialsProducerInventoryBatches = appObject
                .getMaterialsProducerInventoryBatches();
        Set<StageId> stageIds = appObject.getStageIds();
        Map<StageId, Boolean> stageOffers = appObject.getStageOffers();
        Map<StageId, Set<BatchId>> stageBatches = appObject.getStageBatches();
        Map<MaterialsProducerId, Set<StageId>> materialsProducerStages = appObject.getMaterialsProducerStages();

        // material ids
        for (MaterialId materialId : materialIds) {
            MaterialIdInput materialIdInput = this.taskitEngine.translateObjectAsClassSafe(materialId,
                    MaterialId.class);
            // add materialIds
            builder.addMaterialIds(materialIdInput);
        }

        // material producer ids
        for (MaterialsProducerId materialsProducerId : materialsProducerIds) {
            MaterialsProducerIdInput materialsProducerIdInput = this.taskitEngine
                    .translateObjectAsClassSafe(materialsProducerId, MaterialsProducerId.class);

            builder.addMaterialsProducerIds(materialsProducerIdInput);
        }

        // batch property definitions
        for (MaterialId materialId : batchPropertyDefinitions.keySet()) {
            MaterialIdInput materialIdInput = this.taskitEngine.translateObjectAsClassSafe(materialId,
                    MaterialId.class);

            BatchPropertyDefinitionMapInput.Builder batchPropertyDefinitionMapBuilder = BatchPropertyDefinitionMapInput
                    .newBuilder()
                    .setMaterialId(materialIdInput);

            Map<BatchPropertyId, PropertyDefinition> batchPropDefMap = batchPropertyDefinitions.get(materialId);

            for (BatchPropertyId batchPropertyId : batchPropDefMap.keySet()) {
                PropertyDefinition propertyDefinition = batchPropDefMap.get(batchPropertyId);
                PropertyDefinitionInput propertyDefinitionInput = this.taskitEngine.translateObject(propertyDefinition);

                PropertyDefinitionMapInput propertyDefinitionMapInput = PropertyDefinitionMapInput.newBuilder()
                        .setPropertyDefinition(propertyDefinitionInput)
                        .setPropertyId(this.taskitEngine.getAnyFromObject(batchPropertyId))
                        .build();

                batchPropertyDefinitionMapBuilder.addPropertyDefinitions(propertyDefinitionMapInput);
            }

            builder.addBatchPropertyDefinitions(batchPropertyDefinitionMapBuilder.build());
        }

        // materials producer property definitions
        for (MaterialsProducerPropertyId materialsProducerPropertyId : materialsProducerPropertyDefinitions.keySet()) {
            PropertyDefinitionInput propertyDefinitionInput = this.taskitEngine
                    .translateObject(materialsProducerPropertyDefinitions.get(materialsProducerPropertyId));

            PropertyDefinitionMapInput propertyDefinitionMapInput = PropertyDefinitionMapInput.newBuilder()
                    .setPropertyDefinition(propertyDefinitionInput)
                    .setPropertyId(this.taskitEngine.getAnyFromObject(materialsProducerPropertyId))
                    .build();

            // add materialsProducerPropertyDefinitions
            builder.addMaterialsProducerPropertyDefinitions(propertyDefinitionMapInput);
        }

        // materials producer property values
        for (MaterialsProducerId materialsProducerId : materialsProducerPropertyValues.keySet()) {
            MaterialsProducerIdInput materialsProducerIdInput = this.taskitEngine
                    .translateObjectAsClassSafe(materialsProducerId, MaterialsProducerId.class);

            MaterialsProducerPropertyValueMapInput.Builder materialsProducerPropertyValueMapInput = MaterialsProducerPropertyValueMapInput
                    .newBuilder()
                    .setMaterialsProducerId(materialsProducerIdInput);

            Map<MaterialsProducerPropertyId, Object> materialsProducerPropVals = materialsProducerPropertyValues
                    .get(materialsProducerId);

            for (MaterialsProducerPropertyId materialsProducerPropertyId : materialsProducerPropVals.keySet()) {
                Object value = materialsProducerPropVals.get(materialsProducerPropertyId);

                PropertyValueMapInput propertyValueMapInput = PropertyValueMapInput.newBuilder()
                        .setPropertyValue(this.taskitEngine.getAnyFromObject(value))
                        .setPropertyId(this.taskitEngine.getAnyFromObject(materialsProducerPropertyId))
                        .build();

                materialsProducerPropertyValueMapInput.addPropertyValues(propertyValueMapInput);
            }

            builder.addMaterialsProducerPropertyValues(materialsProducerPropertyValueMapInput.build());
        }

        // materials producer resource levels
        for (MaterialsProducerId materialsProducerId : materialsProducerResourceLevels.keySet()) {
            MaterialsProducerIdInput materialsProducerIdInput = this.taskitEngine
                    .translateObjectAsClassSafe(materialsProducerId, MaterialsProducerId.class);

            MaterialsProducerResourceLevelMapInput.Builder resourceLevelMapBuilder = MaterialsProducerResourceLevelMapInput
                    .newBuilder()
                    .setMaterialsProducerId(materialsProducerIdInput);

            Map<ResourceId, Long> resourceLevels = materialsProducerResourceLevels.get(materialsProducerId);

            for (ResourceId resourceId : resourceLevels.keySet()) {
                long amount = appObject.getMaterialsProducerResourceLevel(materialsProducerId, resourceId);

                ResourceInitializationInput resourceInitializationInput = ResourceInitializationInput.newBuilder()
                        .setAmount(amount)
                        .setResourceId(this.taskitEngine.getAnyFromObject(resourceId))
                        .build();

                resourceLevelMapBuilder.addResourceLevels(resourceInitializationInput);

            }

            builder.addMaterialsProducerResourceLevels(resourceLevelMapBuilder.build());
        }

        // batch ids
        for (BatchId batchId : batchIds) {
            BatchIdInput batchIdInput = this.taskitEngine.translateObject(batchId);
            double amount = batchAmounts.get(batchId);
            MaterialIdInput materialIdInput = this.taskitEngine.translateObjectAsClassSafe(batchMaterials.get(batchId),
                    MaterialId.class);

            BatchInput batchMap = BatchInput.newBuilder()
                    .setAmount(amount)
                    .setBatchId(batchIdInput)
                    .setMaterialId(materialIdInput)
                    .build();

            builder.addBatchIds(batchMap);
        }

        // batch property values
        for (BatchId batchId : batchPropertyValues.keySet()) {
            BatchIdInput batchIdInput = this.taskitEngine.translateObject(batchId);

            Map<BatchPropertyId, Object> batchPropVals = batchPropertyValues.get(batchId);

            BatchPropertyValueInput.Builder batchPropertyBuilder = BatchPropertyValueInput.newBuilder()
                    .setBatchId(batchIdInput);
            for (BatchPropertyId propertyId : batchPropVals.keySet()) {
                PropertyValueMapInput.Builder batchPropertyValueMap = PropertyValueMapInput.newBuilder();
                Object value = appObject.getBatchPropertyValues(batchId).get(propertyId);

                batchPropertyValueMap.setPropertyValue(this.taskitEngine.getAnyFromObject(value))
                        .setPropertyId(this.taskitEngine.getAnyFromObject(propertyId));

                batchPropertyBuilder.addPropertyValues(batchPropertyValueMap.build());
            }

            builder.addBatchPropertyValues(batchPropertyBuilder.build());
        }

        // materials producer inventory batches
        for (MaterialsProducerId materialsProducerId : materialsProducerInventoryBatches.keySet()) {
            MaterialsProducerIdInput materialsProducerIdInput = this.taskitEngine
                    .translateObjectAsClassSafe(materialsProducerId, MaterialsProducerId.class);

            MaterialsProducerInventoryBatchesInput.Builder inventoryBatchBuilder = MaterialsProducerInventoryBatchesInput
                    .newBuilder()
                    .setMaterialsProducerId(materialsProducerIdInput);

            Set<BatchId> batches = materialsProducerInventoryBatches.get(materialsProducerId);

            for (BatchId batchId : batches) {
                BatchIdInput batchIdInput = this.taskitEngine.translateObject(batchId);
                inventoryBatchBuilder.addBatches(batchIdInput);
            }

            builder.addMaterialsProducerInverntoryBatches(inventoryBatchBuilder.build());
        }

        // stage ids
        for (StageId stageId : stageIds) {

            StageInput.Builder stageMapBuilder = StageInput.newBuilder();

            StageIdInput stageIdInput = this.taskitEngine.translateObject(stageId);
            boolean offered = stageOffers.get(stageId);

            stageMapBuilder.setOffered(offered).setStageId(stageIdInput);

            builder.addStageIds(stageMapBuilder.build());
        }

        // stage batches
        for (StageId stageId : stageBatches.keySet()) {
            StageIdInput stageIdInput = this.taskitEngine.translateObject(stageId);

            StageBatchInput.Builder stageBatchBuilder = StageBatchInput.newBuilder().setStageId(stageIdInput);

            for (BatchId batchId : stageBatches.get(stageId)) {
                BatchIdInput batchIdInput = this.taskitEngine.translateObject(batchId);

                stageBatchBuilder.addBatchesInStage(batchIdInput);
            }

            builder.addStageBatches(stageBatchBuilder.build());
        }

        // materials producer stages
        for (MaterialsProducerId materialsProducerId : materialsProducerStages.keySet()) {
            MaterialsProducerIdInput materialsProducerIdInput = this.taskitEngine
                    .translateObjectAsClassSafe(materialsProducerId, MaterialsProducerId.class);

            MaterialsProducerStagesInput.Builder materialsStagesBuilder = MaterialsProducerStagesInput.newBuilder();
            materialsStagesBuilder.setMaterialsProducerId(materialsProducerIdInput);

            Set<StageId> stages = materialsProducerStages.get(materialsProducerId);

            for (StageId stageId : stages) {
                StageIdInput stageIdInput = this.taskitEngine.translateObject(stageId);
                materialsStagesBuilder.addStages(stageIdInput);
            }

            builder.addMaterialsProducerStages(materialsStagesBuilder.build());
        }

        return builder.build();
    }

    @Override
    public Class<MaterialsPluginData> getAppObjectClass() {
        return MaterialsPluginData.class;
    }

    @Override
    public Class<MaterialsPluginDataInput> getInputObjectClass() {
        return MaterialsPluginDataInput.class;
    }

}
