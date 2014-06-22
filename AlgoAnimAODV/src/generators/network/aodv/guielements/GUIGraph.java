package generators.network.aodv.guielements;

import algoanim.primitives.Graph;
import algoanim.primitives.generators.Language;
import algoanim.properties.AnimationPropertiesKeys;
import algoanim.properties.GraphProperties;
import algoanim.util.Coordinates;
import algoanim.util.Node;
import generators.network.aodv.AODVNode;

import java.awt.*;
import java.util.ArrayList;

/**
 * This class is a wrapper around AnimalScript's graph object. It provides several
 * additional methods to highlight elements on the graph.
 */
public class GUIGraph extends GUIElement{

    /**
     * The AnimalScript graph instance
     */
    private Graph animalGraph;

    /**
     * The graph's properties
     */
    private GraphProperties graphProperties;

    /**
     * Used to keep track of highlighted edges
     */
    private ArrayList<HighlightedEdge> highlightedEdges = new ArrayList<HighlightedEdge>();

    /**
     * Used to keep track of highlighted nodes
     */
    private ArrayList<AODVNode> highlightedNodes = new ArrayList<AODVNode>();

    /**
     * The constructor for the graph wrapper takes the language object, the AnimalScript graph
     * and the color which is used to highlight nodes and edges.
     *
     * @param lang language object to draw the graph on
     * @param animalGraph animalGraph to load the GUIGraph from
     * @param highlight highlightColor for the graph
     */
    public GUIGraph(Language lang, Graph animalGraph, Color highlight){
        super(lang,new Coordinates(0,0));
        this.graphProperties = new GraphProperties();
        transformGraph(animalGraph,highlight);
    }

    /**
     * Transforms given Graph object to a new object and adding internal properties to this new Graph object
     * @param loadedGraph Graph to be extracted
     */
    private void transformGraph(Graph loadedGraph, Color highlight) {
        graphProperties.set(AnimationPropertiesKeys.HIGHLIGHTCOLOR_PROPERTY, highlight);
        graphProperties.set(AnimationPropertiesKeys.FILL_PROPERTY, Color.WHITE);
        graphProperties.set(AnimationPropertiesKeys.DIRECTED_PROPERTY, true);

        String[] graphLabels;
        /**
         * Extract all information from the given graph object
         */
        Node[] nodes = new Node[loadedGraph.getSize()];
        graphLabels = new String[loadedGraph.getSize()];
        System.out.println(loadedGraph.getSize());
        for (int i = 0; i < loadedGraph.getSize(); i++) {
            nodes[i] = loadedGraph.getNodeForIndex(i);
            graphLabels[i] = loadedGraph.getNodeLabel(nodes[i]);
        }

        animalGraph = lang.newGraph("Graph", transformAdjacencyMatrix(loadedGraph), nodes, graphLabels, null, graphProperties);
        animalGraph.hide();
    }

    /**
     * Transforms the adjacency matrix from the given graph to an matrix of an bidirectional connected graph
     * @param loadedGraph Graph to extract the adjacency matrix from
     * @return new adjacency matrix
     */
    private int[][] transformAdjacencyMatrix(Graph loadedGraph){
        int[][] adjacencyMatrix = new int[loadedGraph.getAdjacencyMatrix().length][loadedGraph.getAdjacencyMatrix()[0].length];
        for (int i = 0; i < loadedGraph.getAdjacencyMatrix().length; i++) {
            for (int y = 0; y < loadedGraph.getAdjacencyMatrix()[i].length;y++){
                if (loadedGraph.getAdjacencyMatrix()[i][y] == 1){
                    adjacencyMatrix[i][y] = 1;
                    adjacencyMatrix[y][i] = 1;
                }
            }
        }
        return adjacencyMatrix;
    }

    /**
     * Highlight a given AODVNode in the Graph;
     * @param node node to be highlighted
     */
    public void highlightNode(AODVNode node){
        animalGraph.highlightNode(node.getIndex(),null,null);
        highlightedNodes.add(node);
    }

    /**
     * Highlights an edge from the given start node to the end node
     * @param startNode start node of the edge
     * @param endNode end node of the edge
     */
    public void highlightEdge(AODVNode startNode, AODVNode endNode){
        animalGraph.highlightEdge(startNode.getIndex(),endNode.getIndex(),null,null);
        highlightedEdges.add(new HighlightedEdge(startNode, endNode));
    }

    /**
     * Unhighlights the latest changes in the graph
     */
    public void unHighlightLastChanges(){
        for (HighlightedEdge edge: highlightedEdges) {
            animalGraph.unhighlightEdge(edge.getStartNode().getIndex(), edge.getEndNode().getIndex(), null, null);
        }

        for (AODVNode node: highlightedNodes) {
            animalGraph.unhighlightNode(node.getIndex(), null, null);
        }
    }

    /**
     * Shows animalGraph on the screen
     */
    public void show(){animalGraph.show();}

    /**
     * Returns the internal animalGraph object
     * @return
     *      Graph object
     */
    public Graph getAnimalGraph(){
        return animalGraph;
    }

    /**
     * This private class represents a highlighted edge. It has a start and an
     * end node.
     *
     * @author Jan David
     */
    private class HighlightedEdge {

        /**
         * The start node of the edge
         */
        private AODVNode startNode;

        /**
         * The end node of the edge
         */
        private AODVNode endNode;

        /**
         * Highlight the edge between the given start and end nodes.
         *
         * @param startNode The node where the edge starts
         * @param endNode The node where the edge ends
         */
        public HighlightedEdge(AODVNode startNode, AODVNode endNode) {
            this.startNode = startNode;
            this.endNode = endNode;
        }

        /**
         * @return the start node
         */
        public AODVNode getStartNode() {
            return startNode;
        }

        /**
         * @return the end node
         */
        public AODVNode getEndNode() {
            return endNode;
        }
    }
}
