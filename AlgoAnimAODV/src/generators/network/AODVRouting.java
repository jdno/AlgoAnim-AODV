package generators.network;

import generators.framework.Generator;
import generators.framework.GeneratorType;
import generators.framework.properties.AnimationPropertiesContainer;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Locale;

import algoanim.animalscript.AnimalScript;
import algoanim.exceptions.NotEnoughNodesException;
import algoanim.primitives.Graph;
import algoanim.primitives.Text;
import algoanim.primitives.generators.Language;
import algoanim.properties.AnimationPropertiesKeys;
import algoanim.properties.GraphProperties;
import algoanim.properties.TextProperties;
import algoanim.util.Coordinates;
import algoanim.util.Node;

// TODO: Tabellen anzeigen
// TODO: Private Klasse für Tabellen
// TODO: Graphen Properties erkunden
// TODO: Infobox anzeigen und verändern

public class AODVRouting implements Generator {
	private Language lang;
	private int[][] adjacencyMatrix;

	Color highlightColor = Color.ORANGE;

	public AODVRouting(Language language) {
		this.lang = language;
	}

	public AODVRouting() {
		lang = new AnimalScript("Ad-hoc Optimized Vector Routing",
				"Sascha Bleidner, Jan David Nose", 800, 600);
	}

	public void init() {
		lang.setStepMode(true);
	}

	public String generate(AnimationPropertiesContainer props,
			Hashtable<String, Object> primitives) {
		adjacencyMatrix = (int[][]) primitives.get("adjacencyMatrix");

		GraphProperties graphProps = new GraphProperties();
		graphProps.set(AnimationPropertiesKeys.HIGHLIGHTCOLOR_PROPERTY,
				highlightColor);
		graphProps.set(AnimationPropertiesKeys.FILL_PROPERTY, Color.WHITE);
		graphProps.set(AnimationPropertiesKeys.DIRECTED_PROPERTY, true);
		graphProps.set(AnimationPropertiesKeys.EDGECOLOR_PROPERTY, Color.GREEN);

		Graph defaultGraph = getDefaultGraph(graphProps);

		defaultGraph.show();

		defaultGraph.highlightNode(0, null, null);
		defaultGraph.highlightNode(3, null, null);
		defaultGraph.highlightEdge(0, 1, null, null);
		// defaultGraph.showNode(2, null, null);
		// defaultGraph.show();
		lang.nextStep();

		Table table1 = new Table(new AODVNode("A"), (new Coordinates(350, 50)));

		// drawTable(new Coordinates(580,50));
		// drawTable(new Coordinates(350,250));
		// drawTable(new Coordinates(580,250));

		InfoBox info = new InfoBox("Erläuterung", new Coordinates(40, 420),
				new Coordinates(660, 600));
		info.updateText("Hallo");
		lang.nextStep();
		info.updateText("Wuhuuu");
		return lang.toString();
	}

	private Graph getDefaultGraph(GraphProperties graphProps) {
		Graph defaultGraph;

		Node[] nodes = new Node[4];
		nodes[0] = new Coordinates(40, 100);
		nodes[1] = new Coordinates(40, 250);
		nodes[2] = new Coordinates(190, 100);
		nodes[3] = new Coordinates(190, 250);

		String[] graphLabels = { "A", "B", "C", "D" };

		defaultGraph = lang.newGraph("AODV-Graph", adjacencyMatrix, nodes,
				graphLabels, null, graphProps);
		defaultGraph.hide();

		return defaultGraph;
	}

	private class Table {

		int verticalMove = 15;
		int verticalDistance = 30;
		int numRows = 3;
		int height = verticalMove * numRows * 2;

		String[] titles = new String[] { "N", "DS", "HC", "NH" };
		ArrayList<ArrayList<Text>> content;

		Coordinates upperLeft;

		AODVNode ownNode;

