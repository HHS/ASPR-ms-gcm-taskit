package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.people.translation.PeopleTranslatorId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.translation.PersonPropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.personproperties.translation.PersonPropertiesTranslatorId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslatorId;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslatorId;
import gov.hhs.aspr.ms.taskit.core.translation.Translator;
import gov.hhs.aspr.ms.taskit.core.testsupport.TranslatorTestSupport;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_PersonPropertiesTranslator {

    @Test
    @UnitTestForCoverage
    public void testGetTranslationSpecs() throws ClassNotFoundException {
        Set<String> missing = TranslatorTestSupport.testGetTranslationSpecs(PersonPropertiesTranslator.class,
                PersonPropertiesTranslator.getTranslationSpecs());

        assertTrue(missing.isEmpty(), missing.toString());
    }

    @Test
    @UnitTestMethod(target = PersonPropertiesTranslator.class, name = "getTranslator", args = {})
    public void testGetTranslator() {
        Translator expectedTranslator = Translator.builder()
                .setTranslatorId(PersonPropertiesTranslatorId.TRANSLATOR_ID)
                .addDependency(PropertiesTranslatorId.TRANSLATOR_ID)
                .addDependency(PeopleTranslatorId.TRANSLATOR_ID)
                .addDependency(ReportsTranslatorId.TRANSLATOR_ID)
                .setInitializer((translatorContext) -> {
                })
                .build();

        assertEquals(expectedTranslator, PersonPropertiesTranslator.getTranslator());
    }

}
