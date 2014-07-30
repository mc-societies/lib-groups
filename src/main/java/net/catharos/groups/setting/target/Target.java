package net.catharos.groups.setting.target;

import net.catharos.lib.core.uuid.UUIDGen;

import java.util.UUID;

/**
 *
 */
public interface Target {


    public static final Target NO_TARGET = new Target() {

        private final UUID DEFAULT_TARGET_UUID = UUIDGen.generateType1UUID();

        @Override
        public UUID getUUID() {
            return DEFAULT_TARGET_UUID;
        }
    };


    UUID getUUID();

}