		public Table(AODVNode ownNode, Coordinates startPoint) {

			this.ownNode = ownNode;
			this.upperLeft = startPoint;
			getHorizontalLine(startPoint, verticalDistance * numRows + 40);
			// -2 for correction of the different alignments of the titles.
			for (int i = 0; i <= numRows; i++) {
				lang.newText(
						moveCoordinate(startPoint, verticalDistance * i,
								-verticalMove), titles[i], "", null);
				if (i != 0) {
					getVerticalLine(
							moveCoordinate(startPoint,
									verticalDistance * i - 5, -15), height);
				}
			}
			initContent();
		}

		private void initContent() {
			Coordinates contentStart = moveCoordinate(upperLeft, 0, 5);
			int numNodes = adjacencyMatrix[0].length;
			this.content = new ArrayList<ArrayList<Text>>();
			for (int i = 0; i < numNodes; i++) {
				ArrayList<Text> currentLine = new ArrayList<Text>();
				for (int z = 0; z <= numRows; z++) {
					currentLine.add(lang.newText(
							moveCoordinate(contentStart, verticalDistance * z,
									verticalMove * i), "A", "", null));
				}
				content.add(currentLine);
			}

		}

		public void updateTable() {
			ArrayList<RoutingTableEntry> currentRoutingTable = ownNode
					.getRoutingTable();
			// for (int i = 0; i < currentRoutingTable.size(); i++){
			//
			// lang.newText(upperLeft, text, name,
			// display)currentEntry.getNodeIdentifier()
			// }

		}

		private algoanim.primitives.Polygon getVerticalLine(
				Coordinates startPoint, int length) {
			return getPolygon(startPoint, length, true);
		}

		private algoanim.primitives.Polygon getHorizontalLine(
				Coordinates startPoint, int length) {
			return getPolygon(startPoint, length, false);
		}

		private algoanim.primitives.Polygon getPolygon(Coordinates startPoint,
				int length, boolean vertical) {

			Node[] nodes = new Node[2];
			nodes[0] = startPoint;
			if (vertical) {
				nodes[1] = new Coordinates(startPoint.getX(), startPoint.getY()
						+ length);
			} else {
				nodes[1] = new Coordinates(startPoint.getX() + length,
						startPoint.getY());
			}

			algoanim.primitives.Polygon line = null;

			try {
				line = lang.newPolygon(nodes, "line", null);
			} catch (NotEnoughNodesException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return line;
		}

	}

	private Coordinates moveCoordinate(Coordinates point, int x, int y) {
		return new Coordinates(point.getX() + x, point.getY() + y);
	}

	public String getName() {
		return "Ad-hoc Optimized Vector Routing";
	}

	public String getAlgorithmName() {
		return "AODV";
	}

	public String getAnimationAuthor() {
		return "Sascha Bleidner, Jan David Nose";
	}

	public String getDescription() {
		return "AODV ist ein reaktiver Routingalgorithmus für mobile Ad-hoc Netze.";
	}

	public String getCodeExample() {
		return "CodeExample";
	}

	public String getFileExtension() {
		return "asu";
	}

	public Locale getContentLocale() {
		return Locale.GERMAN;
	}

	public GeneratorType getGeneratorType() {
		return new GeneratorType(GeneratorType.GENERATOR_TYPE_MORE);
	}

	public String getOutputLanguage() {
		return Generator.JAVA_OUTPUT;
	}

