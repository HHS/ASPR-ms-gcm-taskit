package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translation;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.partitions.translation.PartitionsTranslatorId;
import gov.hhs.aspr.ms.util.annotations.UnitTestField;

public class AT_PartitionsTranslatorId {

    @Test
    @UnitTestField(target = PartitionsTranslatorId.class, name = "TRANSLATOR_ID")
    public void testTranslatorId() {
        assertNotNull(PartitionsTranslatorId.TRANSLATOR_ID);
    }
}
