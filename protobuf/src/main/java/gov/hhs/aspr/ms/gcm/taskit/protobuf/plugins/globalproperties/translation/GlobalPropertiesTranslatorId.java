package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.globalproperties.translation;

import gov.hhs.aspr.ms.taskit.core.translation.TranslatorId;

/**
 * TranslatorId for the GlobalProperties Translator
 */
public final class GlobalPropertiesTranslatorId implements TranslatorId {
    public final static TranslatorId TRANSLATOR_ID = new GlobalPropertiesTranslatorId();

    private GlobalPropertiesTranslatorId() {
    }
}
