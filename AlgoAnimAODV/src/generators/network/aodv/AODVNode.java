package generators.network.aodv;

import generators.network.aodv.AODVMessage.MessageType;
import java.util.ArrayList;

/**
 * This class represents a node in a network that uses AODV. It has an
 * identifier and a sequence number as well as a routing table.
 * 
 * @author Jan David
 * 
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
	 * A list of the neighbors of the node.
	 */
	private ArrayList<AODVNode> neighbors = new ArrayList<AODVNode>();

	/**
	 * The routing table consists of a list of routing table entries.
	 */
	private ArrayList<RoutingTableEntry> routingTable = new ArrayList<RoutingTableEntry>();

	public AODVNode(String nodeIdentifier) {
		this.nodeIdentifier = nodeIdentifier;
	}
	
	/**
	 * Process the currently cached message.
	 */
	public void process() {
		// TODO visualize sending of message
		
		if (cachedMessage != null) {
			updateRoutingTable(cachedMessage);
			
			if (cachedMessage.getType() == MessageType.RREQ) {
				if(cachedMessage.getDestinationIdentifier().equals(nodeIdentifier)) {
					// TODO respond with RREP
				} else {
					for(AODVNode neighbor: neighbors) {
						neighbor.receiveMessage(this, cachedMessage);
					}
				}
			} else {
				for(RoutingTableEntry entry: routingTable) {
					if (entry.getIdentifier().equals(cachedMessage.getDestinationIdentifier())) {
						for(AODVNode neighbor: neighbors) {
							if (neighbor.nodeIdentifier.equals(entry.getNextHop())) {
								neighbor.receiveMessage(this, cachedMessage);
							} else {
								System.err.println("No neighbor found to forward message to.");
							}
						}
					} else {
						System.err.println("No route to message destination found.");
					}
				}
			}
		}
	}
	
	/**
	 * Receive a new AODV message (either a RREQ or RREP). The message does not get processed
	 * automatically, but is cached within the node until you call .process(). If the same 
	 * message is received multiple times, only the first occurrence will be saved. If a new
	 * message arrives before the old one was processed, the old one gets overwritten.
	 * @param message The message to process later
	 */
	public void receiveMessage(AODVNode sender, AODVMessage message) {
		if (cachedMessage == null) {
			cachedMessage = message;
			cachedMessageSender = sender.nodeIdentifier;
		} else {
			if (cachedMessage.getIdentifier() != message.getIdentifier()) {
				cachedMessage = message;
				cachedMessageSender = sender.nodeIdentifier;
    		}
		}
	}
	
	/**
	 * Update the routing table with the information from the given message.
	 * @param message The message to analyze
	 */
	private void updateRoutingTable(AODVMessage message) {
		boolean originatorUpdated = false;
		boolean destinationUpdated = false;
		
		for(RoutingTableEntry entry: routingTable) {
			if (entry.getIdentifier().equals(message.getOriginatorIdentifier())) {
				// Update originator if its sequence number is more up to date
				if (entry.getDestinationSequence() < message.getOriginatorSequence()) {
    				entry.setDestinationSequence(message.getOriginatorSequence());
    				entry.setNextHop(cachedMessageSender);
    				originatorUpdated = true;
    			}
			}
			if (entry.getIdentifier().equals(message.getDestinationIdentifier())) {
				// Update destination if its sequence number is more up to date
				if (entry.getDestinationSequence() < message.getDestinationSequence()) {
    				entry.setDestinationSequence(message.getDestinationSequence());
    				entry.setNextHop(cachedMessageSender);
    				destinationUpdated = true;
    			}
			}
		}
		
		if (!originatorUpdated) {
			RoutingTableEntry newEntry = new RoutingTableEntry(message.getOriginatorIdentifier());
			newEntry.setDestinationSequence(message.getDestinationSequence());
			newEntry.setHopCount(message.getHopCount());
			newEntry.setNextHop(cachedMessageSender);
			routingTable.add(newEntry);
		}
		
		if (!destinationUpdated) {
			RoutingTableEntry newEntry = new RoutingTableEntry(message.getOriginatorIdentifier());
			newEntry.setDestinationSequence(message.getDestinationSequence());
			newEntry.setHopCount(message.getHopCount());
			newEntry.setNextHop(cachedMessageSender);
			routingTable.add(newEntry);
		}
	}
	
	/**
	 * @return the originatorSequence
	 */
	public int getOriginatorSequence() {
		return originatorSequence;
	}

	/**
	 * @param originatorSequence
	 *            the originatorSequence to set
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
}