package org.societies.groups.setting.subject;

import org.societies.groups.Completable;
import org.societies.groups.publisher.SettingPublisher;
import org.societies.groups.setting.Setting;
import org.societies.groups.setting.target.Target;

/**
 * Represents a DefaultSubject
 */
public abstract class CompletablePublishingSubject extends DefaultSubject {

    private final SettingPublisher settingPublisher;
    private final Completable completable;

    protected CompletablePublishingSubject(SettingPublisher settingPublisher, Completable completable) {
        this.settingPublisher = settingPublisher;
        this.completable = completable;
    }

    @Override
    public <V> void set(Setting<V> setting, Target target, V value) {
        super.set(setting, target, value);

        if (completable.isCompleted()) {
            settingPublisher.publish(this, target, setting, value);
        }
    }

    @Override
    public <V> void remove(Setting<V> setting, Target target) {
        super.remove(setting, target);

        if (completable.isCompleted()) {
            settingPublisher.publish(this, target, setting, null);
        }
    }
}
