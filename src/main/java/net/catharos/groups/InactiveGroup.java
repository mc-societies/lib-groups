package net.catharos.groups;

import net.catharos.groups.rank.Rank;
import net.catharos.groups.setting.Setting;
import net.catharos.groups.setting.target.Target;
import net.catharos.groups.setting.value.SettingValue;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Represents a InactiveGroup
 */
public class InactiveGroup implements Group {

    private final UUID uuid;
    private final String name;

    public InactiveGroup(UUID uuid, String name) {this.uuid = uuid;
        this.name = name;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Relation getRelation(Group anotherGroup) {
        throw new InactiveException(this);
    }

    @Override
    public void setRelation(Relation relation) {
        throw new InactiveException(this);
    }

    @Override
    public void setRelation(Relation relation, boolean override) {
        throw new InactiveException(this);
    }

    @Override
    public void setRelation(Group target, Relation relation, boolean override) {
        throw new InactiveException(this);
    }

    @Override
    public void removeRelation(Group anotherGroup) {
        throw new InactiveException(this);
    }

    @Override
    public boolean hasRelation(Group anotherGroup) {
        throw new InactiveException(this);
    }

    @Override
    public Collection<Rank> getRanks() {
        throw new InactiveException(this);
    }

    @Nullable
    @Override
    public Group getParent() {
        throw new InactiveException(this);
    }

    @Override
    public void setParent(@Nullable Group group) {
        throw new InactiveException(this);
    }

    @Override
    public boolean hasParent() {
        throw new InactiveException(this);
    }

    @Override
    public Collection<Group> getSubGroups() {
        throw new InactiveException(this);
    }

    @Override
    public void addSubGroup(Group group) {
        throw new InactiveException(this);
    }

    @Override
    public void removeSubGroup(Group group) {
        throw new InactiveException(this);
    }

    @Override
    public boolean hasSubGroup(Group group) {
        throw new InactiveException(this);
    }

    @Override
    public boolean isParent(Group group) {
        throw new InactiveException(this);
    }

    @Override
    public List<Member> getMembers() {
        throw new InactiveException(this);
    }

    @Override
    public boolean isMember(Member participant) {
        throw new InactiveException(this);
    }

    @Override
    public void addMember(Member member) {
        throw new InactiveException(this);
    }

    @Override
    public void set(Setting setting, Target target, SettingValue value) {
        throw new InactiveException(this);
    }

    @Override
    public SettingValue get(Setting setting, Target target) {
        throw new InactiveException(this);
    }

    @Override
    public String toString() {
        return "InactiveGroup{" +
                "uuid=" + uuid +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InactiveGroup that = (InactiveGroup) o;

        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public int getColumns() {
        return 1;
    }

    @Override
    public String getColumn(int column) {
        return getName();
    }
}
