package net.catharos.groups;

import com.google.common.base.Objects;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Default implementation for a Relation
 */
public class DefaultRelation implements Relation {

    private final UUID source;
    private final UUID target;

    public DefaultRelation(UUID source) {this(source, null);}

    @AssistedInject
    public DefaultRelation(@Assisted("source") UUID source, @Assisted("target") UUID target) {
        this.source = source;
        this.target = target;
    }

    @AssistedInject
    public DefaultRelation(@Assisted("source") Group source, @Assisted("target") Group target) {
        this(source.getUUID(), target.getUUID());
    }

    @Nullable
    @Override
    public UUID getTarget() {
        return target;
    }

    @Override
    public UUID getSource() {
        return source;
    }

    @Override
    @Nullable
    public UUID getOpposite(UUID group) {
        return group == getTarget() ? getSource() : group == getSource() ? getTarget() : null;
    }

    @Override
    public boolean contains(UUID group) {
        return Objects.equal(group, source) || Objects.equal(group, target);
    }

    @Override
    public boolean contains(Group group) {
        return contains(group.getUUID());
    }

    public static DefaultRelation unknownRelation(UUID group) {
        return new DefaultRelation(group);
    }

    public static DefaultRelation unknownRelation(Group group) {
        return unknownRelation(group.getUUID());
    }


    @Override
    public int getColumns() {
        return 2;
    }

    @Override
    public String getColumn(int column) {
        switch (column) {
            case 0:
                return source.toString();
            case 1:
                return target.toString();
            default:
                throw new AssertionError();
        }
    }
}
