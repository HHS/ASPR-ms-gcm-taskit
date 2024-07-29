package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translation.MaterialsTranslatorId;
import gov.hhs.aspr.ms.util.annotations.UnitTestField;

public class AT_MaterialsTranslatorId {

    @Test
    @UnitTestField(target = MaterialsTranslatorId.class, name = "TRANSLATOR_ID")
    public void testTranslatorId() {
        assertNotNull(MaterialsTranslatorId.TRANSLATOR_ID);
    }
}
