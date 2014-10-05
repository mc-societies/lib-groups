package net.catharos.groups.request;

import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Set;
import java.util.concurrent.ExecutionException;

@RunWith(JUnit4.class)
public class SimpleRequestTest {

    private final Participant[] elements = new Participant[]{new SimpleParticipant(), new SimpleParticipant(), new SimpleParticipant(), new SimpleParticipant()};

    private final Set<Participant> participants = Sets.newHashSet(elements);


    @Test
    public void testVoting() throws ExecutionException, InterruptedException, RequestFailedException {
        Involved involved = new Involved() {
            @Override
            public boolean isInvolved(Participant participant) {
                return getReceivers().contains(participant);
            }

            @Override
            public Set<Participant> getReceivers() {
                return participants;
            }
        };


        SimpleRequest request = new SimpleRequest("", new SimpleParticipant(), new SimpleRequestMessenger<Choices>(), involved);

        request.start();

        request.vote(elements[0], Choices.ACCEPT);
        request.vote(elements[1], Choices.ACCEPT);
        request.vote(elements[2], Choices.ACCEPT);
        request.vote(elements[3], Choices.DENY);

        Assert.assertTrue(!request.isPending());

        System.out.println(request.result().get().getChoice());
    }
}
