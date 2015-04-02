package org.societies.groups.dictionary;

import java.util.Locale;

/**
 * Represents a MutableDictionary
 */
public interface MutableDictionary<K> extends Dictionary<K> {

    void addTranslation(Locale locale, K key, String translation);

    void addTranslation(String locale, K key, String translation);
}
