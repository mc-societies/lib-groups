package net.catharos.groups.event;

import net.catharos.groups.Member;

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
