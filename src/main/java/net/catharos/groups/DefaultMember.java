package net.catharos.groups;

import gnu.trove.set.hash.THashSet;
import net.catharos.groups.publisher.Publisher;
import net.catharos.groups.rank.Rank;
import net.catharos.groups.request.Request;
import net.catharos.groups.setting.Setting;
import net.catharos.groups.setting.target.Target;
import net.catharos.groups.setting.value.SettingValue;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

/**
 * Default implementation for a Member
 */
public abstract class DefaultMember implements Member {

    private final UUID uuid;

    @Nullable
    private Group group;
    private THashSet<Rank> ranks = new THashSet<Rank>();
    private Request activeRequest;

    private final Publisher<Member> societyPublisher;

    public DefaultMember(UUID uuid,
                         Publisher<Member> societyPublisher) {
        this.uuid = uuid;
        this.societyPublisher = societyPublisher;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public Set<Rank> getRanks() {
        return ranks;
    }

    @Override
    public void addRank(Rank rank) {
        this.ranks.add(rank);
    }

    @Override
    public SettingValue get(Setting setting, Target target) {
        //fixme
        return null;
    }

    @Override
    @Nullable
    public Group getGroup() {
        return group;
    }

    @Override
    public void setGroup(@Nullable Group group) {
        this.group = group;

        societyPublisher.update(this);

        if (group != null && !group.isMember(this)) {
            group.addMember(this);
        }
    }

    @Override
    public boolean isGroup(Group group) {
        return this.group != null && this.group.equals(group);
    }

    @Override
    public String toString() {
        return "DefaultMember{" +
                "uuid=" + uuid +
                ", group=" + group +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DefaultMember that = (DefaultMember) o;

        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Nullable
    @Override
    public Request getActiveRequest() {
        return activeRequest;
    }

    @Override
    public void setActiveRequest(Request activeRequest) {
        this.activeRequest = activeRequest;
    }

    @Override
    public boolean clearRequest() {
        boolean value = activeRequest != null;
        activeRequest = null;
        return value;
    }


}
