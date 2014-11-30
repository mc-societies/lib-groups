package org.societies.groups.rank;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import org.jetbrains.annotations.Nullable;
import org.societies.groups.group.Group;
import org.societies.groups.setting.Setting;

import java.util.Map;
import java.util.UUID;

/**
 * Represents a StaticAbstractRank
 */
//beautify no need for dynamic settings
public class StaticRank extends AbstractRank {

    @Inject
    public StaticRank(@Assisted UUID uuid, @Assisted String name, @Assisted int priority, Map<String, Setting<Boolean>> rules) {
        super(uuid, name, priority, rules);
    }

    @Nullable
    @Override
    public Group getGroup() {
        return null;
    }

    @Override
    public boolean isStatic() {
        return true;
    }

    @Override
    public boolean isCompleted() {
        return true;
    }

    @Override
    public void complete(boolean value) {

    }

    @Override
    public void complete() {

    }
}
