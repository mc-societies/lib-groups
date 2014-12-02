package org.societies.groups.group;

import com.google.inject.Inject;
import org.joda.time.DateTime;
import org.societies.groups.rank.RankFactory;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

/**
 * Represents a GroupGenerator
 */
public class RandomGroupGenerator {

    private final GroupFactory groupFactory;
    private final RankFactory rankFactory;

    @Inject
    public RandomGroupGenerator(GroupFactory groupFactory, RankFactory rankFactory) {
        this.groupFactory = groupFactory;
        this.rankFactory = rankFactory;
    }

    public Group generate() {
        Group group = groupFactory.create(randomAlphabetic(6), randomAlphabetic(3), DateTime.now());

        group.addRank(rankFactory.create(randomAlphabetic(6), 0, group));

        return group;
    }
}
