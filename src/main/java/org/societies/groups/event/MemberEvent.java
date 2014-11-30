package org.societies.groups.event;

import org.societies.groups.member.Member;

/**
 * Represents a MemberEvent
 */
public class MemberEvent implements Event {

    private final Member member;

    public MemberEvent(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return member;
    }
}
