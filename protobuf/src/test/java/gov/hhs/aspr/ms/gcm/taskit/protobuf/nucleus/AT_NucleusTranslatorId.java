package gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTestField;

public class AT_NucleusTranslatorId {

    @Test
    @UnitTestField(target = NucleusTranslatorId.class, name = "TRANSLATOR_ID")
    public void testTranslatorId() {
        assertNotNull(NucleusTranslatorId.TRANSLATOR_ID);
    }
}
