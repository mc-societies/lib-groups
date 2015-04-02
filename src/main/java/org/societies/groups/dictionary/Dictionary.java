package org.societies.groups.dictionary;

import java.util.Locale;

/**
 *
 */
public interface Dictionary<K> {

    String getTranslation(K key);

    String getTranslation(K key, String locale);

    String getTranslation(K key, Locale locale);


    String getCleanTranslation(K key);

    String getCleanTranslation(K key, String locale);

    String getCleanTranslation(K key, Locale locale);

    //todo getTranslation for parameters

    String getTranslation(K key, Object... args);

    String getTranslation(K key, String locale, Object... args);

    String getTranslation(K key, Locale locale, Object... args);
}
