package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslatorId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslatorId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.regions.translation.RegionsTranslatorId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.translation.ReportsTranslatorId;
import gov.hhs.aspr.ms.taskit.core.testsupport.TranslatorTestSupport;
import gov.hhs.aspr.ms.taskit.core.translation.Translator;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_ResourcesTranslator {

    @Test
    @UnitTestForCoverage
    public void testGetTranslationSpecs() throws ClassNotFoundException {
        Set<String> missing = TranslatorTestSupport.testGetTranslationSpecs(ResourcesTranslator.class,
                ResourcesTranslator.getTranslationSpecs());

        assertTrue(missing.isEmpty(), missing.toString());
    }

    @Test
    @UnitTestMethod(target = ResourcesTranslator.class, name = "getTranslator", args = {})
    public void testGetTranslator() {
        Translator expectedTranslator = Translator.builder()
                .setTranslatorId(ResourcesTranslatorId.TRANSLATOR_ID)
                .addDependency(PeopleTranslatorId.TRANSLATOR_ID)
                .addDependency(PropertiesTranslatorId.TRANSLATOR_ID)
                .addDependency(RegionsTranslatorId.TRANSLATOR_ID)
                .addDependency(ReportsTranslatorId.TRANSLATOR_ID)
                .setInitializer((translatorContext) -> {
                })
                .build();

        assertEquals(expectedTranslator, ResourcesTranslator.getTranslator());
    }

}