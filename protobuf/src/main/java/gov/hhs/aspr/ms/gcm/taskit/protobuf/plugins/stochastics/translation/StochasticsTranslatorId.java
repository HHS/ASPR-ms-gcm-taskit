package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.stochastics.translation;

import gov.hhs.aspr.ms.taskit.core.translation.TranslatorId;

/**
 * TranslatorId for the Stochastics Translator
 */
public final class StochasticsTranslatorId implements TranslatorId {
    public final static TranslatorId TRANSLATOR_ID = new StochasticsTranslatorId();

    private StochasticsTranslatorId() {
    }
}
