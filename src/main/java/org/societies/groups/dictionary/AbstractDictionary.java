package org.societies.groups.dictionary;

import com.google.inject.name.Named;

import java.util.Locale;

/**
 * Represents a AbstractDictionary
 */
public abstract class AbstractDictionary<K> implements Dictionary<K> {
    private final Locale defaultLocale;

    public AbstractDictionary(@Named("default-locale") Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    public String getDefaultLocale() {
        return defaultLocale.getISO3Language();
    }

    @Override
    public String getTranslation(K key, Locale locale, Object... args) {
        return getTranslation(key, locale.getISO3Language(), args);
    }

    @Override
    public String getCleanTranslation(K key, Locale locale) {
        return getCleanTranslation(key, locale.getISO3Language());
    }

    @Override
    public String getTranslation(K key, Locale locale) {
        return getTranslation(key, locale.getISO3Language());
    }
}
