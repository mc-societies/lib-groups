package net.catharos.groups;

import com.google.common.base.Objects;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a UnknownRelation
 */
public class DefaultRelation implements Relation {

    private final Group source;
    @Nullable
    private final Group target;

    public DefaultRelation(Group source) {this(source, null);}

    public DefaultRelation(Group source, @Nullable Group target) {
        this.source = source;
        this.target = target;
    }

    @Nullable
    @Override
    public Group getTarget() {
        return target;
    }

    @Override
    public Group getSource() {
        return source;
    }

    @Override
    @Nullable
    public Group getOpposite(Group group) {
        return group == getTarget() ? getSource() : group == getSource() ? getTarget() : null;
    }

    @Override
    public boolean contains(Group group) {
        return Objects.equal(group, source) || Objects.equal(group, target);
    }

    public static DefaultRelation unknownRelation(Group group) {
        return new DefaultRelation(group);
    }
}
