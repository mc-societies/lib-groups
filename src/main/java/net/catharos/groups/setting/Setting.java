package net.catharos.groups.setting;

import net.catharos.groups.setting.subject.Subject;
import net.catharos.groups.setting.target.Target;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a Setting
 */
public abstract class Setting<V> {

    private final int id;

    public Setting(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public abstract V convert(Subject subject, Target target, byte[] value) throws SettingException;

    public abstract byte[] convert(Subject subject, Target target, @Nullable V value) throws SettingException;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;


        Setting that = (Setting) o;

        return getID() == that.getID();
    }

    @Override
    public int hashCode() {
        return getID();
    }

//    public static class Boolean extends Setting<DefaultSettingValue> {
//
//        public Boolean(int id) {
//            super(id);
//        }
//
//        @Override
//        public DefaultSettingValue convert(byte[] value) {
//            return new DefaultSettingValue(value);
//        }
//    }
}
