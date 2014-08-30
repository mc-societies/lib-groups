package net.catharos.groups.setting.subject;

import net.catharos.groups.publisher.SettingPublisher;
import net.catharos.groups.setting.Setting;
import net.catharos.groups.setting.target.Target;

/**
 * Represents a AbstractPublishingSubject
 */
public abstract class AbstractPublishingSubject extends AbstractSubject {

    private final SettingPublisher settingPublisher;

    protected AbstractPublishingSubject(SettingPublisher settingPublisher) {this.settingPublisher = settingPublisher;}

    @Override
    public <V> void set(Setting<V> setting, Target target, V value) {
        super.set(setting, target, value);

        if (isPrepared()) {
            settingPublisher.publish(this, target, setting, value);
        }
    }

    @Override
    public <V> void remove(Setting<V> setting, Target target) {
        super.remove(setting, target);

        if (isPrepared()) {
            settingPublisher.publish(this, target, setting, null);
        }
    }

    protected abstract boolean isPrepared();

}
