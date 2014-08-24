package net.catharos.groups;

import net.catharos.groups.publisher.VoidPublisher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertTrue;

public class GroupTest {

    private Group a, b;

    @Before
    public void setup() throws Exception {
        a = new DefaultGroup(UUID.randomUUID(), Group.NEW_GROUP_NAME, Group.NEW_GROUP_NAME, new VoidPublisher<Group>(), new VoidPublisher<Group>());
        b = new DefaultGroup(UUID.randomUUID(), Group.NEW_GROUP_NAME, Group.NEW_GROUP_NAME, new VoidPublisher<Group>(), new VoidPublisher<Group>());
    }

    @Test
    public void testSetRelation() throws Exception {
        a.setRelation(new DefaultRelation(a, b));

        assertAToBRelation();
    }

    public void assertAToBRelation() {
        Assert.assertEquals(b, a.getRelation(b).getOpposite(a));
    }

    @Test(expected = AssertionError.class)
    public void testRemoveRelation() throws Exception {
        testSetRelation();

        a.removeRelation(b);
        assertAToBRelation();
    }

    @Test
    public void testAddSubGroup() throws Exception {
        a.addSubGroup(b);

        assertTrue(a.isParent(b));
    }
}
