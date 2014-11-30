package org.societies.groups.setting.subject;


import org.societies.groups.publisher.SettingPublisher;
import org.societies.groups.setting.Setting;
import org.societies.groups.setting.target.Target;

/**
 * Represents a PermissionBase
 */
public abstract class AbstractPublishingSubject extends AbstractSubject implements Subject {

    private final SettingPublisher settingPublisher;

    protected AbstractPublishingSubject(SettingPublisher settingPublisher) {
        this.settingPublisher = settingPublisher;
    }

    @Override
    public <V> void set(Setting<V> setting, Target target, V value) {
        settingPublisher.publish(this, target, setting, value);
    }

    @Override
    public <V> void remove(Setting<V> setting, Target target) {
        settingPublisher.publish(this, target, setting, null);
    }
}
