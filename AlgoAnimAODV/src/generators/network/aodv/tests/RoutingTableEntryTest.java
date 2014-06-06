package generators.network.aodv.tests;

import generators.network.aodv.RoutingTableEntry;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoutingTableEntryTest {

    private RoutingTableEntry entry;

    @Before
    public void setUp() throws Exception {
        entry = new RoutingTableEntry("A", 0, Integer.MAX_VALUE, "");
    }

    @Test
    public void testClone() throws Exception {
        RoutingTableEntry entryClone1 = entry.clone();

        assertNotEquals(entry, entryClone1);
        assertEquals(entry.getNextHop(), entryClone1.getNextHop());
        assertEquals(entry.getDestinationSequence(), entryClone1.getDestinationSequence());
        assertEquals(entry.getHopCount(), entryClone1.getHopCount());
        assertEquals(entry.getIdentifier(), entryClone1.getIdentifier());

        RoutingTableEntry entryClone2 = entry.clone();

        assertNotEquals(entryClone1, entryClone2);
        assertEquals(entryClone1.getNextHop(), entryClone2.getNextHop());
        assertEquals(entryClone1.getDestinationSequence(), entryClone2.getDestinationSequence());
        assertEquals(entryClone1.getHopCount(), entryClone2.getHopCount());
        assertEquals(entryClone1.getIdentifier(), entryClone2.getIdentifier());
    }
}