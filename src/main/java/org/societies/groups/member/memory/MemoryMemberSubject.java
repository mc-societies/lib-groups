package org.societies.groups.member.memory;

import org.societies.groups.member.MemberPublisher;
import org.societies.groups.setting.Setting;
import org.societies.groups.setting.subject.DefaultSubject;
import org.societies.groups.setting.target.Target;

/**
 * Represents a PublishingSubject
 */
public class MemoryMemberSubject extends DefaultSubject {

    private final MemoryMemberHeart heart;
    private final MemberPublisher memberPublisher;

    public MemoryMemberSubject(MemoryMemberHeart heart, MemberPublisher memberPublisher) {
        super(heart.getHolder().getUUID());
        this.heart = heart;
        this.memberPublisher = memberPublisher;
    }

    @Override
    public <V> void set(Setting<V> setting, Target target, V value) {
        super.set(setting, target, value);

        if (heart.linked()) {
            memberPublisher.publish(heart.getHolder());
        }
    }

    @Override
    public <V> void remove(Setting<V> setting, Target target) {
        super.remove(setting, target);

        if (heart.linked()) {
            memberPublisher.publish(heart.getHolder());
        }
    }
}
