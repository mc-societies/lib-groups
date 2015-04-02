package org.societies.groups.member;

import order.Command;
import order.format.table.RowForwarder;
import order.sender.Sender;
import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;
import org.societies.groups.AbstractExtensible;
import org.societies.groups.Extensible;
import org.societies.groups.group.Group;
import org.societies.groups.group.GroupHeart;
import org.societies.groups.rank.Rank;
import org.societies.groups.request.Participant;
import org.societies.groups.request.Request;

import java.util.Set;
import java.util.UUID;

/**
 *
 */
public class Member extends AbstractExtensible implements MemberHeart, Participant, Sender, Extensible, RowForwarder {

    private final UUID uuid;


    private Participant participant;
    private Sender sender;
    private MemberHeart memberHeart;

    public Member(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        Group group = getGroup();
        return "DefaultMember{" +
                "uuid=" + uuid +
                ", group=" + (group != null ? group.getName() : null) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Member that = (Member) o;

        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    public void setMemberHeart(MemberHeart memberHeart) {
        this.memberHeart = memberHeart;
    }


    @Override
    public UUID getUUID() {
        return uuid;
    }

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

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
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
    public boolean hasGroup() {
        return memberHeart.hasGroup();
    }

    @Override
    public boolean hasRule(String rule) {
        return memberHeart.hasRule(rule);
    }

    @Override
    public void setGroup(@Nullable GroupHeart group) {
        memberHeart.setGroup(group);
    }

    @Override
    public boolean isGroup(GroupHeart group) {
        return memberHeart.isGroup(group);
    }

    @Override
    public boolean linked() {
        return memberHeart.linked();
    }

    @Override
    public void unlink() {
        memberHeart.unlink();
    }

    @Override
    public void link() {
        memberHeart.link();
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
    @Nullable
    public Group getGroup() {
        GroupHeart group = memberHeart.getGroup();
        if (group == null) {
            return null;
        }
        return group.getHolder();
    }

}
