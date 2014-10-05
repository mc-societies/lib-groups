package net.catharos.groups.request;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import gnu.trove.map.hash.THashMap;
import gnu.trove.procedure.TObjectProcedure;
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

    private final String name;
    private final Participant supplier;
    private final RequestMessenger<Choices> messenger;
    private final Involved receivers;
    private final SettableFuture<SimpleRequestResult<Choices>> future = SettableFuture.create();

    private final THashMap<Participant, Choices> results = new THashMap<Participant, Choices>();

    private final DateTime created;

    private boolean started = false;

    public SimpleRequest(String name, Participant supplier, RequestMessenger<Choices> messenger, Involved delegate) {
        this.name = name;
        this.supplier = supplier;
        this.messenger = messenger;
        this.receivers = delegate;
        this.created = DateTime.now();
    }

    @Override
    public boolean isInvolved(Participant participant) {
        return receivers.isInvolved(participant);
    }

    @Override
    public Collection<? extends Participant> getReceivers() {
        return receivers.getReceivers();
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
    public String getName() {
        return name;
    }

    @Override
    public void start() {
        supplier.setSuppliedRequest(this);

        for (Participant participant : receivers.getReceivers()) {
            participant.setReceivedRequest(this);
            messenger.start(this, participant);
        }

        started = true;
    }

    @Override
    public void vote(Participant participant, Choices choice) {
        if (future.isDone()) {
            throw new RuntimeException("Request already done.");
        }

        if (isInvolved(participant)) {
            results.put(participant, choice);
            check();
            messenger.voted(this, choice, participant);
        }
    }

    @Override
    public void cancel() {
        if (future.isDone()) {
            throw new RuntimeException("Request already done.");
        }

        future.set(new SimpleRequestResult<Choices>(Choices.CANCELLED, this));
        messenger.cancelled(this);
    }

    private void done() {
        supplier.setSuppliedRequest(null);

        for (Participant participant : getReceivers()) {
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
        future.set(new SimpleRequestResult<Choices>(choice, this));
        messenger.end(this);
    }

    @Override
    public boolean isPending() {
        return results.size() < receivers.getReceivers().size();
    }

    @Override
    public DateTime getDateCreated() {
        return created;
    }

    @Override
    public ListenableFuture<SimpleRequestResult<Choices>> result() {

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
