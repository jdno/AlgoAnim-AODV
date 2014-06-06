package generators.network.aodv;

import algoanim.primitives.Graph;
import algoanim.primitives.generators.Language;
import algoanim.properties.AnimationPropertiesKeys;
import algoanim.properties.GraphProperties;
import algoanim.util.Node;

import java.awt.*;
import java.util.ArrayList;

public class AODVGraph {

    private Graph animalGraph;
    private Language lang;
    private String[] graphLabels = {};
    GraphProperties graphProperties;
    private ArrayList<AODVNode> aodvNodes = new ArrayList<AODVNode>();
    Color highlightColor = Color.ORANGE;

    public AODVGraph(Language lang, Graph animalGraph) {
        this.lang = lang;
        graphProperties = new GraphProperties();
        transformGraph(animalGraph);
        initialize();
    }

    public void highlightNode(int index) {
        animalGraph.highlightNode(index, null, null);
    }

    public void highlightEdge(int startNode, int endNode) {
        animalGraph.highlightEdge(startNode, endNode, null, null);
    }

    public void show() {
        animalGraph.show();
    }


    private void transformGraph(Graph loadedGraph) {

        graphProperties.set(AnimationPropertiesKeys.HIGHLIGHTCOLOR_PROPERTY, highlightColor);
        graphProperties.set(AnimationPropertiesKeys.FILL_PROPERTY, Color.WHITE);
        graphProperties.set(AnimationPropertiesKeys.DIRECTED_PROPERTY, false);

        /**
         * Extract all information from the given graph object
         */
        Node[] nodes = new Node[loadedGraph.getSize()];
        graphLabels = new String[loadedGraph.getSize()];
        for (int i = 0; i < loadedGraph.getSize(); i++) {
            nodes[i] = loadedGraph.getNodeForIndex(i);
            graphLabels[i] = loadedGraph.getNodeLabel(nodes[i]);
        }

        animalGraph = lang.newGraph("Graph", loadedGraph.getAdjacencyMatrix(), nodes, graphLabels, null, graphProperties);
    }

    private void initialize() {
        AODVNode aodv;

        for (int i = 0; i < animalGraph.getSize(); i++) {
            aodv = new AODVNode(animalGraph.getNodeLabel(i));
            graphLabels[i] = aodv.getNodeIdentifier();
            aodvNodes.add(aodv);
        }

        int[] neighbors;
        initializeRoutingTables();
        
        

        for (int i = 0; i < animalGraph.getSize(); i++) {
            neighbors = animalGraph.getEdgesForNode(i);
            aodv = aodvNodes.get(i);
            
            for (int j = 0; j < neighbors.length;j++) {
            	if (neighbors[j]==1){
                aodv.addNeighbor(aodvNodes.get(j));
                aodvNodes.get(j).addNeighbor(aodv);
            }
        }
        }
        printGraph();
    }

    private void initializeRoutingTables() {
        ArrayList<RoutingTableEntry> routingTable = new ArrayList<RoutingTableEntry>(aodvNodes.size());
        RoutingTableEntry entry;

        for(AODVNode node: aodvNodes) {
            entry = new RoutingTableEntry(node.getNodeIdentifier(), 0, Integer.MAX_VALUE, "");
            routingTable.add(entry);
        }

        for(AODVNode node: aodvNodes) {
            node.setRoutingTable(routingTable);
        }
    }

    public ArrayList<AODVNode> getAODVNodes() {
        return aodvNodes;
    }

    public Graph getGraph() {
        return animalGraph;
    }

    public AODVNode getNode(int index) {
        return aodvNodes.get(index);
    }

  
    public void printGraph(){
    	for (AODVNode node : aodvNodes){
    		System.out.println(node.getNeighborsAsString());
    	}
    }
    
}
