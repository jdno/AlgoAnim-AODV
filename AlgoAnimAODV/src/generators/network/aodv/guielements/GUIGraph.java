package generators.network.aodv.guielements;

import algoanim.primitives.Graph;
import algoanim.primitives.generators.Language;
import algoanim.properties.AnimationPropertiesKeys;
import algoanim.properties.GraphProperties;
import algoanim.util.Node;
import generators.network.aodv.AODVNode;

import java.awt.*;

/**
 * Class represents a graphical GUI graph
 */
public class GUIGraph extends GUIElement{

    private Graph animalGraph;
    private GraphProperties graphProperties;
    private AODVNode lastStartNode;
    private AODVNode lastEndNode;

    /**
     * Constructor fo a GUIGraph
     * @param lang
     *          language object to draw the graph on
     * @param animalGraph
     *          animalGraph to load the GUIGraph from
     * @param highlight
     *          highlightColor for the graph
     */
    public GUIGraph(Language lang, Graph animalGraph, Color highlight){
        super(lang);
        this.graphProperties = new GraphProperties();
        transformGraph(animalGraph,highlight);
    }

    /**
     * Transforms given Graph object to a new object and adding internal properties to this new Graph object
     * @param loadedGraph
     *             Graph to be extracted
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
     * @param loadedGraph
     *          Graph to extract the adjacency matrix from
     * @return
     *      new adjacency matrix
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
     * @param node
     *          node to be highlighted
     */
    public void highlightNode(AODVNode node){
        animalGraph.highlightNode(node.getIndex(),null,null);
        lastStartNode = node;
    }

    /**
     * Highlights an edge from the given startnode to the endnode
     * @param startNode
     *          startnode of the edge
     * @param endNode
     *          endnode of the edge
     */
    public void highlightEdge(AODVNode startNode, AODVNode endNode){
        animalGraph.highlightEdge(startNode.getIndex(),endNode.getIndex(),null,null);
        lastStartNode = startNode;
        lastEndNode = endNode;
    }

    /**
     * Unhighlights the latest changes in the graph
     */
    public void unHighlightLastChange(){
        if (lastStartNode != null) {
            animalGraph.unhighlightNode(lastStartNode.getIndex(), null, null);
            if (lastEndNode != null) {
                animalGraph.unhighlightEdge(lastStartNode.getIndex(), lastEndNode.getIndex(), null, null);
            }
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

}
