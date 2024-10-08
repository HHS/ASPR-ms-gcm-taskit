package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTestField;

public class AT_PeopleTranslatorId {

    @Test
    @UnitTestField(target = PeopleTranslatorId.class, name = "TRANSLATOR_ID")
    public void testTranslatorId() {
        assertNotNull(PeopleTranslatorId.TRANSLATOR_ID);
    }
}
