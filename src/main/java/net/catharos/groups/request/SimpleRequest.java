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
public class SimpleRequest implements Request<SimpleRequest.Choices> {

    private final Involved involved;
    private final SettableFuture<SimpleRequestResult<SimpleRequest.Choices>> future = SettableFuture.create();

    private final THashMap<Participant, SimpleRequest.Choices> results = new THashMap<Participant, SimpleRequest.Choices>();

    private final DateTime created;

    public SimpleRequest(Involved delegate) {
        this.involved = delegate;
        this.created = DateTime.now();
    }

    @Override
    public boolean isInvolved(Participant participant) {
        return involved.isInvolved(participant);
    }

    @Override
    public Collection<? extends Participant> getInvolved() {
        return involved.getInvolved();
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
    public void vote(Participant participant, SimpleRequest.Choices choice) {
        if (isInvolved(participant)) {
            results.put(participant, choice);
            check();
        }
    }

    public Map<SimpleRequest.Choices, Number> stats() {
        return CastSafe.toGeneric(internalStats());
    }

    private Map<SimpleRequest.Choices, MutableInt> internalStats() {
        final THashMap<SimpleRequest.Choices, MutableInt> stats = new THashMap<SimpleRequest.Choices, MutableInt>();

        results.forEachValue(new TObjectProcedure<SimpleRequest.Choices>() {
            @Override
            public boolean execute(SimpleRequest.Choices object) {
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

        ArrayList<Map.Entry<SimpleRequest.Choices, MutableInt>> sorted = newArrayList(internalStats().entrySet());
        sort(sorted, new EntryValueComparator<SimpleRequest.Choices, MutableInt>());

        //Check for duplicates
        MutableInt last = null;
        for (Map.Entry<SimpleRequest.Choices, MutableInt> entry : sorted) {
            if (entry.getValue().equals(last)) {
                future.setException(new RequestFailedException());
                return;
            }

            last = entry.getValue();
        }

        if (sorted.isEmpty()) {
            return;
        }

        SimpleRequest.Choices choice = getLast(sorted).getKey();
        future.set(new SimpleRequestResult<SimpleRequest.Choices>(choice, this));
    }

    @Override
    public boolean isPending() {
        return results.size() < involved.getInvolved().size();
    }

    @Override
    public DateTime getDateCreated() {
        return created;
    }

    @Override
    public ListenableFuture<SimpleRequestResult<SimpleRequest.Choices>> result() {
        return future;
    }

    public static enum Choices implements Choice {

        ACCEPT,
        DENY,
        ABSTAIN

    }

    private static class EntryValueComparator<K, V extends Comparable<V>> implements Comparator<Map.Entry<K, V>> {
        @Override
        public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
            return o1.getValue().compareTo(o2.getValue());
        }
    }
}
