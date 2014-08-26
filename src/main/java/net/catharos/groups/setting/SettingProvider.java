package net.catharos.groups.setting;

import org.jetbrains.annotations.Nullable;

/**
 * Represents a SettingProvider
 */
public interface SettingProvider {

    @Nullable
    Setting getSetting(int id);
}
