package net.catharos.groups;

import net.catharos.groups.request.Participant;

import java.util.Collection;
import java.util.UUID;

/**
 *
 */
public interface Member extends Participant {

    UUID getUUID();

    Collection<Group> getGroups();

    Group getGroup();

    void addGroup(Group group);

    boolean hasGroup(Group group);
}
