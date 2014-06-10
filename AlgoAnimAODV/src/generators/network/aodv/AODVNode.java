package generators.network.aodv;

import generators.network.aodv.AODVMessage.MessageType;
import generators.network.aodv.guielements.InfoTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * This class represents a node in a network that uses AODV. It has an
 * identifier and a sequence number as well as a routing table.
 *
 * @author Jan David
 */
public class AODVNode {

    /**
     * Textual representation of the node.
     */
    private final String nodeIdentifier;

    /**
     * Sequence number used to evaluate the freshness of routes. It's
     * updated in two cases only: - before the node instantiates a RREQ -
     * before the node answers to a RREP
     */
    private int originatorSequence = 0;

    /**
     * Nodes save a RREQ or RREP until they get the order to process it.
     */
    private AODVMessage cachedMessage;

    /**
     * The node from which we received the last message.
     */
    private String cachedMessageSender;

    /**
     * Keep track of already processed messages.
     */
    private HashMap<String, HashSet<Integer>> processedMessages = new HashMap<String, HashSet<Integer>>();

    /**
     * The info table used to display the routing table.
     */
    private InfoTable infoTable;

    /**
     * A list of the neighbors of the node.
     */
    private ArrayList<AODVNode> neighbors = new ArrayList<AODVNode>();

    /**
     * The routing table consists of a list of routing table entries.
     */
    private ArrayList<RoutingTableEntry> routingTable = new ArrayList<RoutingTableEntry>();

    /**
     * Index of the node in the AODVGraph
     */
    private int index;

    /**
     * Reference to AODVNodeListener to notify it when processing a node
     */
    private AODVNodeListener listener;

    /**
     * Create a new AODV node with the given node identifier.
     *
     * @param nodeIdentifier The node's identifier
     */
    public AODVNode(String nodeIdentifier) {
        this.nodeIdentifier = nodeIdentifier;
    }

    /**
     * Create a new AODV node with the given node identifier.
     *
     * @param nodeIdentifier The node's identifier
     * @param index The node's position in the animal graph
     * @param listener The listener used to highlight the node
     */
    public AODVNode(String nodeIdentifier, int index, AODVNodeListener listener) {
        this.nodeIdentifier = nodeIdentifier;
        this.index = index;
        this.listener = listener;
    }

    /**
     * Add a neighbor to this node.
     *
     * @param neighbor The neighbor to add
     */
    public void addNeighbor(AODVNode neighbor) {
        this.neighbors.add(neighbor);
        RoutingTableEntry entry = null;

        for(RoutingTableEntry e: routingTable) {
            if (e.getIdentifier().equals(neighbor.getNodeIdentifier())) {
                entry = e;
                break;
            }
        }

        if (entry == null) {
            entry = new RoutingTableEntry(neighbor.getNodeIdentifier(), neighbor.getOriginatorSequence(), 1, neighbor.getNodeIdentifier());
            routingTable.add(entry);
        } else {
            entry.setHopCount(1);
            entry.setNextHop(neighbor.getNodeIdentifier());
        }
    }

    /**
     * Add the info table for this node.
     *
     * @param infoTable The table that displays this node's routing table
     */
    public void addTable(InfoTable infoTable) {
        this.infoTable = infoTable;
    }

    /**
     * Process the currently cached message.
     */
    public void process() {
        // TODO visualize sending of message
        if (cachedMessage != null) {
            highlightNode();

            markCachedMessageAsRead();

            updateRoutingTable(cachedMessage);
            cachedMessage.incrementHopCount();

            if (cachedMessage.getType() == MessageType.RREQ) {
                if (cachedMessage.getDestinationIdentifier().equals(nodeIdentifier)) {
                    int identifier = ++originatorSequence;
                    String destinationIdentifier = cachedMessage.getOriginatorIdentifier();
                    int destinationSequence = cachedMessage.getOriginatorSequence();

                    AODVMessage msg = new AODVMessage(MessageType.RREP, identifier, destinationIdentifier, destinationSequence, nodeIdentifier, originatorSequence);

                    sendMessageToNeighbor(destinationIdentifier, msg);
                } else {
                    for (AODVNode neighbor : neighbors) {
                        neighbor.receiveMessage(this, cachedMessage.clone());
                    }
                }
            } else {
                if (!cachedMessage.getDestinationIdentifier().equals(nodeIdentifier)) {
                    sendMessageToNeighbor(cachedMessage.getDestinationIdentifier(), cachedMessage);

                }
            }

            cachedMessage = null;
        }
    }

