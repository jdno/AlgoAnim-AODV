package generators.network.aodv.tests;

import algoanim.animalscript.AnimalScript;
import algoanim.primitives.generators.Language;
import algoanim.util.Coordinates;
import generators.network.aodv.AODVMessage;
import generators.network.aodv.AODVNode;
import generators.network.aodv.RoutingTableEntry;
import generators.network.aodv.guielements.GUIController;
import generators.network.aodv.guielements.GeometryToolBox;
import generators.network.aodv.guielements.InfoTable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AODVNodeTest {

    private AODVNode nodeA;
    private AODVNode nodeB;
    private AODVNode nodeC;
    private AODVNode nodeD;

    @Before
    public void setUp() throws Exception {
        nodeA = new AODVNode("A");
        nodeB = new AODVNode("B");
        nodeC = new AODVNode("C");
        nodeD = new AODVNode("D");
        
        nodeA.addNeighbor(nodeB);
        nodeA.addNeighbor(nodeC);
        nodeB.addNeighbor(nodeA);
        nodeB.addNeighbor(nodeD);
        nodeC.addNeighbor(nodeA);
        nodeC.addNeighbor(nodeD);
        nodeD.addNeighbor(nodeB);
        nodeD.addNeighbor(nodeC);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testConstructor() {
        assertEquals("A", nodeA.getNodeIdentifier());
    }

    @Test
    public void testAddNeighbor() {
        assertEquals(2, nodeA.getRoutingTable().size());

        assertEquals(nodeA.getRoutingTable().get(0).getIdentifier(), nodeB.getNodeIdentifier());
        assertEquals(nodeA.getRoutingTable().get(1).getIdentifier(), nodeC.getNodeIdentifier());

        assertEquals(1, nodeA.getRoutingTable().get(0).getHopCount());
        assertEquals(nodeA.getRoutingTable().get(0).getDestinationSequence(), nodeB.getOriginatorSequence());
        assertEquals(nodeA.getRoutingTable().get(0).getNextHop(), nodeB.getNodeIdentifier());
    }

    @Test
    public void testProcessBeginningWithRREQ() {
        Language language = new AnimalScript("JUnit Tests", "Sascha Bleidner, Jan David Nose", 1200, 800);
        GUIController gui = new GUIController(language);

        InfoTable infoTable = new InfoTable(language, gui, nodeA, new Coordinates(0,0), 4);
        nodeA.addTable(infoTable);

        AODVMessage msg = new AODVMessage(AODVMessage.MessageType.RREQ, 0, nodeA, nodeD);
        nodeA.receiveMessage(nodeA, msg);

        assertNotNull(nodeA.getCachedMessage());

        nodeA.process();

        assertNotNull(nodeB.getCachedMessage());
        assertNotNull(nodeC.getCachedMessage());
        assertNull(nodeD.getCachedMessage());

    }

    @Test
    public void testProcessIntermediateWithRREQ() {
        Language language = new AnimalScript("JUnit Tests", "Sascha Bleidner, Jan David Nose", 1200, 800);
        GUIController gui = new GUIController(language);

        InfoTable infoTable = new InfoTable(language, gui, nodeB, new Coordinates(0,0), 4);
        nodeB.addTable(infoTable);

        AODVMessage msg = new AODVMessage(AODVMessage.MessageType.RREQ, 0, nodeA, nodeD);
        nodeB.receiveMessage(nodeA, msg);

        assertNotNull(nodeB.getCachedMessage());

        nodeB.process();

        assertNotNull(nodeA.getCachedMessage());
        assertNotNull(nodeD.getCachedMessage());
    }

    @Test
    public void testReceiveMessage() {
        AODVNode sender = new AODVNode("B");
        AODVMessage msg1 = new AODVMessage(AODVMessage.MessageType.RREQ, 0, sender, nodeA);
        AODVMessage msg2 = new AODVMessage(AODVMessage.MessageType.RREQ, 0, nodeC, nodeA);

        assertNull(nodeA.getCachedMessage());

        nodeA.receiveMessage(sender, msg1);

        assertEquals(msg1, nodeA.getCachedMessage());
        assertEquals(sender.getNodeIdentifier(), nodeA.getCachedMessageSender());

        nodeA.process();

        nodeA.receiveMessage(sender, msg2);

        assertEquals(msg2, nodeA.getCachedMessage());

        nodeA.setCachedMessage(null);

        assertNull(nodeA.getCachedMessage());

        nodeA.receiveMessage(sender, msg1);

        assertNull(nodeA.getCachedMessage());
    }

    @Test
    public void testStartRoutingDiscovery() {
        nodeA.startRouteDiscovery(nodeD);

        assertNotNull(nodeB.getCachedMessage());
        assertNotNull(nodeC.getCachedMessage());
        assertNull(nodeD.getCachedMessage());
        assertEquals(AODVMessage.MessageType.RREQ, nodeB.getCachedMessage().getType());

        nodeB.setCachedMessage(null);
        nodeC.setCachedMessage(null);

        nodeA.startRouteDiscovery(nodeB);

        assertNotNull(nodeB.getCachedMessage());
        assertNull(nodeC.getCachedMessage());
        assertNull(nodeD.getCachedMessage());
    }

    @Test
    public void testSetRoutingTable() {
        ArrayList<RoutingTableEntry> routingTable = new ArrayList<RoutingTableEntry>(10);
        char letter = 'A';
        RoutingTableEntry entry;

        for(int i = 0; i < 10; i++) {
            entry = new RoutingTableEntry(String.valueOf(letter), 0, Integer.MAX_VALUE, "");
            letter++;
            routingTable.add(entry);
        }

        nodeA.setRoutingTable(routingTable);
        nodeB.setRoutingTable(routingTable);

        assertEquals(10, nodeA.getRoutingTable().size());
        assertNotEquals(routingTable, nodeA.getRoutingTable());
        assertNotEquals(nodeA.getRoutingTable(), nodeB.getRoutingTable());

        RoutingTableEntry entryA = nodeA.getRoutingTable().get(0);
        RoutingTableEntry entryB = nodeB.getRoutingTable().get(0);

        assertNotEquals(entryA, entryB);
        assertEquals("A", entryA.getIdentifier());
        assertEquals("A", entryB.getIdentifier());
        assertEquals(Integer.MAX_VALUE, entryB.getHopCount());
        assertEquals("", entryB.getNextHop());
    }
}