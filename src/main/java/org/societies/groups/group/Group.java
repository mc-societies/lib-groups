package org.societies.groups.group;

import com.google.common.collect.Table;
import net.catharos.lib.core.command.format.table.RowForwarder;
import org.joda.time.DateTime;
import org.societies.groups.AbstractExtensible;
import org.societies.groups.Extensible;
import org.societies.groups.Relation;
import org.societies.groups.member.Member;
import org.societies.groups.rank.Rank;
import org.societies.groups.request.Involved;
import org.societies.groups.request.Participant;
import org.societies.groups.setting.Setting;
import org.societies.groups.setting.subject.Subject;
import org.societies.groups.setting.target.Target;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

/**
 * This represents a generic group. A group can be a party, where {@link org.societies.groups.member.Member}s participate, a town with citizens or a guild.
 * <p/>
 * A group can have a parent and child groups.
 * <p/>
 * {@link org.societies.groups.Relation}s are bidirectional between groups. This means they are mirrored to each other.
 */
public class Group extends AbstractExtensible implements Extensible, GroupHeart, Subject, RowForwarder, Involved {

    private final UUID uuid;

    private Subject subject;
    private GroupHeart groupHeart;

    public Group(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public boolean linked() {
        return groupHeart.linked();
    }

    @Override
    public void unlink() {
        groupHeart.unlink();
    }

    @Override
    public void link() {
        groupHeart.link();
    }

    public GroupHeart getHeart() {
        return groupHeart;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setGroupHeart(GroupHeart groupHeart) {
        this.groupHeart = groupHeart;
    }

    @Override
    public String toString() {
        return "DefaultGroup{" +
                "uuid=" + getUUID() +
                ", relations=" + getRelations() +
                ", ranks=" + getRanks() +
                ", members=" + getMembers() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group that = (Group) o;

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

    @Override
    public boolean isInvolved(Participant participant) {
        Set<? extends Participant> members = getMembers();
        return members.contains(participant);
    }

    @Override
    public Collection<? extends Participant> getRecipients() {
        return getMembers();
    }

    public Subject getSubject() {
        return subject;
    }

    //================================================================================
    // Delegates
    //================================================================================

    @Override
    public <V> void set(Setting<V> setting, Target target, V value) {
        subject.set(setting, target, value);
    }

    @Override
    public <V> void set(Setting<V> setting, V value) {
        subject.set(setting, value);
    }

    @Override
    public <V> void remove(Setting<V> setting, Target target) {
        subject.remove(setting, target);
    }

    @Override
    public <V> void remove(Setting<V> setting) {
        subject.remove(setting);
    }

    @Override
    public <V> V get(Setting<V> setting, Target target) {
        return subject.get(setting, target);
    }

    @Override
    public boolean getBoolean(Setting<Boolean> setting, Target target) {
        return subject.getBoolean(setting, target);
    }

    @Override
    public int getInteger(Setting<Integer> setting, Target target) {
        return subject.getInteger(setting, target);
    }

    @Override
    public double getDouble(Setting<Double> setting, Target target) {
        return subject.getDouble(setting, target);
    }

    @Override
    public <V> V get(Setting<V> setting) {
        return subject.get(setting);
    }

    @Override
    public double getDouble(Setting<Double> setting) {
        return subject.getDouble(setting);
    }

    @Override
    public boolean getBoolean(Setting<Boolean> setting) {
        return subject.getBoolean(setting);
    }

    @Override
    public int getInteger(Setting<Integer> setting) {
        return subject.getInteger(setting);
    }

    @Override
    public Table<Setting, Target, Object> getSettings() {
        return subject.getSettings();
    }

    @Override
    public String getName() {
        return groupHeart.getName();
    }

    @Override
    public String getTag() {
        return groupHeart.getTag();
    }

    @Override
    public void setTag(String tag) {
        groupHeart.setTag(tag);
    }

    @Override
    public void setName(String name) {
        groupHeart.setName(name);
    }

    @Override
    public DateTime getLastActive() {
        return groupHeart.getLastActive();
    }

    @Override
    public DateTime getCreated() {
        return groupHeart.getCreated();
    }

    @Override
    public void setCreated(DateTime created) {
        groupHeart.setCreated(created);
    }

    @Override
    public Relation getRelation(GroupHeart anotherGroup) {
        return groupHeart.getRelation(anotherGroup);
    }

    @Override
    public Collection<Relation> getRelations() {
        return groupHeart.getRelations();
    }

    @Override
    public Collection<Relation> getRelations(Relation.Type type) {
        return groupHeart.getRelations(type);
    }

    @Override
    public void setRelation(GroupHeart target, Relation relation) {
        groupHeart.setRelation(target, relation);
    }

    @Override
    public void setRelation(GroupHeart target, Relation relation, boolean override) {
        groupHeart.setRelation(target, relation, override);
    }

    @Override
    public void removeRelation(GroupHeart anotherGroup) {
        groupHeart.removeRelation(anotherGroup);
    }

    @Override
    public boolean hasRelation(GroupHeart anotherGroup) {
        return groupHeart.hasRelation(anotherGroup);
    }

    @Override
    public void addRank(Rank rank) {
        groupHeart.addRank(rank);
    }

    @Override
    public void removeRank(Rank rank) {
        groupHeart.removeRank(rank);
    }

    @Override
    public Rank getRank(String name) {
        return groupHeart.getRank(name);
    }

    @Override
    public Rank getRank(UUID uuid) {
        return groupHeart.getRank(uuid);
    }

    @Override
    public Collection<Rank> getRanks() {
        return groupHeart.getRanks();
    }

    @Override
    public Collection<Rank> getRanks(String rule) {
        return groupHeart.getRanks(rule);
    }

    @Override
    public Set<Member> getMembers() {
        return groupHeart.getMembers();
    }

    @Override
    public int size() {
        return groupHeart.size();
    }

    @Override
    public Set<Member> getMembers(Setting<Boolean> setting) {
        return groupHeart.getMembers(setting);
    }

    @Override
    public Set<Member> getMembers(String rule) {
        return groupHeart.getMembers(rule);
    }

    @Override
    public boolean isMember(Member member) {
        return groupHeart.isMember(member);
    }

    @Override
    public void addMember(Member member) {
        groupHeart.addMember(member);
    }

    @Override
    public void removeMember(Member member) {
        groupHeart.removeMember(member);
    }

    @Override
    public boolean isVerified() {
        return groupHeart.isVerified();
    }

    @Override
    public void verify(boolean newState) {
        groupHeart.verify(newState);
    }

    @Override
    public void send(String message) {
        groupHeart.send(message);
    }

    @Override
    public void send(String message, Object... obj) {
        groupHeart.send(message, obj);
    }

    @Override
    public Group getHolder() {
        return groupHeart.getHolder();
    }
}
