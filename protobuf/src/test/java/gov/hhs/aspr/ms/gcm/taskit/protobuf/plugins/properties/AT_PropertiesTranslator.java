package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.taskit.core.translation.Translator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslatorId;
import gov.hhs.aspr.ms.taskit.core.testsupport.TranslatorTestSupport;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_PropertiesTranslator {

    @Test
    @UnitTestForCoverage
    public void testGetTranslationSpecs() throws ClassNotFoundException {
        Set<String> missing = TranslatorTestSupport.testGetTranslationSpecs(PropertiesTranslator.class,
                PropertiesTranslator.getTranslationSpecs());

        assertTrue(missing.isEmpty(), missing.toString());
    }

    @Test
    @UnitTestMethod(target = PropertiesTranslator.class, name = "getTranslator", args = {})
    public void testGetTranslator() {
        Translator expectedTranslator = Translator.builder()
                .setTranslatorId(PropertiesTranslatorId.TRANSLATOR_ID)
                .setInitializer((translatorContext) -> {
                })
                .build();

        assertEquals(expectedTranslator, PropertiesTranslator.getTranslator());
    }
}
