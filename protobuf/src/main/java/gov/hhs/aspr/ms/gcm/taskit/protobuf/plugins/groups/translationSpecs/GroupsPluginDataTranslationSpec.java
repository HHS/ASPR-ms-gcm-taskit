package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translationSpecs;

import java.util.*;

import gov.hhs.aspr.ms.gcm.plugins.groups.datamanagers.GroupsPluginData;
import gov.hhs.aspr.ms.gcm.plugins.groups.support.GroupId;
import gov.hhs.aspr.ms.gcm.plugins.groups.support.GroupPropertyId;
import gov.hhs.aspr.ms.gcm.plugins.groups.support.GroupPropertyValue;
import gov.hhs.aspr.ms.gcm.plugins.groups.support.GroupTypeId;
import gov.hhs.aspr.ms.gcm.plugins.people.support.PersonId;
import gov.hhs.aspr.ms.gcm.plugins.properties.support.PropertyDefinition;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.data.input.GroupsPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.support.input.GroupInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.support.input.GroupPropertyDefinitionMapInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.support.input.GroupPropertyValueMapInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.support.input.GroupToPersonMembershipInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.support.input.GroupTypeIdInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.support.input.PersonToGroupMembershipInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.support.input.PropertyDefinitionInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.support.input.PropertyDefinitionMapInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.support.input.PropertyValueMapInput;
import gov.hhs.aspr.ms.taskit.core.CoreTranslationError;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationSpec;
import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * TranslationSpec that defines how to convert between
 * {@linkplain GroupsPluginDataInput} and {@linkplain GroupsPluginData}
 */
public class GroupsPluginDataTranslationSpec extends ProtobufTranslationSpec<GroupsPluginDataInput, GroupsPluginData> {

    @Override
    protected GroupsPluginData convertInputObject(GroupsPluginDataInput inputObject) {
        if (!GroupsPluginData.checkVersionSupported(inputObject.getVersion())) {
            throw new ContractException(CoreTranslationError.UNSUPPORTED_VERSION);
        }

        GroupsPluginData.Builder builder = GroupsPluginData.builder();

        // Add groups
        for (GroupInput groupInput : inputObject.getGroupsList()) {
            GroupTypeId groupTypeId = this.translationEngine.convertObject(groupInput.getGroupTypeId());
            for (Integer gId : groupInput.getGIdList()) {
                builder.addGroup(new GroupId(gId), groupTypeId);
            }

        }

        // Add group type ids
        for (GroupTypeIdInput groupTypeIdInput : inputObject.getGroupTypeIdsList()) {
            GroupTypeId groupTypeId = this.translationEngine.convertObject(groupTypeIdInput);
            builder.addGroupTypeId(groupTypeId);
        }

        // Add group type property definitions
        for (GroupPropertyDefinitionMapInput groupPropertyDefinitionMapInput : inputObject
                .getGroupPropertyDefinitionsList()) {
            GroupTypeId groupTypeId = this.translationEngine
                    .convertObject(groupPropertyDefinitionMapInput.getGroupTypeId());
            for (PropertyDefinitionMapInput propertyDefinitionMapInput : groupPropertyDefinitionMapInput
                    .getPropertyDefinitionsList()) {

                GroupPropertyId groupPropertyId = this.translationEngine
                        .convertObject(propertyDefinitionMapInput.getPropertyId());
                PropertyDefinition propertyDefinition = this.translationEngine
                        .convertObject(propertyDefinitionMapInput.getPropertyDefinition());
                builder.defineGroupProperty(groupTypeId, groupPropertyId, propertyDefinition);
            }
        }

        // add group property values
        for (GroupPropertyValueMapInput groupPropertyValueMapInput : inputObject.getGroupPropertyValuesList()) {
            PropertyValueMapInput propertyValueMapInput = groupPropertyValueMapInput.getPropertyValueMap();

            for (int groupId : groupPropertyValueMapInput.getGIdsList()) {

                GroupPropertyId groupPropertyId = this.translationEngine
                        .getObjectFromAny(propertyValueMapInput.getPropertyId());
                Object propertyValue = this.translationEngine
                        .getObjectFromAny(propertyValueMapInput.getPropertyValue());

                builder.setGroupPropertyValue(new GroupId(groupId), groupPropertyId, propertyValue);
            }

        }

        for (PersonToGroupMembershipInput ptgMembership : inputObject.getPersonToGroupMembershipsList()) {
            PersonId personId = new PersonId(ptgMembership.getPId());

            for (int gId : ptgMembership.getGIdsList()) {
                builder.addGroupToPerson(new GroupId(gId), personId);
            }
        }

        for (GroupToPersonMembershipInput gtpMembership : inputObject.getGroupToPersonMembershipsList()) {
            GroupId groupId = new GroupId(gtpMembership.getGId());

            for (int pId : gtpMembership.getPIdsList()) {
                builder.addPersonToGroup(new PersonId(pId), groupId);
            }
        }

        builder.setNextGroupIdValue(inputObject.getNextGroupIdValue());

        return builder.build();
    }

