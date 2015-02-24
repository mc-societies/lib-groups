package org.societies.groups.setting;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import gnu.trove.map.hash.THashMap;

import java.util.Map;

/**
 * Represents a CollectiveSettingProvider
 */
public class CollectiveSettingProvider implements SettingProvider {

    private final THashMap<String, Setting> settings = new THashMap<String, Setting>();

    @Inject(optional = true)
    private void load(@Named("settings") Map<String, Setting> settings) {
        this.settings.putAll(settings);
    }

    @Override
    public Setting getSetting(String id) {
        return settings.get(id);
    }
}
