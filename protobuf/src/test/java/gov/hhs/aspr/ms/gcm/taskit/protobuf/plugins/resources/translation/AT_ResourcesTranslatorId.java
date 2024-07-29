package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translation;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translation.ResourcesTranslatorId;
import gov.hhs.aspr.ms.util.annotations.UnitTestField;

public class AT_ResourcesTranslatorId {

    @Test
    @UnitTestField(target = ResourcesTranslatorId.class, name = "TRANSLATOR_ID")
    public void testTranslatorId() {
        assertNotNull(ResourcesTranslatorId.TRANSLATOR_ID);
    }
}
