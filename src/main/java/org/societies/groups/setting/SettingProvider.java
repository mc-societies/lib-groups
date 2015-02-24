package org.societies.groups.setting;

import org.jetbrains.annotations.Nullable;

/**
 * Represents a SettingProvider
 */
public interface SettingProvider {

    @Nullable
    Setting getSetting(String id);
}
