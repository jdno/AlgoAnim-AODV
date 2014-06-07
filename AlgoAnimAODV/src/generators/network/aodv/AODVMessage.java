package generators.network.aodv;

/**
 * Representation of the AODV Route Request (RREQ) and Route Response (RREP). See the official RFC for
 * documentation:
 * 
 * http://www.ietf.org/rfc/rfc3561.txt
 * 
 * @author Jan David
 *
 */
public class AODVMessage {
	
	/**
	 * The following types of AODV messages are currently supported.
	 */
	public enum MessageType {
		RREQ,
		RREP
	}
	
	/**
	 * Can either be "RREQ" or "RREQ".
	 */
	private MessageType type;
	
	/**
	 * A sequence number uniquely identifying the particular RREQ/RRPE when taken in conjunction with the
	 * originating node's IP address.
	 */
	private int identifier;
	
	/**
	 * The destination for which a route is required.
	 */
	private String destinationIdentifier;
	
	/**
	 * The latest sequence number received in the past by the originator for any route towards the
	 * destination.
	 */
	private int destinationSequence;
	
	/**
	 * The originator of the route request.
	 */
	private String originatorIdentifier;
	
	/**
	 * The current sequence number to be used in the route entry pointing towards the originator of the route
	 * request.
	 */
	private int originatorSequence;
	
	/**
	 * The number of hops from the Originator to the node handling the request.
	 */
	private int hopCount = 1;
	
	/**
	 * Create an AODV message. The message ID should be set to the originator's sequence number or another number
	 * that is unique when combined with the originator's identifier.
	 * @param type Can either be "RREQ" or "RREP"
	 * @param identifier The RREQ/RREP's ID
	 * @param destinationIdentifier The identifier of the destination
	 * @param destinationSequence The last known sequence number of the destination
	 * @param originatorIdentifier The identifier of the originator
	 * @param originatorSequence The sequence number of the originator
	 */
	public AODVMessage(MessageType type, int identifier, String destinationIdentifier, int destinationSequence, String originatorIdentifier, int originatorSequence) {
		this.type = type;
		this.identifier = identifier;
		this.destinationIdentifier = destinationIdentifier;
		this.destinationSequence = destinationSequence;
		this.originatorIdentifier = originatorIdentifier;
		this.originatorSequence = originatorSequence;
	}

    /**
     * Create an AODV message. Use this constructor for texts or neighbor-to-neighbor communication.
     * @param type Can either be "RREQ" or "RREP"
     * @param identifier The RREQ/RREP's ID
     * @param sender The original sender of the message
     * @param receiver The intended receiver for the message
     */
    public AODVMessage(MessageType type, int identifier, AODVNode sender, AODVNode receiver) {
        this.type = type;
        this.identifier = identifier;
        this.destinationIdentifier = receiver.getNodeIdentifier();
        this.destinationSequence = receiver.getOriginatorSequence();
        this.originatorIdentifier = sender.getNodeIdentifier();
        this.originatorSequence = sender.getOriginatorSequence();
    }

    /**
     * Return an exact copy of this instance.
     * @return The message to clone
     */
    @Override
    public AODVMessage clone() {
        AODVMessage clone = new AODVMessage(type, identifier, destinationIdentifier, destinationSequence, originatorIdentifier, originatorSequence);
        clone.setHopCount(this.hopCount);

        return clone;
    }

    /**
     * Increase the hop count by 1.
     */
    public void incrementHopCount() {
        hopCount++;
    }

	/**
	 * @return the hopCount
	 */
	public int getHopCount() {
		return hopCount;
	}

	/**
	 * @param hopCount the hopCount to set
	 */
	public void setHopCount(int hopCount) {
		this.hopCount = hopCount;
	}

	/**
	 * @return the type
	 */
	public MessageType getType() {
		return type;
	}

	/**
	 * @return the identifier
	 */
	public int getIdentifier() {
		return identifier;
	}

	/**
	 * @return the destinationIdentifier
	 */
	public String getDestinationIdentifier() {
		return destinationIdentifier;
	}

	/**
	 * @return the destinationSequence
	 */
	public int getDestinationSequence() {
		return destinationSequence;
	}

	/**
	 * @return the originatorIdentifier
	 */
	public String getOriginatorIdentifier() {
		return originatorIdentifier;
	}

	/**
	 * @return the originatorSequence
	 */
	public int getOriginatorSequence() {
		return originatorSequence;
	}
}