package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.taskit.core.TranslationSpec;
import gov.hhs.aspr.ms.taskit.core.Translator;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_ReportsTranslator {

    @Test
    @UnitTestForCoverage
    public void testGetTranslationSpecs() throws ClassNotFoundException {
        List<TranslationSpec<?, ?>> translationSpecs = ReportsTranslator.getTranslationSpecs();
        List<Class<?>> translationSpecClasses = new ArrayList<>();

        for (TranslationSpec<?, ?> translationSpec : translationSpecs) {
            translationSpecClasses.add(translationSpec.getClass());
        }

        String packageName = this.getClass().getPackageName() + ".translationSpecs";
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        URL packageURL;

        packageURL = classLoader.getResource(packageName.replaceAll("[.]", "/"));

        if (packageURL != null) {
            String packagePath = packageURL.getPath();
            if (packagePath != null) {
                packagePath = packagePath.replaceAll("test-classes", "classes");
                File packageDir = new File(packagePath);
                if (packageDir.isDirectory()) {
                    File[] files = packageDir.listFiles();
                    for (File file : files) {
                        String className = file.getName();
                        if (className.endsWith(".class")) {
                            className = packageName + "." + className.substring(0, className.length() - 6);
                            Class<?> classRef = classLoader.loadClass(className);

                            assertTrue(translationSpecClasses.contains(classRef), classRef.getSimpleName());
                        }
                    }
                }
            }
        }
    }

    @Test
    @UnitTestMethod(target = ReportsTranslator.class, name = "getTranslator", args = {})
    public void testGetTranslator() {
        Translator expectedTranslator = Translator.builder()
                .setTranslatorId(ReportsTranslatorId.TRANSLATOR_ID)
                .setInitializer((translatorContext) -> {
                })
                .build();

        assertEquals(expectedTranslator, ReportsTranslator.getTranslator());
    }
}
