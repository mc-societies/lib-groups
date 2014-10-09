package net.catharos.groups.command;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import net.catharos.groups.Group;
import net.catharos.groups.GroupProvider;
import net.catharos.lib.core.command.CommandContext;
import net.catharos.lib.core.command.ParsingException;
import net.catharos.lib.core.command.parser.ArgumentParser;
import net.catharos.lib.core.i18n.Dictionary;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * Represents a GroupParser
 */
public class GroupParser implements ArgumentParser<Group> {

    private final GroupProvider provider;
    private final Dictionary<String> dictionary;

    @Inject
    public GroupParser(GroupProvider provider, Dictionary<String> dictionary) {
        this.provider = provider;
        this.dictionary = dictionary;
    }

    @NotNull
    @Override
    public Group parse(String input, CommandContext<?> ctx) throws ParsingException {
        try {
            Set<Group> groups = provider.getGroup(input).get();

            if (groups.isEmpty()) {
                throw new ParsingException(dictionary.getTranslation("target-society.not-found", (Object) input), ctx);
            }

            return Iterables.getOnlyElement(groups);
        } catch (InterruptedException e) {
            throw new ParsingException(e, dictionary.getTranslation("target-society.not-found", (Object) input), ctx);
        } catch (ExecutionException e) {
            throw new ParsingException(e, dictionary.getTranslation("target-society.not-found", (Object) input), ctx);
        }
    }
}
