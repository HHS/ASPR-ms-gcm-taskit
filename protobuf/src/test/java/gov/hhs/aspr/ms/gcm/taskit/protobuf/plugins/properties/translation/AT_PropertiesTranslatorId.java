package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslatorId;
import gov.hhs.aspr.ms.util.annotations.UnitTestField;

public class AT_PropertiesTranslatorId {

    @Test
    @UnitTestField(target = PropertiesTranslatorId.class, name = "TRANSLATOR_ID")
    public void testTranslatorId() {
        assertNotNull(PropertiesTranslatorId.TRANSLATOR_ID);
    }
}
