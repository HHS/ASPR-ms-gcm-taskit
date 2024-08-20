package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translation;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTestField;

public class AT_RegionsTranslatorId {

    @Test
    @UnitTestField(target = RegionsTranslatorId.class, name = "TRANSLATOR_ID")
    public void testTranslatorId() {
        assertNotNull(RegionsTranslatorId.TRANSLATOR_ID);
    }
}
