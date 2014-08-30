package net.catharos.groups;

import com.google.common.base.Objects;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import org.jetbrains.annotations.Nullable;

/**
 * Default implementation for a Relation
 */
public class DefaultRelation implements Relation {

    private final Group source;
    private final Group target;

    public DefaultRelation(Group source) {this(source, null);}

    @AssistedInject
    public DefaultRelation(@Assisted("source") Group source, @Assisted("target") Group target) {
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

    @Override
    public int getColumns() {
        return 2;
    }

    @Override
    public String getColumn(int column) {
        switch (column) {
            case 0:
                return source.getName();
            case 1:
                return target.getName();
            default:
                throw new AssertionError();
        }
    }
}