    /**
     * Receive a new AODV message (either a RREQ or RREP). The message does not get processed
     * automatically, but is cached within the node until you call .process(). If the same
     * message is received multiple times, only the first occurrence will be saved. If a new
     * message arrives before the old one was processed, the old one gets overwritten.
     *
     * @param message The message to process later
     */
    public void receiveMessage(AODVNode sender, AODVMessage message) {
        if (cachedMessage == null && !messageAlreadyProcessed(message)) {
            cachedMessage = message;
            cachedMessageSender = sender.nodeIdentifier;
        }
    }

    /**
     * A Route Discovery is started by sending a RREQ to all neighbors.
     *
     * @param destination The destination for the Route Discovery
     */
    public void startRouteDiscovery(AODVNode destination) {
        highlightNode();

        int identifier = ++originatorSequence;
        String destinationIdentifier = destination.nodeIdentifier;
        int destinationSequence = -1;

        for (RoutingTableEntry entry : routingTable) {
            if (entry.getIdentifier().equals(destinationIdentifier)) {
                destinationSequence = entry.getDestinationSequence();
                break;
            }
        }

        if (destinationSequence == -1) {
            destinationSequence = 0;
        }

        AODVMessage rreq = new AODVMessage(AODVMessage.MessageType.RREQ, identifier, destinationIdentifier, destinationSequence, nodeIdentifier, originatorSequence);

        HashSet<Integer> processedMessagesForSender = new HashSet<Integer>();
        processedMessagesForSender.add(identifier);
        processedMessages.put(nodeIdentifier, processedMessagesForSender);

        if (neighbors.contains(destination)) {
            destination.receiveMessage(this, rreq.clone());
        } else {
            for (AODVNode neighbor : neighbors) {
                neighbor.receiveMessage(this, rreq.clone());
            }
        }

        markCachedMessageAsRead();
        updateInfoTable();
    }

    /**
     * Highlight this node if a listener is set.
     */
    private void highlightNode() {
        if (listener != null) {
            listener.highlightNode(this);
        }
    }

    /**
     * Adds the currently cached message to the list of already processed messages.
     */
    private void markCachedMessageAsRead() {
        if (cachedMessage != null) {
            HashSet<Integer> processedMessagesForSender = processedMessages.get(cachedMessage.getOriginatorIdentifier());

            if (processedMessagesForSender == null) {
                processedMessagesForSender = new HashSet<Integer>();
                processedMessages.put(cachedMessage.getOriginatorIdentifier(), processedMessagesForSender);
            }

            processedMessagesForSender.add(cachedMessage.getIdentifier());
        }
    }

    /**
     * Checks whether the message was already received.
     * @param message The message to analyze
     * @return True if the message has already been processed, false otherwise
     */
    private boolean messageAlreadyProcessed(AODVMessage message) {
        HashSet<Integer> processedMessagesForSender = processedMessages.get(message.getOriginatorIdentifier());

        if (processedMessagesForSender == null) {
            return false;
        } else {
            return processedMessagesForSender.contains(message.getIdentifier());
        }
    }

