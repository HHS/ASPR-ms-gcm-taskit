package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.resources.translation;

import gov.hhs.aspr.ms.taskit.core.translation.TranslatorId;

/**
 * TranslatorId for the Regions Translator
 */
public class ResourcesTranslatorId implements TranslatorId {
    public final static TranslatorId TRANSLATOR_ID = new ResourcesTranslatorId();

    private ResourcesTranslatorId() {
    }
}
