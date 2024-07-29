package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translation.GroupsTranslatorId;
import gov.hhs.aspr.ms.util.annotations.UnitTestField;

public class AT_GroupsTranslatorId {

    @Test
    @UnitTestField(target = GroupsTranslatorId.class, name = "TRANSLATOR_ID")
    public void testTranslatorId() {
        assertNotNull(GroupsTranslatorId.TRANSLATOR_ID);
    }
}
