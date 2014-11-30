package org.societies.groups.event;

import org.societies.groups.member.Member;

/**
 * Represents a MemberJoinEvent
 */
public class MemberJoinEvent extends MemberEvent {

    public MemberJoinEvent(Member member) {
        super(member);
    }
}
