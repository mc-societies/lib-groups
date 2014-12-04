package org.societies.groups;

import com.google.inject.assistedinject.Assisted;
import org.societies.groups.group.Group;
import org.societies.groups.group.GroupHeart;

import java.util.UUID;

/**
 * Represents a RelationFactory
 */
public interface RelationFactory {

    Relation create(@Assisted("source") UUID source, @Assisted("target") UUID target, Relation.Type type);

    Relation create(@Assisted("source") GroupHeart source, @Assisted("target") GroupHeart target, Relation.Type type);
}
