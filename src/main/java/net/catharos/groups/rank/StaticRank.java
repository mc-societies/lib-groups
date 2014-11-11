package net.catharos.groups.rank;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import net.catharos.groups.Group;
import net.catharos.groups.setting.Setting;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;

/**
 * Represents a StaticAbstractRank
 */
//todo no need for dynamic settings
public class StaticRank extends AbstractRank {

    @Inject
    public StaticRank(@Assisted UUID uuid, @Assisted String name, @Assisted int priority, Map<String, Setting<Boolean>> rules) {
        super(uuid, name, priority, rules);
    }

    @Override
    public int getState() {
        return 0;
    }

    @Override
    public void setState(int state) {

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
}
