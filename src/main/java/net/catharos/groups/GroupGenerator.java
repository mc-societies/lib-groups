package net.catharos.groups;

import com.google.inject.Inject;
import net.catharos.groups.rank.RankFactory;
import org.joda.time.DateTime;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

/**
 * Represents a GroupGenerator
 */
public class GroupGenerator {

    private final GroupFactory groupFactory;
    private final RankFactory rankFactory;

    @Inject
    public GroupGenerator(GroupFactory groupFactory, RankFactory rankFactory) {
        this.groupFactory = groupFactory;
        this.rankFactory = rankFactory;
    }

    public Group generate() {
        Group group = groupFactory.create(randomAlphabetic(6), randomAlphabetic(3), DateTime.now());

        group.addRank(rankFactory.create(randomAlphabetic(6), 0));

        return group;
    }
}
