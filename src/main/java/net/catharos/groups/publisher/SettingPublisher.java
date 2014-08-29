package net.catharos.groups.publisher;

import net.catharos.groups.setting.Setting;
import net.catharos.groups.setting.subject.Subject;
import net.catharos.groups.setting.target.Target;

/**
 * Represents a SettingPublisher
 */
public interface SettingPublisher {

    <V> void publish(Subject group, Target target, Setting<V> setting, V value);
}
