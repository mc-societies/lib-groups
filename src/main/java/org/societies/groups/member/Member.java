package org.societies.groups.member;

import net.catharos.lib.core.command.format.table.RowForwarder;
import net.catharos.lib.core.command.sender.Sender;
import org.societies.groups.Extensible;
import org.societies.groups.group.Group;
import org.societies.groups.request.Participant;
import org.societies.groups.setting.subject.Subject;

import java.util.UUID;

/**
 *
 */
public interface Member extends MemberHeart, Subject, Participant, Sender, Extensible, RowForwarder {

    @Override
    UUID getUUID();

    Sender getSender();

    @Override
    Group getGroup();
}
