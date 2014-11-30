package org.societies.groups.publisher;

import org.jetbrains.annotations.Nullable;
import org.societies.groups.setting.Setting;
import org.societies.groups.setting.subject.Subject;
import org.societies.groups.setting.target.Target;

/**
 * Represents a SettingPublisher
 */
public interface SettingPublisher {

    <V> void publish(Subject subject, Target target, Setting<V> setting, @Nullable V value);
}
