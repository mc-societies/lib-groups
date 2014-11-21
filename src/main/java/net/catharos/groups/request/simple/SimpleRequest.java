package net.catharos.groups.request.simple;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import gnu.trove.map.hash.THashMap;
import gnu.trove.procedure.TObjectProcedure;
import net.catharos.groups.request.*;
import net.catharos.lib.core.util.CastSafe;
import org.apache.commons.lang3.mutable.MutableInt;
import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;

import static com.google.common.collect.Iterables.getLast;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.sort;

/**
 * Represents a SimpleRequest
 */
public class SimpleRequest implements Request<Choices> {

    private final Participant supplier;
    private final RequestMessenger<Choices> messenger;
    private final Involved recipients;
    private final SettableFuture<DefaultRequestResult<Choices>> future = SettableFuture.create();

    private final THashMap<Participant, Choices> results = new THashMap<Participant, Choices>();

    private final DateTime created;

    private boolean started = false;


    @AssistedInject
    public SimpleRequest(@Assisted Participant supplier,
                         @Assisted Involved recipients,
                         @Assisted RequestMessenger<Choices> messenger) {
        this.supplier = supplier;
        this.messenger = messenger;
        this.recipients = recipients;
        this.created = DateTime.now();
    }

    @Override
    public boolean isInvolved(Participant participant) {
        return recipients.isInvolved(participant);
    }

    @Override
    public Collection<? extends Participant> getRecipients() {
        return recipients.getRecipients();
    }

    @Nullable
    public Choice getState(Participant participant) {
        if (!isInvolved(participant)) {
            return null;
        }

        Choice choice = results.get(participant);
        return choice == null ? Choices.ABSTAIN : choice;
    }

    @Override
    public void start() {
        supplier.setSuppliedRequest(this);

        for (Participant participant : recipients.getRecipients()) {
            participant.setReceivedRequest(this);
        }

        started = true;

        messenger.start(this);
    }

    @Override
    public void vote(Participant participant, Choices choice) {
        if (future.isDone()) {
            throw new RuntimeException("Request already done.");
        }

        if (isInvolved(participant)) {
            for (Participant otherParticipant : getRecipients()) {
                if (otherParticipant.equals(participant)) {
                    continue;
                }

                messenger.voted(this, choice, participant);
            }

            results.put(participant, choice);
            check();
        }
    }

    @Override
    public void cancel() {
        if (future.isDone()) {
            throw new RuntimeException("Request already done.");
        }

        future.set(new DefaultRequestResult<Choices>(Choices.CANCELLED, this));
        messenger.cancelled(this);
    }

    private void done() {
        supplier.setSuppliedRequest(null);

        for (Participant participant : getRecipients()) {
            participant.setReceivedRequest(null);
        }
    }

    public Map<Choices, Number> stats() {
        return CastSafe.toGeneric(internalStats());
    }

    private Map<Choices, MutableInt> internalStats() {
        final THashMap<Choices, MutableInt> stats = new THashMap<Choices, MutableInt>();

        results.forEachValue(new TObjectProcedure<Choices>() {
            @Override
            public boolean execute(Choices object) {
                MutableInt count = stats.get(object);

                if (count == null) {
                    stats.put(object, count = new MutableInt());
                }

                count.increment();
                return true;
            }
        });

        return stats;
    }

    private void check() {
        if (isPending()) {
            return;
        }

        ArrayList<Map.Entry<Choices, MutableInt>> sorted = newArrayList(internalStats().entrySet());
        sort(sorted, new EntryValueComparator<Choices, MutableInt>());

        //Check for duplicates
        MutableInt last = null;
        for (Map.Entry<Choices, MutableInt> entry : sorted) {
            if (entry.getValue().equals(last)) {
                future.setException(new RequestFailedException());
                return;
            }

            last = entry.getValue();
        }

        if (sorted.isEmpty()) {
            return;
        }

        Choices choice = getLast(sorted).getKey();
        future.set(new DefaultRequestResult<Choices>(choice, this));
        messenger.end(this, choice);
    }

    @Override
    public boolean isPending() {
        return results.size() < recipients.getRecipients().size();
    }

    @Override
    public Participant getSupplier() {
        return supplier;
    }

    @Override
    public DateTime getDateCreated() {
        return created;
    }

    @Override
    public ListenableFuture<DefaultRequestResult<Choices>> result() {

        if (!started) {
            throw new RuntimeException("Request not started yet!");
        }

        return future;
    }

    private static class EntryValueComparator<K, V extends Comparable<V>> implements Comparator<Map.Entry<K, V>> {
        @Override
        public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
            return o1.getValue().compareTo(o2.getValue());
        }
    }
}
