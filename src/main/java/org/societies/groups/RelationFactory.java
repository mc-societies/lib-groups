package org.societies.groups;

import com.google.inject.assistedinject.Assisted;
import org.societies.groups.group.Group;

import java.util.UUID;

/**
 * Represents a RelationFactory
 */
public interface RelationFactory {

    Relation create(@Assisted("source") UUID source, @Assisted("target") UUID target, Relation.Type type);

    Relation create(@Assisted("source") Group source, @Assisted("target") Group target, Relation.Type type);
}