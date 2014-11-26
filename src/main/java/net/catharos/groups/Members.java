package net.catharos.groups;

import gnu.trove.set.hash.THashSet;

import java.util.Set;

/**
 * Represents a Members
 */
public final class Members {

    private Members() {
    }

    public static int countOnline(Set<Member> members) {
        int online = 0;

        for (Member member : members) {
            if (member.isAvailable()) {
                online++;
            }
        }

        return online;
    }

    public static Set<Member> onlineMembers(Set<Member> members) {
        Set<Member> ret = new THashSet<Member>();

        for (Member member : members) {
            if (member.isAvailable()) {
                ret.add(member);
            }
        }

        return ret;
    }
}
