package org.societies.groups.command;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import order.CommandContext;
import order.ParsingException;
import order.parser.ArgumentParser;
import org.societies.groups.member.Member;
import org.societies.groups.member.MemberProvider;

/**
 * Represents a MemberParser
 */
public class MemberParser implements ArgumentParser<Member> {

    private final MemberProvider provider;

    @Inject
    public MemberParser(MemberProvider provider) {
        this.provider = provider;
    }

    @Override
    public Member parse(String input, CommandContext<?> ctx) throws ParsingException {
        Optional<Member> member = provider.getMember(input);

        if (!member.isPresent()) {
            throw new ParsingException("target-member.not-found", ctx);
        }

        return member.get();
    }
}
