package net.catharos.groups.event;

import net.catharos.groups.Member;

/**
 * Represents a MemberJoinEvent
 */
public class MemberJoinEvent extends MemberEvent {

    public MemberJoinEvent(Member member) {
        super(member);
    }
}
