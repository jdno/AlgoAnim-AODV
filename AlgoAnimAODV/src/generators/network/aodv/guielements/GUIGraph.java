package generators.network.aodv.guielements;

import algoanim.primitives.Graph;
import algoanim.primitives.generators.Language;
import algoanim.properties.AnimationPropertiesKeys;
import algoanim.properties.GraphProperties;
import algoanim.util.Node;
import generators.graph.helpers.AdjacencyMatrix;
import generators.network.aodv.AODVGraph;
import generators.network.aodv.AODVNode;

import java.awt.*;

/**
 * Created by sascha on 07.06.14.
 */
public class GUIGraph extends GUIElement{

    private Graph animalGraph;
    private GraphProperties graphProperties;
    private AODVNode lastStartNode;
    private AODVNode lastEndNode;
    Color highlightColor = Color.ORANGE;

    public GUIGraph(Language lang, Graph animalGraph){
        super(lang);
        this.graphProperties = new GraphProperties();
        transformGraph(animalGraph);
    }

    private void transformGraph(Graph loadedGraph) {

        graphProperties.set(AnimationPropertiesKeys.HIGHLIGHTCOLOR_PROPERTY, highlightColor);
        graphProperties.set(AnimationPropertiesKeys.FILL_PROPERTY, Color.WHITE);
        graphProperties.set(AnimationPropertiesKeys.DIRECTED_PROPERTY, true);

        String[] graphLabels;

        /**
         * Extract all information from the given graph object
         */
        Node[] nodes = new Node[loadedGraph.getSize()];
        graphLabels = new String[loadedGraph.getSize()];
        for (int i = 0; i < loadedGraph.getSize(); i++) {
            nodes[i] = loadedGraph.getNodeForIndex(i);
            graphLabels[i] = loadedGraph.getNodeLabel(nodes[i]);
        }

        animalGraph = lang.newGraph("Graph", transformAdjacencyMatrix(loadedGraph), nodes, graphLabels, null, graphProperties);
        animalGraph.hide();
    }

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


    public void highlightNode(AODVNode node){
        animalGraph.highlightNode(node.getIndex(),null,null);
        lastStartNode = node;
    }

    public void highlightEdge(AODVNode startNode, AODVNode endNode){
        animalGraph.highlightEdge(startNode.getIndex(),endNode.getIndex(),null,null);
        lastStartNode = startNode;
        lastEndNode = endNode;
    }

    public void unHighlightLastChange(){
        if (lastStartNode != null) {
            animalGraph.unhighlightNode(lastStartNode.getIndex(), null, null);
            if (lastEndNode != null) {
                animalGraph.unhighlightEdge(lastStartNode.getIndex(), lastEndNode.getIndex(), null, null);
            }
        }
    }

    public void show(){animalGraph.show();}

    public Graph getAnimalGraph(){
        return animalGraph;
    }

}
