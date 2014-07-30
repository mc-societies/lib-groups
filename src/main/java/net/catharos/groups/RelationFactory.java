package net.catharos.groups;

import com.google.inject.assistedinject.Assisted;

/**
 * Represents a RelationFactory
 */
public interface RelationFactory {

    Relation create(@Assisted("source") Group source, @Assisted("target") Group target);
}
