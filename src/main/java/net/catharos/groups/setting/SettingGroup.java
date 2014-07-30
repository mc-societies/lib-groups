package net.catharos.groups.setting;

import gnu.trove.set.hash.THashSet;

/**
 * Represents a PermissionGroup
 */
public class SettingGroup extends Setting {

    private final THashSet<Setting> settings = new THashSet<Setting>();

    protected SettingGroup(int id, String description) {
        super(id, description);
    }

    public void addSetting(Setting setting) {
        settings.add(setting);
    }

    public void removeSetting(Setting setting) {
        settings.remove(setting);
    }

    @Override
    public boolean implies(Setting setting) {
        return settings.contains(setting);
    }
}
