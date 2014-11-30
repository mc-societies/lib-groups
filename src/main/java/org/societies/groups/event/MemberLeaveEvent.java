package org.societies.groups.event;

import org.societies.groups.group.Group;
import org.societies.groups.member.Member;

/**
 * Represents a MemberLeaveEvent
 */
public class MemberLeaveEvent extends MemberEvent {

    private final Group group;

    public MemberLeaveEvent(Member member, Group group) {
        super(member);
        this.group = group;
    }

    public Group getPreviousGroup() {
        return group;
    }
}
