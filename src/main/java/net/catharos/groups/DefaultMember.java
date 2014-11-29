package net.catharos.groups;

import com.google.common.collect.Table;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import gnu.trove.map.hash.THashMap;
import net.catharos.groups.event.Event;
import net.catharos.groups.event.EventController;
import net.catharos.groups.publisher.MemberCreatedPublisher;
import net.catharos.groups.publisher.MemberGroupPublisher;
import net.catharos.groups.publisher.MemberLastActivePublisher;
import net.catharos.groups.publisher.MemberRankPublisher;
import net.catharos.groups.rank.Rank;
import net.catharos.groups.request.Participant;
import net.catharos.groups.request.Request;
import net.catharos.groups.setting.Setting;
import net.catharos.groups.setting.subject.Subject;
import net.catharos.groups.setting.target.Target;
import net.catharos.lib.core.command.Command;
import net.catharos.lib.core.command.sender.Sender;
import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;

import java.util.Set;
import java.util.UUID;

/**
 * Represents a MemoryMember
 */
public class DefaultMember implements Member, Extensible {

    private final UUID uuid;

    private boolean completed = true;

    private final THashMap<Class<?>, Object> extensions = new THashMap<Class<?>, Object>();

    private final Subject subject;
    private final Participant participant;
    private final Sender sender;
    private MemberHeart memberHeart;

    public DefaultMember(UUID uuid, Subject subject, Participant participant, Sender sender) {
        this.uuid = uuid;
        this.subject = subject;
        this.participant = participant;
        this.sender = sender;
    }

    @Override
    public <E> E add(E extension) {
        return (E) extensions.put(extension.getClass(), extension);
    }

    @Override
    public <E> E add(Class<? extends E> clazz, E extension) {
        return (E) extensions.put(clazz, extension);
    }

    @Override
    public <E> E get(Class<? extends E> extensionClass) {
        return (E) extensions.get(extensionClass);
    }

    @Override
    public <E> boolean has(Class<? extends E> extensionClass) {
        return extensions.containsKey(extensionClass);
    }

    @Override
    public <E> boolean has(E extension) {
        return has(extension.getClass());
    }

    @Override
    public <E> E remove(Class<? extends E> extensionClass) {
        return (E) extensions.remove(extensionClass);
    }

