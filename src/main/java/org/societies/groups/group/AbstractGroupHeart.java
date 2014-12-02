package org.societies.groups.group;

import com.google.common.base.Objects;
import gnu.trove.set.hash.THashSet;
import net.catharos.lib.core.util.CastSafe;
import org.joda.time.DateTime;
import org.societies.groups.DefaultRelation;
import org.societies.groups.Relation;
import org.societies.groups.member.Member;
import org.societies.groups.rank.Rank;
import org.societies.groups.setting.Setting;
import org.societies.groups.setting.subject.Subject;

import java.util.*;

import static java.util.Collections.unmodifiableCollection;

/**
 * Represents a AbstractGroupHeart
 */
public abstract class AbstractGroupHeart implements GroupHeart {

    private boolean completed = true;

    private final Map<String, Setting<Boolean>> rules;
    private final Setting<Boolean> verifySetting;
    private final Setting<Relation> relationSetting;
    private final Subject subject;


    protected AbstractGroupHeart(Map<String, Setting<Boolean>> rules, Setting<Boolean> verifySetting, Setting<Relation> relationSetting, Subject subject) {
        this.rules = rules;
        this.verifySetting = verifySetting;
        this.relationSetting = relationSetting;
        this.subject = subject;
    }

    @Override
    public Rank getRank(String name) {
        for (Rank rank : getRanks()) {
            if (rank.getName().equals(name)) {
                return rank;
            }
        }
        return null;
    }

    @Override
    public Rank getRank(UUID uuid) {
        for (Rank rank : getRanks()) {
            if (rank.getUUID().equals(uuid)) {
                return rank;
            }
        }
        return null;
    }

    @Override
    public Collection<Rank> getRanks(String rule) {
        THashSet<Rank> ranks = new THashSet<Rank>();

        for (Rank rank : getRanks()) {
            if (rank.hasRule(rule)) {
                ranks.add(rank);
            }
        }

        return ranks;
    }

    @Override
    public Set<Member> getMembers(String rule) {
        Setting<Boolean> setting = rules.get(rule);

        if (setting == null) {
            return Collections.emptySet();
        }

        return getMembers(setting);
    }

    @Override
    public int size() {
        return getMembers().size();
    }

    @Override
    public Set<Member> getMembers(Setting<Boolean> setting) {
        THashSet<Member> out = new THashSet<Member>();

        for (Member member : getMembers()) {
            if (member.getBooleanRankValue(setting)) {
                out.add(member);
            }
        }

        return out;
    }

    @Override
    public boolean isMember(Member member) {
        return getMembers().contains(member);
    }

    @Override
    public Relation getRelation(GroupHeart anotherGroup) {
        Relation relation = subject.get(relationSetting, anotherGroup);
        return relation == null ? DefaultRelation.unknownRelation(subject.getUUID()) : relation;
    }

    @Override
    public Collection<Relation> getRelations() {
        return unmodifiableCollection(CastSafe
                .<Collection<Relation>>toGeneric(subject.getSettings().row(relationSetting).values()));
    }

    @Override
    public Collection<Relation> getRelations(Relation.Type type) {
        THashSet<Relation> relations = new THashSet<Relation>();
        for (Object obj : subject.getSettings().row(relationSetting).values()) {
            Relation relation = (Relation) obj;
            relations.add(relation);

        }
        return relations;
    }

    @Override
    public void setRelation(GroupHeart target, Relation relation) {
        setRelation(target, relation, true);
    }

    @Override
    public void setRelation(GroupHeart target, Relation relation, boolean override) {
        if (!relation.contains(subject.getUUID())) {
            throw new IllegalArgumentException("Relation must contain a target or source identical to \"this\"!");
        }

        if (!relation.contains(target)) {
            throw new IllegalArgumentException("Relation contains wrong target!");
        }

        if (!override && !Objects.equal(relation, getRelation(target))) {
            throw new IllegalStateException("Relation already exists!");
        }

        if (target == this) {
            throw new IllegalStateException("Target and source mustn't be equal!");
        }


        subject.set(relationSetting, target, relation);


        if (!target.hasRelation(this)) {
            target.setRelation(this, relation, override);
        }
    }

    @Override
    public void removeRelation(GroupHeart anotherGroup) {
        if (hasRelation(anotherGroup)) {
            subject.remove(relationSetting, anotherGroup);
        }

        if (anotherGroup.hasRelation(this)) {
            anotherGroup.removeRelation(this);
        }
    }

    @Override
    public boolean hasRelation(GroupHeart anotherGroup) {
        Relation value = subject.get(relationSetting, anotherGroup);

        if (value == null) {
            return false;
        }

        UUID opposite = value.getOpposite(subject.getUUID());

        return opposite != null && opposite.equals(anotherGroup.getUUID());

    }

    @Override
    public DateTime getLastActive() {
        DateTime lastActive = null;

        for (Member member : getMembers()) {
            DateTime memberLastActive = member.getLastActive();
            if (lastActive == null || memberLastActive.isAfter(lastActive)) {
                lastActive = memberLastActive;
            }
        }

        if (lastActive == null) {
            return new DateTime(0);
        }

        return lastActive;
    }

    @Override
    public boolean isVerified() {
        return subject.getBoolean(verifySetting);
    }

    @Override
    public void verify(boolean newState) {
        subject.set(verifySetting, newState);
    }


    @Override
    public UUID getUUID() {
        return getHolder().getUUID();
    }

    @Override
    public void unlink() {
        unlink(false);
    }

    @Override
    public boolean linked() {
        return completed;
    }

    public void unlink(boolean value) {
        this.completed = value;
    }

    @Override
    public void link() {
        unlink(true);
    }
}
