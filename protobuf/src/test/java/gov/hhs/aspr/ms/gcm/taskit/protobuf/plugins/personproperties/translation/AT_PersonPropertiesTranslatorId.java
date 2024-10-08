package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.translation;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTestField;

public class AT_PersonPropertiesTranslatorId {

    @Test
    @UnitTestField(target = PersonPropertiesTranslatorId.class, name = "TRANSLATOR_ID")
    public void testTranslatorId() {
        assertNotNull(PersonPropertiesTranslatorId.TRANSLATOR_ID);
    }
}
