package net.catharos.groups.event;

import net.catharos.groups.Group;
import net.catharos.groups.Member;

/**
 * Represents a MemberLeaveEvent
 */
public class MemberLeaveEvent extends MemberEvent{

    private final Group group;

    public MemberLeaveEvent(Member member, Group group) {
        super(member);
        this.group = group;
    }

    public Group getPreviousGroup() {
        return group;
    }
}
