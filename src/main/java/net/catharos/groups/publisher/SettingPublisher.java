package net.catharos.groups.publisher;

import net.catharos.groups.setting.Setting;
import net.catharos.groups.setting.subject.Subject;
import net.catharos.groups.setting.target.Target;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a SettingPublisher
 */
public interface SettingPublisher {

    <V> void publish(Subject subject, Target target, Setting<V> setting, @Nullable V value);
}
