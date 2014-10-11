package net.catharos.groups;

import com.google.inject.Inject;
import net.catharos.lib.core.uuid.TimeUUIDProvider;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;

import static org.junit.Assert.assertTrue;

@RunWith(JukitoRunner.class)
public class GroupTest {


    public static class Module extends JukitoModule {
        @Override
        protected void configureTest() {
            bind(UUID.class).toProvider(TimeUUIDProvider.class);
            bind(Group.class).to(DefaultGroup.class);
        }
    }

    @Inject
    private Group a, b;

    @Test
    public void testSetRelation() throws Exception {
        a.setRelation(b, new DefaultRelation(a, b, Relation.Type.ALLIED));

        assertAToBRelation();
    }

    public void assertAToBRelation() {
        Assert.assertEquals(b.getUUID(), a.getRelation(b).getOpposite(a.getUUID()));
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
