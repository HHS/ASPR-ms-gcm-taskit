package gov.hhs.aspr.ms.gcm.taskit.protobuf.nucleus.translation;

import gov.hhs.aspr.ms.taskit.core.translation.TranslatorId;

/**
 * TranslatorId for the Nucleus Translator
 */
public class NucleusTranslatorId implements TranslatorId {
    public final static TranslatorId TRANSLATOR_ID = new NucleusTranslatorId();

    private NucleusTranslatorId() {
    }
}
