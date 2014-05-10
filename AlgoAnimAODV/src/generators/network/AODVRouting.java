package generators.network;

import generators.framework.Generator;
import generators.framework.GeneratorType;
import generators.framework.properties.AnimationPropertiesContainer;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Locale;

import algoanim.animalscript.AnimalScript;
import algoanim.primitives.Graph;
import algoanim.primitives.generators.Language;
import algoanim.properties.GraphProperties;
import algoanim.util.Coordinates;
import algoanim.util.Node;

// TODO: Tabellen anzeigen
// TODO: Private Klasse für Tabellen
// TODO: Graphen Properties erkunden
// TODO: Infobox anzeigen und verändern


public class AODVRouting implements Generator {
    private Language lang;
    private int[][] adjacencyMatrix;

    public void init(){
        lang = new AnimalScript("Ad-hoc Optimized Vector Routing", "Sascha Bleidner, Jan David Nose", 800, 600);
    }

    public String generate(AnimationPropertiesContainer props,Hashtable<String, Object> primitives) {
        adjacencyMatrix = (int[][])primitives.get("adjacencyMatrix");
        
//        GraphProperties graphProps = (GraphProperties) props.getPropertiesByName("graphProps");
        getDefaultGraph(null);
        return lang.toString();
    }
    
    private Graph getDefaultGraph(GraphProperties graphProps){
    	Graph defaultGraph;
    	
    	Node[] nodes = new Node[4];
    	nodes[0] = new Coordinates(40, 100);
	    nodes[1] = new Coordinates(40, 250);
	    nodes[2] = new Coordinates(190, 100);
	    nodes[3] = new Coordinates(190, 250);
	    
    	String[] graphLabels = {"A", "B", "C", "D"}; 
	    
    	defaultGraph = lang.newGraph("AODV-Graph", adjacencyMatrix, nodes, graphLabels, null);
//    	defaultGraph.hide();
    	return defaultGraph;
    	
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

    public String getDescription(){
        return "AODV ist ein reaktiver Routingalgorithmus für mobile Ad-hoc Netze.";
    }

    public String getCodeExample(){
        return "CodeExample";
    }

    public String getFileExtension(){
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
     * This class represents a node in a network that uses AODV. It has an identifier
     * and a sequence number as well as a routing table.
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
    	 * Sequence number used to evaluate the freshness of routes. It's updated in two cases
    	 * only:
    	 * - before the node instantiates a RREQ
    	 * - before the node answers to a RREP
    	 */
    	private int originatorSequence = 0;
    	
    	/**
    	 * The routing table consists of a list of routing table entries.
    	 */
    	private ArrayList<RoutingTableEntry> routingTable = new ArrayList<RoutingTableEntry>();
    	
    	public AODVNode (String nodeIdentifier) {
    		this.nodeIdentifier = nodeIdentifier;
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
     * A routing table for the AODV routing algorithm holds he following information about
     * the nodes in the network:
     * 
     * - nodeIdenifier: textual representation of a node's name/identifier
     * - destinationSequence: this is used to evaluate the freshness of a route
     * - hopCount: the distance to the node
     * - nextHop: this is the next node on the route to the destination
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
    	 * Sequence number used to evaluate the freshness of the route. It is updated in
    	 * two cases only:
    	 * - the route to the destination breaks
    	 * - new information becomes available with a higher sequence number for the node
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
    	 * Create a new routing table entry with default values. The nodeIdentifier must be
    	 * set though.
    	 * @param nodeIdentifier The node's identifier
    	 */
    	public RoutingTableEntry(String nodeIdentifier) {
    		this.nodeIdentifier = nodeIdentifier;
    	}
    	
    	/**
    	 * Create a new routing table entry with custom values.
    	 * @param nodeIdentifier The node's identifier
    	 * @param destinationSequence The node's destination sequence
    	 * @param hopCount The hop count to the node
    	 * @param nextHop The next hop to the node
    	 */
    	public RoutingTableEntry(String nodeIdentifier, int destinationSequence, int hopCount, String nextHop) {
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
		 * @param destinationSequence the destinationSequence to set
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
		 * @param hopCount the hopCount to set
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
		 * @param nextHop the nextHop to set
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

}