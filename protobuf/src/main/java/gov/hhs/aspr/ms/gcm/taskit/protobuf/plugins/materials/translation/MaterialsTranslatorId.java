package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.materials.translation;

import gov.hhs.aspr.ms.taskit.core.translation.TranslatorId;

/**
 * TranslatorId for the Materials Translator
 */
public class MaterialsTranslatorId implements TranslatorId {
    public final static TranslatorId TRANSLATOR_ID = new MaterialsTranslatorId();

    private MaterialsTranslatorId() {
    }
}