    /**
     * Auxiliary method used to send a message to a given neighbor.
     *
     * @param destinationIdentifier The neighbor to send to
     * @param message               The message to send
     */
    private void sendMessageToNeighbor(String destinationIdentifier, AODVMessage message) {
        boolean messageSent = false;

        for (RoutingTableEntry entry : routingTable) {
            if (entry.getIdentifier().equals(destinationIdentifier)) {
                for (AODVNode neighbor : neighbors) {
                    if (neighbor.getNodeIdentifier().equals(entry.getNextHop())) {
                        neighbor.receiveMessage(this, message.clone());
                        messageSent = true;
                        break;
                    }
                }
            } else {
                System.err.println("No route to message destination found.");
            }

            if (messageSent) break;
        }
    }

    /**
     * Update the info table.
     */
    private void updateInfoTable() {
        if (infoTable != null) {
            infoTable.updateTable();
        }
    }

    /**
     * Update the routing table with the information from the given message.
     *
     * @param message The message to analyze
     */
    private void updateRoutingTable(AODVMessage message) {
        for (RoutingTableEntry entry : routingTable) {
            if (entry.getIdentifier().equals(message.getOriginatorIdentifier())) {
                // Update originator if its sequence number is more up to date
                if (entry.getDestinationSequence() < message.getOriginatorSequence()) {
                    entry.setDestinationSequence(message.getOriginatorSequence());
                    entry.setNextHop(cachedMessageSender);
                    entry.setHopCount(message.getHopCount());
                    updateInfoTable();
                }
            }
            if (entry.getIdentifier().equals(message.getDestinationIdentifier())) {
                // Update destination if its sequence number is more up to date
                if (entry.getDestinationSequence() < message.getDestinationSequence()) {
                    entry.setDestinationSequence(message.getDestinationSequence());
                    updateInfoTable();
                }
            }
        }
    }

    /**
     * @return the originatorSequence
     */
    public int getOriginatorSequence() {
        return originatorSequence;
    }

    /**
     * @param originatorSequence the originatorSequence to set
     */
    public void setOriginatorSequence(int originatorSequence) {
        this.originatorSequence = originatorSequence;
    }

    /**
     * @return the cachedMessage
     */
    public AODVMessage getCachedMessage() {
        return cachedMessage;
    }

    /**
     * @param cachedMessage the cachedMessage to set
     */
    public void setCachedMessage(AODVMessage cachedMessage) {
        this.cachedMessage = cachedMessage;
    }

    /**
     * @return the cachedMessageSender
     */
    public String getCachedMessageSender() {
        return cachedMessageSender;
    }

    /**
     * @param cachedMessageSender the cachedMessageSender to set
     */
    public void setCachedMessageSender(String cachedMessageSender) {
        this.cachedMessageSender = cachedMessageSender;
    }

    /**
     * @return the nodeIdentifier
     */
    public String getNodeIdentifier() {
        return nodeIdentifier;
    }

    /**
     * @return the routingTable
     */
    public ArrayList<RoutingTableEntry> getRoutingTable() {
        return routingTable;
    }

    /**
     * @param routingTable the routingTable to set
     */
    public void setRoutingTable(ArrayList<RoutingTableEntry> routingTable) {
        this.routingTable.clear();

        RoutingTableEntry newEntry;

        for(RoutingTableEntry entry: routingTable) {
            newEntry = entry.clone();
            this.routingTable.add(newEntry);

            if (newEntry.getIdentifier().equals(nodeIdentifier)) {
                newEntry.setHopCount(0);
                newEntry.setNextHop(nodeIdentifier);
            }
        }
    }
    
    /**
     * Returns the node ID and all his neighbors as a string for console testing
     * @return
     */
    public String getNeighborsAsString(){
    	StringBuffer strBuff = new StringBuffer();
    	strBuff.append("Node: ").append(getNodeIdentifier()).append(" has neighbors: ");
    	for (AODVNode neigbhor: neighbors){
    		strBuff.append(neigbhor.getNodeIdentifier()).append("; ");
    	}
    	return strBuff.toString();
    }

    public int getIndex(){
        return index;
    }
}
