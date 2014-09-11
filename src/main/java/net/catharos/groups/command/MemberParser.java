package net.catharos.groups.command;

import com.google.inject.Inject;
import net.catharos.groups.Member;
import net.catharos.groups.MemberProvider;
import net.catharos.lib.core.command.CommandContext;
import net.catharos.lib.core.command.ParsingException;
import net.catharos.lib.core.command.parser.ArgumentParser;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutionException;

/**
 * Represents a MemberParser
 */
public class MemberParser<M extends Member> implements ArgumentParser<M> {

    private final MemberProvider<M> provider;

    @Inject
    public MemberParser(MemberProvider<M> provider) {this.provider = provider;}

    @NotNull
    @Override
    public M parse(String input, CommandContext<?> ctx) throws ParsingException {
        try {
            M member = provider.getMember(input).get();

            if (member == null) {
                throw new ParsingException("target-member.not-found", ctx);
            }

            return member;
        } catch (InterruptedException e) {
            throw new ParsingException("target-member.not-found", ctx);
        } catch (ExecutionException e) {
            throw new ParsingException(e, "target-member.not-found", ctx);
        }
    }
}
