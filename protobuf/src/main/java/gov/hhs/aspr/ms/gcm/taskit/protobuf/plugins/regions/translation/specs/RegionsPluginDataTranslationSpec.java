package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translation.specs;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import gov.hhs.aspr.ms.gcm.simulation.plugins.people.support.PersonId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.properties.support.PropertyDefinition;
import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.datamanagers.RegionsPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.support.RegionId;
import gov.hhs.aspr.ms.gcm.simulation.plugins.regions.support.RegionPropertyId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.support.input.PropertyDefinitionInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.support.input.PropertyDefinitionMapInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.support.input.PropertyValueMapInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.data.input.RegionsPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.support.input.RegionIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.support.input.RegionMembershipInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.support.input.RegionMembershipInput.RegionPersonInfo;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.support.input.RegionPropertyValueMapInput;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitError;
import gov.hhs.aspr.ms.taskit.protobuf.translation.ProtobufTranslationSpec;
import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain RegionsPluginDataInput} and {@linkplain RegionsPluginData}
 */
public class RegionsPluginDataTranslationSpec
        extends ProtobufTranslationSpec<RegionsPluginDataInput, RegionsPluginData> {

    @Override
    protected RegionsPluginData translateInputObject(RegionsPluginDataInput inputObject) {
        if (!RegionsPluginData.checkVersionSupported(inputObject.getVersion())) {
            throw new ContractException(TaskitError.UNSUPPORTED_VERSION);
        }

        RegionsPluginData.Builder builder = RegionsPluginData.builder();

        // add regions
        for (RegionIdInput regionIdInput : inputObject.getRegionIdsList()) {
            RegionId regionId = this.taskitEngine.translateObject(regionIdInput);

            builder.addRegion(regionId);
        }

        // define regions
        for (PropertyDefinitionMapInput propertyDefinitionMapInput : inputObject.getRegionPropertyDefinitionsList()) {
            RegionPropertyId regionPropertyId = this.taskitEngine
                    .getObjectFromAny(propertyDefinitionMapInput.getPropertyId());
            PropertyDefinition propertyDefinition = this.taskitEngine
                    .translateObject(propertyDefinitionMapInput.getPropertyDefinition());

            builder.defineRegionProperty(regionPropertyId, propertyDefinition);
        }

        // add region property values
        for (RegionPropertyValueMapInput regionPropertyValueMapInput : inputObject.getRegionPropertyValuesList()) {
            RegionId regionId = this.taskitEngine.translateObject(regionPropertyValueMapInput.getRegionId());
            for (PropertyValueMapInput propertyValueMapInput : regionPropertyValueMapInput.getPropertyValueMapList()) {
                RegionPropertyId regionPropertyId = this.taskitEngine
                        .getObjectFromAny(propertyValueMapInput.getPropertyId());
                Object regionPropertyValue = this.taskitEngine
                        .getObjectFromAny(propertyValueMapInput.getPropertyValue());

                builder.setRegionPropertyValue(regionId, regionPropertyId, regionPropertyValue);
            }
        }

        // assign people to regions
        boolean trackRegionArrivalTimes = inputObject.getTrackRegionArrivalTimes();
        builder.setPersonRegionArrivalTracking(trackRegionArrivalTimes);

        for (RegionMembershipInput regionMembershipInput : inputObject.getPersonRegionsList()) {
            RegionId regionId = this.taskitEngine.translateObject(regionMembershipInput.getRegionId());

            for (RegionPersonInfo regionPersonInfo : regionMembershipInput.getPeopleList()) {
                PersonId personId = new PersonId(regionPersonInfo.getPersonId());
                if (trackRegionArrivalTimes) {
                    builder.addPerson(personId, regionId, regionPersonInfo.getArrivalTime());
                } else {
                    builder.addPerson(personId, regionId);
                }
            }
        }

        return builder.build();
    }

    @Override
    protected RegionsPluginDataInput translateAppObject(RegionsPluginData appObject) {
        RegionsPluginDataInput.Builder builder = RegionsPluginDataInput.newBuilder();

        builder.setVersion(appObject.getVersion());

        // add regions
        for (RegionId regionId : appObject.getRegionIds()) {
            RegionIdInput regionIdInput = this.taskitEngine.translateObjectAsClassSafe(regionId, RegionId.class);
            builder.addRegionIds(regionIdInput);
        }

        // add region property definitions
        for (RegionPropertyId regionPropertyId : appObject.getRegionPropertyIds()) {
            PropertyDefinitionInput propertyDefinitionInput = this.taskitEngine
                    .translateObject(appObject.getRegionPropertyDefinition(regionPropertyId));

            PropertyDefinitionMapInput propertyDefinitionMapInput = PropertyDefinitionMapInput.newBuilder()
                    .setPropertyId(this.taskitEngine.getAnyFromObject(regionPropertyId))
                    .setPropertyDefinition(propertyDefinitionInput)
                    .build();

            builder.addRegionPropertyDefinitions(propertyDefinitionMapInput);
        }

        for (RegionId regionId : appObject.getRegionIds()) {
            RegionIdInput regionIdInput = this.taskitEngine.translateObjectAsClassSafe(regionId, RegionId.class);

            for (RegionPropertyId regionPropertyId : appObject.getRegionPropertyValues(regionId).keySet()) {
                PropertyValueMapInput propertyValueMapInput = PropertyValueMapInput.newBuilder()
                        .setPropertyId(this.taskitEngine.getAnyFromObject(regionPropertyId))
                        .setPropertyValue(this.taskitEngine
                                .getAnyFromObject(appObject.getRegionPropertyValues(regionId).get(regionPropertyId)))
                        .build();

                RegionPropertyValueMapInput regionPropertyValueMapInput = RegionPropertyValueMapInput.newBuilder()
                        .setRegionId(regionIdInput)
                        .addPropertyValueMap(propertyValueMapInput)
                        .build();

                builder.addRegionPropertyValues(regionPropertyValueMapInput);
            }
        }

        boolean trackRegionArrivalTimes = appObject.getPersonRegionArrivalTrackingPolicy();

        builder.setTrackRegionArrivalTimes(trackRegionArrivalTimes);

        Map<RegionIdInput, List<RegionPersonInfo>> regionMembershipMap = new LinkedHashMap<>();
        for (int i = 0; i < appObject.getPersonCount(); i++) {
            PersonId personId = new PersonId(i);

            RegionId regionId = appObject.getPersonRegion(personId).get();
            RegionIdInput regionIdInput = this.taskitEngine.translateObjectAsClassSafe(regionId, RegionId.class);
            List<RegionPersonInfo> peopleInRegion = regionMembershipMap.get(regionIdInput);

            if (peopleInRegion == null) {
                peopleInRegion = new ArrayList<>();

                regionMembershipMap.put(regionIdInput, peopleInRegion);
            }

            RegionPersonInfo.Builder regionPersonInfoBuilder = RegionPersonInfo.newBuilder().setPersonId(i);
            if (trackRegionArrivalTimes) {
                // can safely assume this because the person region exists
                regionPersonInfoBuilder.setArrivalTime(appObject.getPersonRegionArrivalTime(personId).get());
            }

            peopleInRegion.add(regionPersonInfoBuilder.build());
        }

        for (RegionIdInput regionIdInput : regionMembershipMap.keySet()) {
            RegionMembershipInput.Builder regionMembershipBuilder = RegionMembershipInput.newBuilder();

            regionMembershipBuilder.setRegionId(regionIdInput).addAllPeople(regionMembershipMap.get(regionIdInput));

            builder.addPersonRegions(regionMembershipBuilder.build());
        }

        return builder.build();
    }

    @Override
    public Class<RegionsPluginData> getAppObjectClass() {
        return RegionsPluginData.class;
    }

    @Override
    public Class<RegionsPluginDataInput> getInputObjectClass() {
        return RegionsPluginDataInput.class;
    }

}
