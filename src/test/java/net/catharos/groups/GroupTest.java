package net.catharos.groups;

import net.catharos.groups.publisher.*;
import net.catharos.groups.setting.Setting;
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
    private SettingPublisher settingPublisher;
    @Mock
    private GroupStatePublisher groupStatePublisher;
    @Mock
    private GroupRankPublisher groupRankPublisher;
    @Mock
    private RankDropPublisher rankDropPublisher;
    @Mock
    private Setting<Relation> relationSetting;


    @Before
    public void setup() throws Exception {

        a = new DefaultGroup(UUID
                .randomUUID(), Group.NEW_GROUP_NAME, Group.NEW_GROUP_NAME, namePublisher, settingPublisher, groupStatePublisher, groupRankPublisher, rankDropPublisher, relationSetting);
        b = new DefaultGroup(UUID
                .randomUUID(), Group.NEW_GROUP_NAME, Group.NEW_GROUP_NAME, namePublisher, settingPublisher, groupStatePublisher, groupRankPublisher, rankDropPublisher, relationSetting);
    }

    @Test
    public void testSetRelation() throws Exception {
        a.setRelation(b, new DefaultRelation(a, b));

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
