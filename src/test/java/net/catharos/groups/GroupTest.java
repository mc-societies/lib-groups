package net.catharos.groups;

import net.catharos.groups.publisher.LastActivePublisher;
import net.catharos.groups.publisher.NamePublisher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class GroupTest {

    private Group a, b;
    @Mock
    private NamePublisher namePublisher;
    @Mock
    private LastActivePublisher lastActivePublisher;

    @Before
    public void setup() throws Exception {

        a = new DefaultGroup(UUID
                .randomUUID(), Group.NEW_GROUP_NAME, Group.NEW_GROUP_NAME, namePublisher, lastActivePublisher);
        b = new DefaultGroup(UUID
                .randomUUID(), Group.NEW_GROUP_NAME, Group.NEW_GROUP_NAME, namePublisher, lastActivePublisher);
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
