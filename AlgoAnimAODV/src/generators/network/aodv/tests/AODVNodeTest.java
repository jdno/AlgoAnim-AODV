package generators.network.aodv.tests;

import generators.network.aodv.AODVMessage;
import generators.network.aodv.AODVNode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
    public void testReceiveMessage() {
        AODVNode sender = new AODVNode("B");
        AODVMessage msg1 = new AODVMessage(AODVMessage.MessageType.RREQ, 0, sender, nodeA);
        AODVMessage msg2 = new AODVMessage(AODVMessage.MessageType.RREQ, 1, sender, nodeA);

        assertNull(nodeA.getCachedMessage());

        nodeA.receiveMessage(sender, msg1);

        assertEquals(msg1, nodeA.getCachedMessage());
        assertEquals(sender.getNodeIdentifier(), nodeA.getCachedMessageSender());

        nodeA.receiveMessage(sender, msg2);

        assertEquals(msg2, nodeA.getCachedMessage());
    }

    @Test
    public void testStartRoutingDiscovery() {
        nodeA.startRouteDiscovery(nodeD);

        assertNotNull(nodeB.getCachedMessage());
        assertNotNull(nodeC.getCachedMessage());
        assertEquals(AODVMessage.MessageType.RREQ, nodeB.getCachedMessage().getType());

        nodeB.setCachedMessage(null);
        nodeC.setCachedMessage(null);

        nodeA.startRouteDiscovery(nodeB);

        assertNotNull(nodeB.getCachedMessage());
        assertNull(nodeC.getCachedMessage());
    }
}