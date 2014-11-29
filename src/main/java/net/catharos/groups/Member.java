package net.catharos.groups;

import net.catharos.groups.request.Participant;
import net.catharos.groups.setting.subject.Subject;
import net.catharos.lib.core.command.format.table.RowForwarder;
import net.catharos.lib.core.command.sender.Sender;

import java.util.UUID;

/**
 *
 */
public interface Member extends Extensible<Member>, Subject, Participant, Sender, RowForwarder, Completable, MemberHeart {

    @Override
    UUID getUUID();

    Sender getSender();
}
