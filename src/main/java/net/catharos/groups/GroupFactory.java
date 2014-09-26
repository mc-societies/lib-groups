package net.catharos.groups;

import com.google.inject.assistedinject.Assisted;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Used to create new instances {@link net.catharos.groups.Group}
 */
public interface GroupFactory {

    /**
     * Creates a group by name. The uuid will be generated.
     *
     * @param name The name
     * @return A brand new group
     */
    Group create(@Assisted("name") String name, @Assisted("tag") String tag);

    /**
     * Creates a group by name and uuid.
     *
     * @param uuid The uuid
     * @param name The name
     * @return A brand new group
     */
    Group create(UUID uuid, @Assisted("name") String name, @Assisted("tag") String tag);

    /**
     * Creates a group by name and uuid and specifies a parent.
     *
     * @param uuid   The uuid
     * @param name   The name
     * @param parent The parent
     * @return A brand new group
     */
    Group create(UUID uuid, @Assisted("name") String name, @Assisted("tag") String tag, @Nullable Group parent);
}