    @Override
    public <E> E remove(E extension) {
        return (E) extensions.remove(extension.getClass());
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public Sender getSender() {
        return sender;
    }

    @Override
    public String getColumn(int column) {
        return getName();
    }

    @Override
    public int getColumns() {
        return 1;
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    @Override
    public void complete(boolean value) {
        this.completed = value;
    }

    @Override
    public void complete() {
        complete(true);
    }

    //================================================================================
    // Delegations
    //================================================================================


    //================================================================================
    // Sender
    //================================================================================

    @Override
    public void send(String message) {
        sender.send(message);
    }

    @Override
    public void send(String message, Object... args) {
        sender.send(message, args);
    }

    @Override
    public void send(StringBuilder message) {
        sender.send(message);
    }

    @Override
    public boolean hasPermission(Command command) {
        return sender.hasPermission(command);
    }

    @Override
    public boolean hasPermission(String permission) {
        return sender.hasPermission(permission);
    }

    @Override
    public String getName() {
        return sender.getName();
    }

    //================================================================================
    // Subject
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

    //================================================================================
    //  Participant
    //================================================================================

    @Override
    @Nullable
    public Request getReceivedRequest() {
        return participant.getReceivedRequest();
    }

    @Override
    public void setReceivedRequest(Request activeRequest) {
        participant.setReceivedRequest(activeRequest);
    }

    @Override
    public void clearReceivedRequest() {
        participant.clearReceivedRequest();
    }

    @Override
    @Nullable
    public Request getSuppliedRequest() {
        return participant.getSuppliedRequest();
    }

    @Override
    public void setSuppliedRequest(@Nullable Request suppliedRequest) {
        participant.setSuppliedRequest(suppliedRequest);
    }

    @Override
    public void clearSuppliedRequest() {
        participant.clearSuppliedRequest();
    }

    //================================================================================
    // Member
    //================================================================================

    @Override
    public Set<Rank> getRanks() {
        return memberHeart.getRanks();
    }

    @Override
    public void addRank(Rank rank) {
        memberHeart.addRank(rank);
    }

    @Override
    public boolean hasRank(Rank rank) {
        return memberHeart.hasRank(rank);
    }

    @Override
    @Nullable
    public Group getGroup() {
        return memberHeart.getGroup();
    }

    @Override
    public boolean hasGroup() {
        return memberHeart.hasGroup();
    }

    @Override
    public boolean hasRule(String rule) {
        return memberHeart.hasRule(rule);
    }

    @Override
    public void setGroup(@Nullable Group group) {
        memberHeart.setGroup(group);
    }

    @Override
    public boolean isGroup(Group group) {
        return memberHeart.isGroup(group);
    }

    @Override
    public boolean removeRank(Rank rank) {
        return memberHeart.removeRank(rank);
    }

    @Override
    public Rank getRank() {
        return memberHeart.getRank();
    }

    @Override
    public DateTime getLastActive() {
        return memberHeart.getLastActive();
    }

    @Override
    public void activate() {
        memberHeart.activate();
    }

    @Override
    public void setLastActive(DateTime lastActive) {
        memberHeart.setLastActive(lastActive);
    }

    @Override
    public DateTime getCreated() {
        return memberHeart.getCreated();
    }

    @Override
    public void setCreated(DateTime created) {
        memberHeart.setCreated(created);
    }

    @Override
    public <V> V getRankValue(Setting<V> setting) {
        return memberHeart.getRankValue(setting);
    }

    @Override
    public boolean getBooleanRankValue(Setting<Boolean> setting) {
        return memberHeart.getBooleanRankValue(setting);
    }


    //================================================================================
    // Others
    //================================================================================

    @Override
    public String toString() {
        return "DefaultMember{" +
                "uuid=" + uuid +
                ", group=" + (getGroup() != null ? getGroup().getName() : null) +
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

    public void setMemberHeart(MemberHeart memberHeart) {
        this.memberHeart = memberHeart;
    }

    public static final class Statics {
        private final MemberGroupPublisher groupPublisher;
        private final MemberRankPublisher memberRankPublisher;
        private final MemberLastActivePublisher lastActivePublisher;
        private final MemberCreatedPublisher createdPublisher;

        private final EventController eventController;
        private final Rank defaultRank;

        @Inject
        public Statics(MemberGroupPublisher groupPublisher,
                       MemberRankPublisher memberRankPublisher,
                       MemberLastActivePublisher lastActivePublisher,
                       MemberCreatedPublisher createdPublisher,
                       EventController eventController,
                       @Named("default-rank") Rank defaultRank) {
            this.groupPublisher = groupPublisher;
            this.memberRankPublisher = memberRankPublisher;
            this.lastActivePublisher = lastActivePublisher;
            this.createdPublisher = createdPublisher;
            this.eventController = eventController;
            this.defaultRank = defaultRank;
        }

        public ListenableFuture publishGroup(Member member, Group group) {
            return groupPublisher.publishGroup(member, group);
        }

        public ListenableFuture publishCreated(Member member, DateTime created) {
            return createdPublisher.publishCreated(member, created);
        }

        public ListenableFuture publishRank(Member member, Rank rank) {
            return memberRankPublisher.publishRank(member, rank);
        }

        public ListenableFuture dropRank(Member member, Rank rank) {
            return memberRankPublisher.dropRank(member, rank);
        }

        public ListenableFuture publishLastActive(Member member, DateTime date) {
            return lastActivePublisher.publishLastActive(member, date);
        }

        public Rank getDefaultRank() {
            return defaultRank;
        }

        public void publish(Event event) {
            eventController.publish(event);
        }
    }
}
