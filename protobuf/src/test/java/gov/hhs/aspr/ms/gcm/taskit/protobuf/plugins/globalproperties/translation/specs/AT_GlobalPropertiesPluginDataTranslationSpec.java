package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.translation.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.gcm.simulation.plugins.globalproperties.datamanagers.GlobalPropertiesPluginData;
import gov.hhs.aspr.ms.gcm.simulation.plugins.globalproperties.testsupport.GlobalPropertiesTestPluginFactory;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.data.input.GlobalPropertiesPluginDataInput;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.translation.GlobalPropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.properties.translation.PropertiesTranslator;
import gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.reports.ReportsTranslator;
import gov.hhs.aspr.ms.taskit.core.engine.TaskitError;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufJsonTaskitEngine;
import gov.hhs.aspr.ms.taskit.protobuf.engine.ProtobufTaskitEngine;
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
    public void testTranslateObject() {

        ProtobufTaskitEngine protobufTaskitEngine = ProtobufJsonTaskitEngine.builder()
                .addTranslator(GlobalPropertiesTranslator.getTranslator())
                .addTranslator(PropertiesTranslator.getTranslator())
                .addTranslator(ReportsTranslator.getTranslator())
                .build();

        GlobalPropertiesPluginDataTranslationSpec translationSpec = new GlobalPropertiesPluginDataTranslationSpec();
        translationSpec.init(protobufTaskitEngine);

        GlobalPropertiesPluginData expectedAppValue = GlobalPropertiesTestPluginFactory
                .getStandardGlobalPropertiesPluginData(8368397106493368066L);

        GlobalPropertiesPluginDataInput globalPropertiesPluginDataInput = translationSpec
                .translateAppObject(expectedAppValue);

        GlobalPropertiesPluginData actualAppValue = translationSpec
                .translateInputObject(globalPropertiesPluginDataInput);

        assertEquals(expectedAppValue, actualAppValue);
        assertEquals(expectedAppValue.toString(), actualAppValue.toString());

        // preconditions
        // version is not supported
        ContractException contractException = assertThrows(ContractException.class, () -> {
            translationSpec.translateInputObject(
                    GlobalPropertiesPluginDataInput.newBuilder().setVersion("badversion").build());
        });

        assertEquals(TaskitError.UNSUPPORTED_VERSION, contractException.getErrorType());
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
