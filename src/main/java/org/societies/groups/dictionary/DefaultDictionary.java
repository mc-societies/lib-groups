package org.societies.groups.dictionary;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.text.MessageFormat;
import java.util.Locale;

public class DefaultDictionary<K> extends AbstractDictionary<K> implements MutableDictionary<K> {

    private final Table<String, K, String> table;

    @Inject
    public DefaultDictionary(@Named("default-locale") Locale defaultLocale) {
        super(defaultLocale);

        this.table = HashBasedTable.create();
    }

    @Override
    public String getTranslation(K key) {
        return getCleanTranslation(key);
    }

    @Override
    public String getTranslation(K key, String locale) {
        return getCleanTranslation(key, locale);
    }


    @Override
    public String getCleanTranslation(K key) {
        return getCleanTranslation(key, getDefaultLocale());
    }

    @Override
    public String getCleanTranslation(K key, String locale) {
        if (key == null) {
            return null;
        }

        String translation = table.get(locale, key);

        if (translation == null) {
            if (locale.equals(getDefaultLocale())) {
                return key.toString();
            }

            getTranslation(key, getDefaultLocale());
        }

        return translation;
    }

    @Override
    public String getTranslation(K key, Object... args) {
        return getTranslation(key, getDefaultLocale(), args);
    }

    @Override
    public String getTranslation(K key, String locale, Object... args) {
        String translated = getTranslation(key, locale);

        if (translated == null) {
            if (key == null) {
                return "null";
            }
            return key.toString();
        }

        return MessageFormat.format(translated, args);
    }

    @Override
    public void addTranslation(Locale locale, K key, String translation) {
        addTranslation(locale.getISO3Language(), key, translation);
    }

    @Override
    public void addTranslation(String locale, K key, String translation) {
        this.table.put(locale, key, translation);
    }
}
