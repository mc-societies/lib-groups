package net.catharos.groups;

import net.catharos.groups.request.Participant;
import net.catharos.groups.setting.subject.Subject;
import net.catharos.lib.core.command.format.table.RowForwarder;
import net.catharos.lib.core.command.sender.Sender;

import java.util.UUID;

/**
 *
 */
public interface Member extends MemberHeart, Subject, Participant, Sender, Extensible, RowForwarder, Completable {

    @Override
    UUID getUUID();

    Sender getSender();
}
