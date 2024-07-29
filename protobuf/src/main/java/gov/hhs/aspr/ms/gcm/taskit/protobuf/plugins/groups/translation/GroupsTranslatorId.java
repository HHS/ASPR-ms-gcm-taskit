package gov.hhs.aspr.ms.gcm.taskit.protobuf.plugins.groups.translation;

import gov.hhs.aspr.ms.taskit.core.translation.TranslatorId;

/**
 * TranslatorId for the Groups Translator
 */
public class GroupsTranslatorId implements TranslatorId {
    public final static TranslatorId TRANSLATOR_ID = new GroupsTranslatorId();

    private GroupsTranslatorId() {
    }
}
