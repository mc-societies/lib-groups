package net.catharos.groups;

import java.util.Set;

/**
 * Represents a Members
 */
public final class Members {

    private Members() {
    }

    public static int onlineMembers(Set<Member> members) {
        int online = 0;

        for (Member member : members) {
            if (member.isAvailable()) {
                online++;
            }
        }

        return online;
    }
}
