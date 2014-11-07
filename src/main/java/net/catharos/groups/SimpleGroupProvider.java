package net.catharos.groups;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Represents a SimpleGroupProvider
 */
public class SimpleGroupProvider implements Provider<Group> {

    private final GroupFactory groupFactory;

    @Inject
    public SimpleGroupProvider(GroupFactory groupFactory) {this.groupFactory = groupFactory;}

    @Override
    public Group get() {
        return groupFactory.create(Group.NEW_GROUP_NAME, Group.NEW_GROUP_TAG);
    }
}
