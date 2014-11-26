package net.catharos.groups.event;

import net.catharos.groups.Group;

/**
 * Represents a GroupEvent
 */
public class GroupEvent implements Event {

    private final Group group;

    public GroupEvent(Group group) {
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }
}
