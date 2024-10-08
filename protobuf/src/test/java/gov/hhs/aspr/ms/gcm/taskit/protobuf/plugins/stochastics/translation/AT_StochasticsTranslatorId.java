package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.translation;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTestField;

public class AT_StochasticsTranslatorId {

    @Test
    @UnitTestField(target = StochasticsTranslatorId.class, name = "TRANSLATOR_ID")
    public void testTranslatorId() {
        assertNotNull(StochasticsTranslatorId.TRANSLATOR_ID);
    }
}
