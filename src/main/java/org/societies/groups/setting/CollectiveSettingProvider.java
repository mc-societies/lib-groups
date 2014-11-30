package org.societies.groups.setting;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.Map;

/**
 * Represents a CollectiveSettingProvider
 */
public class CollectiveSettingProvider implements SettingProvider {

    private final TIntObjectHashMap<Setting> settings = new TIntObjectHashMap<Setting>();

    @Inject(optional = true)
    private void load(@Named("settings") Map<Integer, Setting> settings) {
        this.settings.putAll(settings);
    }

    @Override
    public Setting getSetting(int id) {
        return settings.get(id);
    }
}
