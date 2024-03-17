package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.translationSpecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.plugins.globalproperties.datamanagers.GlobalPropertiesPluginData;
import gov.hhs.aspr.ms.gcm.plugins.globalproperties.testsupport.GlobalPropertiesTestPluginFactory;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.GlobalPropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.data.input.GlobalPropertiesPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.taskit.core.CoreTranslationError;
import gov.hhs.aspr.ms.taskit.protobuf.ProtobufTranslationEngine;
import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestForCoverage;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;
import gov.hhs.aspr.ms.util.errors.ContractException;

public class AT_GlobalPropertiesPluginDataTranslationSpec {

    @Test
    @UnitTestConstructor(target = GlobalPropertiesPluginDataTranslationSpec.class, args = {})
    public void testConstructor() {
        assertNotNull(new GlobalPropertiesPluginDataTranslationSpec());
    }

    @Test
    @UnitTestForCoverage
    public void testConvertObject() {

        ProtobufTranslationEngine protobufTranslationEngine = ProtobufTranslationEngine.builder()
                .addTranslator(GlobalPropertiesTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .build();

        GlobalPropertiesPluginDataTranslationSpec translationSpec = new GlobalPropertiesPluginDataTranslationSpec();
        translationSpec.init(protobufTranslationEngine);

        GlobalPropertiesPluginData expectedAppValue = GlobalPropertiesTestPluginFactory
                .getStandardGlobalPropertiesPluginData(8368397106493368066L);

        GlobalPropertiesPluginDataInput globalPropertiesPluginDataInput = translationSpec
                .convertAppObject(expectedAppValue);

        GlobalPropertiesPluginData actualAppValue = translationSpec.convertInputObject(globalPropertiesPluginDataInput);

        assertEquals(expectedAppValue, actualAppValue);
        assertEquals(expectedAppValue.toString(), actualAppValue.toString());

        // preconditions
        // version is not supported
        ContractException contractException = assertThrows(ContractException.class, () -> {
            translationSpec.convertInputObject(GlobalPropertiesPluginDataInput.newBuilder().setVersion("badversion").build());
        });

        assertEquals(CoreTranslationError.UNSUPPORTED_VERSION, contractException.getErrorType());
    }

    @Test
    @UnitTestMethod(target = GlobalPropertiesPluginDataTranslationSpec.class, name = "getAppObjectClass", args = {})
    public void testGetAppObjectClass() {
        GlobalPropertiesPluginDataTranslationSpec translationSpec = new GlobalPropertiesPluginDataTranslationSpec();

        assertEquals(GlobalPropertiesPluginData.class, translationSpec.getAppObjectClass());
    }

    @Test
    @UnitTestMethod(target = GlobalPropertiesPluginDataTranslationSpec.class, name = "getInputObjectClass", args = {})
    public void testGetInputObjectClass() {
        GlobalPropertiesPluginDataTranslationSpec translationSpec = new GlobalPropertiesPluginDataTranslationSpec();

        assertEquals(GlobalPropertiesPluginDataInput.class, translationSpec.getInputObjectClass());
    }

}
