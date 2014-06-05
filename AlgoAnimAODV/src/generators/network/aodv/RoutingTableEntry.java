package generators.network.aodv;

/**
 * A routing table for the AODV routing algorithm holds he following information
 * about the nodes in the network:
 * 
 * - nodeIdenifier: textual representation of a node's name/identifier -
 * destinationSequence: this is used to evaluate the freshness of a route -
 * hopCount: the distance to the node - nextHop: this is the next node on the
 * route to the destination
 * 
 * @author Jan David
 * 
 */
public class RoutingTableEntry {

	/**
	 * Textual representation of the destination node.
	 */
	private final String identifier;

	/**
	 * Sequence number used to evaluate the freshness of the route. It is
	 * updated in two cases only: - the route to the destination breaks - new
	 * information becomes available with a higher sequence number for the node
	 */
	private int destinationSequence = 0;

	/**
	 * The hop count to the destination.
	 */
	private int hopCount = Integer.MAX_VALUE;

	/**
	 * This is the next node on the route to the destination.
	 */
	private String nextHop = "";

	/**
	 * Create a new routing table entry with default values. The nodeIdentifier
	 * must be set though.
	 * 
	 * @param identifier
	 *            The node's identifier
	 */
	public RoutingTableEntry(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * Create a new routing table entry with custom values.
	 * 
	 * @param identifier
	 *            The node's identifier
	 * @param destinationSequence
	 *            The node's destination sequence
	 * @param hopCount
	 *            The hop count to the node
	 * @param nextHop
	 *            The next hop to the node
	 */
	public RoutingTableEntry(String identifier, int destinationSequence,
			int hopCount, String nextHop) {
		this.identifier = identifier;
		this.destinationSequence = destinationSequence;
		this.hopCount = hopCount;
		this.nextHop = nextHop;
	}

    /**
     * Return a clone of the current instance.
     *
     * @return the routingTableEntry
     */
    @Override
    public RoutingTableEntry clone() {
        return new RoutingTableEntry(identifier, destinationSequence, hopCount, nextHop);
    }

	/**
	 * @return the destinationSequence
	 */
	public int getDestinationSequence() {
		return destinationSequence;
	}

	/**
	 * @param destinationSequence
	 *            the destinationSequence to set
	 */
	public void setDestinationSequence(int destinationSequence) {
		this.destinationSequence = destinationSequence;
	}

	/**
	 * @return the hopCount
	 */
	public int getHopCount() {
		return hopCount;
	}
	
	/**
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @param hopCount
	 *            the hopCount to set
	 */
	public void setHopCount(int hopCount) {
		this.hopCount = hopCount;
	}

	/**
	 * @return the nextHop
	 */
	public String getNextHop() {
		return nextHop;
	}

	/**
	 * @param nextHop
	 *            the nextHop to set
	 */
	public void setNextHop(String nextHop) {
		this.nextHop = nextHop;
	}
}