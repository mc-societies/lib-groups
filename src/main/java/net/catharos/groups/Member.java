package net.catharos.groups;

import java.util.Collection;
import java.util.UUID;

/**
 *
 */
public interface Member extends Inactivatable {

    UUID getUUID();


    Collection<Group> getGroups();

    Group getGroup();

    void addGroup(Group group);

    boolean hasGroup(Group group);
}
