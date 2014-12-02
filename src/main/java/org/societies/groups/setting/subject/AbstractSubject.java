package org.societies.groups.setting.subject;

import org.societies.groups.setting.Setting;
import org.societies.groups.setting.target.Target;

/**
 * Represents a DefaultSubject
 */
public abstract class AbstractSubject implements Subject {

    @Override
    public <V> void remove(Setting<V> setting) {
        remove(setting, this);
    }

    @Override
    public <V> void set(Setting<V> setting, V value) {
        set(setting, this, value);
    }

    @Override
    public <V> V get(Setting<V> setting) {
        return get(setting, this);
    }

    @Override
    public boolean getBoolean(Setting<Boolean> setting, Target target) {
        Boolean value = this.<Boolean>get(setting, target);
        return value != null && value;
    }

    @Override
    public int getInteger(Setting<Integer> setting, Target target) {
        Integer value = this.<Integer>get(setting, target);

        if (value == null) {
            return 0;
        }
        return value;
    }

    @Override
    public boolean getBoolean(Setting<Boolean> setting) {
        Boolean value = this.<Boolean>get(setting);
        return value != null && value;
    }

    @Override
    public int getInteger(Setting<Integer> setting) {
        Integer value = this.<Integer>get(setting);

        if (value == null) {
            return 0;
        }
        return value;
    }

    @Override
    public double getDouble(Setting<Double> setting, Target target) {
        Double value = this.<Double>get(setting, target);

        if (value == null) {
            return 0;
        }
        return value;
    }

    @Override
    public double getDouble(Setting<Double> setting) {
        Double value = this.<Double>get(setting);

        if (value == null) {
            return 0;
        }
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Target)) {
            return false;
        }

        Target that = (Target) o;

        return getUUID().equals(that.getUUID());
    }

    @Override
    public int hashCode() {
        return getUUID().hashCode();
    }
}
