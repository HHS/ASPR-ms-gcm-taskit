package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.translation;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslatorId;
import gov.hhs.aspr.ms.util.annotations.UnitTestField;

public class AT_ReportsTranslatorId {

    @Test
    @UnitTestField(target = ReportsTranslatorId.class, name = "TRANSLATOR_ID")
    public void testTranslatorId() {
        assertNotNull(ReportsTranslatorId.TRANSLATOR_ID);
    }
}
