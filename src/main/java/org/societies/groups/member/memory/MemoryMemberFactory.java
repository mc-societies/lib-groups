package org.societies.groups.member.memory;

import com.google.inject.Inject;
import net.catharos.lib.core.command.sender.Sender;
import org.societies.groups.ExtensionFactory;
import org.societies.groups.ExtensionRoller;
import org.societies.groups.member.Member;
import org.societies.groups.member.MemberComposite;
import org.societies.groups.member.MemberFactory;
import org.societies.groups.member.MemberPublisher;
import org.societies.groups.request.DefaultParticipant;

import java.util.Set;
import java.util.UUID;

/**
 * Represents a BukkitMemberFactory
 */
public class MemoryMemberFactory implements MemberFactory {

    private final ExtensionFactory<? extends Sender, UUID> senderFactory;
    private final MemberPublisher memberPublisher;
    private final ExtensionFactory<MemoryMemberHeart, Member> heartFactory;
    private final Set<ExtensionRoller> extensions;

    @Inject
    public MemoryMemberFactory(ExtensionFactory<Sender, UUID> senderFactory,
                               MemberPublisher memberPublisher,
                               ExtensionFactory<MemoryMemberHeart, Member> heartFactory,
                               Set<ExtensionRoller> extensions) {
        this.senderFactory = senderFactory;
        this.memberPublisher = memberPublisher;
        this.heartFactory = heartFactory;
        this.extensions = extensions;
    }

    @Override
    public Member create(UUID uuid) {
        MemberComposite member = new MemberComposite(uuid);

        Sender sender = senderFactory.create(uuid);
        DefaultParticipant participant = new DefaultParticipant(sender);
        MemoryMemberHeart heart = heartFactory.create(member);
        MemoryMemberSubject subject = new MemoryMemberSubject(heart, memberPublisher);

        member.setMemberHeart(heart);
        member.setSubject(subject);
        member.setParticipant(participant);
        member.setSender(sender);

        for (ExtensionRoller extension : extensions) {
            extension.roll(member);
        }

        return member;
    }
}