    @Override
    protected GroupsPluginDataInput convertAppObject(GroupsPluginData appObject) {
        GroupsPluginDataInput.Builder builder = GroupsPluginDataInput.newBuilder();

        builder.setVersion(appObject.getVersion());

        // add group type ids
        for (GroupTypeId groupTypeId : appObject.getGroupTypeIds()) {
            GroupTypeIdInput groupTypeIdInput = this.translationEngine.convertObjectAsSafeClass(groupTypeId,
                    GroupTypeId.class);
            builder.addGroupTypeIds(groupTypeIdInput);
        }

        Map<GroupTypeIdInput, List<Integer>> groupMap = new LinkedHashMap<>();
        // add groups
        for (GroupId groupId : appObject.getGroupIds()) {

            GroupTypeIdInput groupTypeIdInput = this.translationEngine
                    .convertObjectAsSafeClass(appObject.getGroupTypeId(groupId), GroupTypeId.class);

            List<Integer> groups = groupMap.get(groupTypeIdInput);

            if (groups == null) {
                groups = new ArrayList<>();
                groupMap.put(groupTypeIdInput, groups);
            }

            groups.add(groupId.getValue());
            //
            // .build();

            //
        }

        for (GroupTypeIdInput groupTypeIdInput : groupMap.keySet()) {
            List<Integer> gIds = groupMap.get(groupTypeIdInput);
            GroupInput groupInput = GroupInput.newBuilder().setGroupTypeId(groupTypeIdInput).addAllGId(gIds).build();

            builder.addGroups(groupInput);
        }

        // add group type property definitions

        for (GroupTypeId groupTypeId : appObject.getGroupTypeIds()) {
            GroupPropertyDefinitionMapInput.Builder groupPropDefMapInputBuilder = GroupPropertyDefinitionMapInput
                    .newBuilder();

            GroupTypeIdInput groupTypeIdInput = this.translationEngine.convertObjectAsSafeClass(groupTypeId,
                    GroupTypeId.class);
            groupPropDefMapInputBuilder.setGroupTypeId(groupTypeIdInput);

            Set<GroupPropertyId> groupPropertyIds = appObject.getGroupPropertyIds(groupTypeId);

            for (GroupPropertyId groupPropertyId : groupPropertyIds) {
                PropertyDefinition propertyDefinition = appObject.getGroupPropertyDefinition(groupTypeId,
                        groupPropertyId);

                PropertyDefinitionInput propertyDefinitionInput = this.translationEngine
                        .convertObject(propertyDefinition);

                PropertyDefinitionMapInput propertyDefInput = PropertyDefinitionMapInput.newBuilder()
                        .setPropertyDefinition(propertyDefinitionInput)
                        .setPropertyId(this.translationEngine.getAnyFromObject(groupPropertyId))
                        .build();

                groupPropDefMapInputBuilder.addPropertyDefinitions(propertyDefInput);
            }

            builder.addGroupPropertyDefinitions(groupPropDefMapInputBuilder.build());
        }
        // add group property values
        Map<GroupPropertyValue, List<Integer>> propValueToGroupIdsMap = new LinkedHashMap<>();

        for (GroupId groupId : appObject.getGroupIds()) {

            // groupPropValMapBuilder.setGId(groupId.getValue());

            List<GroupPropertyValue> groupPropertyValues = appObject.getGroupPropertyValues(groupId);

            for (GroupPropertyValue groupPropertyValue : groupPropertyValues) {
                List<Integer> groupIds = propValueToGroupIdsMap.get(groupPropertyValue);

                if (groupIds == null) {
                    groupIds = new ArrayList<>();
                    propValueToGroupIdsMap.put(groupPropertyValue, groupIds);
                }

                groupIds.add(groupId.getValue());
            }
        }

        for (GroupPropertyValue groupPropertyValue : propValueToGroupIdsMap.keySet()) {
            GroupPropertyValueMapInput.Builder groupPropValMapBuilder = GroupPropertyValueMapInput.newBuilder();

            Object propertyValue = groupPropertyValue.value();
            List<Integer> groupIds = propValueToGroupIdsMap.get(groupPropertyValue);
            Collections.sort(groupIds);

            PropertyValueMapInput propertyValueMapInput = PropertyValueMapInput.newBuilder()
                    .setPropertyValue(this.translationEngine.getAnyFromObject(propertyValue))
                    .setPropertyId(this.translationEngine.getAnyFromObject(groupPropertyValue.groupPropertyId()))
                    .build();

            groupPropValMapBuilder.setPropertyValueMap(propertyValueMapInput).addAllGIds(groupIds);

            builder.addGroupPropertyValues(groupPropValMapBuilder.build());
        }

        // add people
        for (int i = 0; i < appObject.getPersonCount(); i++) {
            PersonId personId = new PersonId(i);
            List<GroupId> groupsForPerson = appObject.getGroupsForPerson(personId);

            if (!groupsForPerson.isEmpty()) {
                PersonToGroupMembershipInput.Builder groupMembershipBuilder = PersonToGroupMembershipInput.newBuilder();

                groupMembershipBuilder.setPId(i);

                for (GroupId groupId : groupsForPerson) {
                    groupMembershipBuilder.addGIds(groupId.getValue());
                }
                builder.addPersonToGroupMemberships(groupMembershipBuilder.build());
            }

        }

        for (int i = 0; i < appObject.getGroupCount(); i++) {
            GroupId groupId = new GroupId(i);
            List<PersonId> peopleInGroup = appObject.getPeopleForGroup(groupId);

            if (!peopleInGroup.isEmpty()) {
                GroupToPersonMembershipInput.Builder groupMembershipBuilder = GroupToPersonMembershipInput.newBuilder();

                groupMembershipBuilder.setGId(i);

                for (PersonId personId : peopleInGroup) {
                    groupMembershipBuilder.addPIds(personId.getValue());
                }
                builder.addGroupToPersonMemberships(groupMembershipBuilder.build());
            }
        }

        builder.setNextGroupIdValue(appObject.getNextGroupIdValue());

        return builder.build();
    }

    @Override
    public Class<GroupsPluginData> getAppObjectClass() {
        return GroupsPluginData.class;
    }

    @Override
    public Class<GroupsPluginDataInput> getInputObjectClass() {
        return GroupsPluginDataInput.class;
    }

}
