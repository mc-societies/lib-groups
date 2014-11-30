package org.societies.groups.group;

import net.catharos.lib.core.command.format.table.RowForwarder;
import org.societies.groups.Completable;
import org.societies.groups.request.Involved;
import org.societies.groups.setting.subject.Subject;

import java.util.UUID;

/**
 * This represents a generic group. A group can be a party, where {@link org.societies.groups.member.Member}s participate, a town with citizens or a guild.
 * <p/>
 * A group can have a parent and child groups.
 * <p/>
 * {@link org.societies.groups.Relation}s are bidirectional between groups. This means they are mirrored to each other.
 */
public interface Group extends GroupHeart, Subject, RowForwarder, Involved, Completable {

    /**
     * The default name for new groups
     */
    String NEW_GROUP_NAME = "new-group";

    String NEW_GROUP_TAG = "ng";

    /**
     * @return The uuid
     */
    @Override
    UUID getUUID();
}
