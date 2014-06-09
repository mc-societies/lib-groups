package net.catharos.groups.command;

import com.google.inject.Inject;
import net.catharos.groups.Group;
import net.catharos.groups.GroupProvider;
import net.catharos.lib.core.command.ParsingException;
import net.catharos.lib.core.command.parser.ArgumentParser;
import org.jetbrains.annotations.NotNull;

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
    public Group parse(String input) throws ParsingException {
        try {
            return (Group) provider.getGroup(input).get().toArray()[0];
        } catch (InterruptedException e) {
            throw new ParsingException("Not found!");
        } catch (ExecutionException e) {
            throw new ParsingException(e, "Not found!");
        }
    }
}
