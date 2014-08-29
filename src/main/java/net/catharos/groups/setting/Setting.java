package net.catharos.groups.setting;

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

    public abstract V convert(byte[] value);

    public abstract byte[] convert(@Nullable V value);

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
