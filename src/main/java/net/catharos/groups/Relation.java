package net.catharos.groups;

import gnu.trove.map.hash.TByteObjectHashMap;
import net.catharos.lib.core.command.format.table.RowForwarder;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * A {@link net.catharos.groups.Relation} describes a relation between two groups. A source and a target.
 * Usually the source is the group which initiated this relation.
 */
public interface Relation extends RowForwarder {

    /**
     * @return The source
     */
    UUID getSource();

    /**
     * @return The target
     */
    UUID getTarget();

    Type getType();

    /**
     * @return The opposite group in this relation. If the source is specified the target will be returned. If the target
     * is specified the source will be returned.
     */
    @Nullable
    UUID getOpposite(UUID group);

    /**
     * @param group The group
     * @return Whether this group participates in this relation
     */
    boolean contains(UUID group);

    boolean contains(Group group);

    public static enum Type {
        ALLIED(1, "allies"),
        RIVALED(2, "rivals"),
        UNKNOWN(0, "unkown");

        private static final TByteObjectHashMap<Type> ids = new TByteObjectHashMap<Type>();

        static {
            for (Type type : Type.values()) {
                ids.put(type.getID(), type);
            }
        }

        private final byte id;
        private final String name;

        Type(int id, String name) {
            this.name = name;
            this.id = (byte) id;
        }

        public byte getID() {
            return id;
        }

        public static Type getType(byte id) {
            return ids.get(id);
        }

        public String getName() {
            return name;
        }
    }
}
