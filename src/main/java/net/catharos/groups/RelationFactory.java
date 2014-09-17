package net.catharos.groups;

import com.google.inject.assistedinject.Assisted;

import java.util.UUID;

/**
 * Represents a RelationFactory
 */
public interface RelationFactory {

    Relation create(@Assisted("source") UUID source, @Assisted("target") UUID target);

    Relation create(@Assisted("source") Group source, @Assisted("target") Group target);
}
