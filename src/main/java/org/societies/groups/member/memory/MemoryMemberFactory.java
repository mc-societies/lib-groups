package org.societies.groups.member.memory;

import com.google.inject.Inject;
import net.catharos.lib.core.command.sender.Sender;
import org.societies.groups.ExtensionFactory;
import org.societies.groups.ExtensionRoller;
import org.societies.groups.member.Member;
import org.societies.groups.member.MemberFactory;
import org.societies.groups.request.DefaultParticipant;

import java.util.Set;
import java.util.UUID;

/**
 * Represents a BukkitMemberFactory
 */
public class MemoryMemberFactory implements MemberFactory {

    private final ExtensionFactory<? extends Sender, UUID> senderFactory;
    private final ExtensionFactory<MemoryMemberHeart, Member> heartFactory;
    private final Set<ExtensionRoller<Member>> extensions;

    @Inject
    public MemoryMemberFactory(ExtensionFactory<Sender, UUID> senderFactory,
                               ExtensionFactory<MemoryMemberHeart, Member> heartFactory,
                               Set<ExtensionRoller<Member>> extensions) {
        this.senderFactory = senderFactory;
        this.heartFactory = heartFactory;
        this.extensions = extensions;
    }

    @Override
    public Member create(UUID uuid) {
        Member member = new Member(uuid);

        Sender sender = senderFactory.create(uuid);
        DefaultParticipant participant = new DefaultParticipant(sender);
        MemoryMemberHeart heart = heartFactory.create(member);

        member.setMemberHeart(heart);
        member.setParticipant(participant);
        member.setSender(sender);

        for (ExtensionRoller<Member> extension : extensions) {
            extension.roll(member);
        }

        return member;
    }
}
