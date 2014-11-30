package org.societies.groups.event;

import org.societies.groups.group.Group;

/**
 * Represents a GroupTagEvent
 */
public class GroupTagEvent extends GroupEvent {
    public GroupTagEvent(Group group) {
        super(group);
    }
}