	/**
	 * This class represents a node in a network that uses AODV. It has an
	 * identifier and a sequence number as well as a routing table.
	 * 
	 * @author Jan David
	 * 
	 */
	private class AODVNode {

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
    	 * Receive a new AODV message (either a RREQ or RREP). The message does not get processed
    	 * automatically, but is cached within the node until you call .process(). If the same 
    	 * message is received multiple times, only the first occurrence will be saved. If a new
    	 * message arrives before the old one was processed, the old one gets overwritten.
    	 * @param message The message to process later
    	 */
    	public void receiveMessage(AODVMessage message) {
    		if (cachedMessage == null) {
    			cachedMessage = message;
    			// TODO update routing table
    		} else {
    			if (cachedMessage.identifier != message.identifier) {
    				cachedMessage = message;
        			// TODO update routing table
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
		 * @param originatorSequence
		 *            the originatorSequence to set
		 */
		public void setOriginatorSequence(int originatorSequence) {
			this.originatorSequence = originatorSequence;
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
	
	/**
     * Representation of the AODV Route Request (RREQ) and Route Response (RREP). See the official RFC for
     * documentation:
     * 
     * http://www.ietf.org/rfc/rfc3561.txt
     * 
     * @author Jan David
     *
     */
    private class AODVMessage {
    	
    	/**
    	 * Can either be "RREQ" or "RREQ".
    	 */
    	private String type;
    	
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
    	private int hopCount = 0;
    	
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
    	public AODVMessage(String type, int identifier, String destinationIdentifier, int destinationSequence, String originatorIdentifier, int originatorSequence) {
    		this.type = type;
    		this.identifier = identifier;
    		this.destinationIdentifier = destinationIdentifier;
    		this.destinationSequence = destinationSequence;
    		this.originatorIdentifier = originatorIdentifier;
    		this.originatorSequence = originatorSequence;
    	}

		/**
		 * @return the hopCount
		 */
		public int getHopCount() {
			return hopCount;
		}
	}

	/**
	 * A routing table for the AODV routing algorithm holds he following
	 * information about the nodes in the network:
	 * 
	 * - nodeIdenifier: textual representation of a node's name/identifier -
	 * destinationSequence: this is used to evaluate the freshness of a route -
	 * hopCount: the distance to the node - nextHop: this is the next node on
	 * the route to the destination
	 * 
	 * @author Jan David
	 * 
	 */
	private class RoutingTableEntry {

		/**
		 * Textual representation of the destination node.
		 */
		private final String nodeIdentifier;

		/**
		 * Sequence number used to evaluate the freshness of the route. It is
		 * updated in two cases only: - the route to the destination breaks -
		 * new information becomes available with a higher sequence number for
		 * the node
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
		 * Create a new routing table entry with default values. The
		 * nodeIdentifier must be set though.
		 * 
		 * @param nodeIdentifier
		 *            The node's identifier
		 */
		public RoutingTableEntry(String nodeIdentifier) {
			this.nodeIdentifier = nodeIdentifier;
		}

		/**
		 * Create a new routing table entry with custom values.
		 * 
		 * @param nodeIdentifier
		 *            The node's identifier
		 * @param destinationSequence
		 *            The node's destination sequence
		 * @param hopCount
		 *            The hop count to the node
		 * @param nextHop
		 *            The next hop to the node
		 */
		public RoutingTableEntry(String nodeIdentifier,
				int destinationSequence, int hopCount, String nextHop) {
			this.nodeIdentifier = nodeIdentifier;
			this.destinationSequence = destinationSequence;
			this.hopCount = hopCount;
			this.nextHop = nextHop;
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

		/**
		 * @return the nodeIdentifier
		 */
		public String getNodeIdentifier() {
			return nodeIdentifier;
		}
	}

	private class InfoBox {

		Text displayedText;

		public InfoBox(String title, Coordinates upperLeft,
				Coordinates lowerRight) {
			TextProperties textProps = new TextProperties();
			textProps.set(AnimationPropertiesKeys.FONT_PROPERTY, new Font(
					"Monospaced", Font.PLAIN, 12));
			lang.newText(upperLeft, title, "Title", null, textProps);
			lang.newRect(moveCoordinate(upperLeft, 0, 20), lowerRight,
					"InfoBox", null);
			this.displayedText = lang.newText(
					moveCoordinate(upperLeft, 10, 25), "", "Text", null,
					textProps);
		}

		public void updateText(String text) {
			displayedText.setText(text, null, null);
		}

	}

}