package net.catharos.groups.command;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import net.catharos.groups.Group;
import net.catharos.groups.GroupProvider;
import net.catharos.lib.core.command.CommandContext;
import net.catharos.lib.core.command.ParsingException;
import net.catharos.lib.core.command.parser.ArgumentParser;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * Represents a GroupParser
 */
public class GroupParser implements ArgumentParser<Group> {

    private final GroupProvider provider;

    @Inject
    public GroupParser(GroupProvider provider) {this.provider = provider;}

    @NotNull
    @Override
    public Group parse(String input, CommandContext<?> ctx) throws ParsingException {
        try {
            Set<Group> groups = provider.getGroup(input).get();

            if (groups.isEmpty()) {
                throw new ParsingException("Not found!", ctx);
            }

            return Iterables.getOnlyElement(groups);
        } catch (InterruptedException e) {
            throw new ParsingException("Not found!", ctx);
        } catch (ExecutionException e) {
            throw new ParsingException(e, "Not found!", ctx);
        }
    }
}
