package org.societies.groups.group.memory;

import org.societies.groups.group.GroupPublisher;
import org.societies.groups.setting.Setting;
import org.societies.groups.setting.subject.DefaultSubject;
import org.societies.groups.setting.target.Target;

/**
 * Represents a PublishingSubject
 */
public class MemoryGroupSubject extends DefaultSubject {

    private final MemoryGroupHeart heart;
    private final GroupPublisher groupPublisher;

    public MemoryGroupSubject(MemoryGroupHeart heart, GroupPublisher groupPublisher) {
        super(heart.getUUID());
        this.heart = heart;
        this.groupPublisher = groupPublisher;
    }

    @Override
    public <V> void set(Setting<V> setting, Target target, V value) {
        super.set(setting, target, value);

        if (heart.linked()) {
            groupPublisher.publish(heart.getHolder());
        }
    }

    @Override
    public <V> void remove(Setting<V> setting, Target target) {
        super.remove(setting, target);

        if (heart.linked()) {
            groupPublisher.publish(heart.getHolder());
        }
    }
}
