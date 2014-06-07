package generators.network.aodv.guielements;

import algoanim.primitives.Graph;
import algoanim.primitives.generators.Language;
import algoanim.properties.AnimationPropertiesKeys;
import algoanim.properties.GraphProperties;
import algoanim.util.Node;
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
        graphProperties.set(AnimationPropertiesKeys.DIRECTED_PROPERTY, false);

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

        animalGraph = lang.newGraph("Graph", loadedGraph.getAdjacencyMatrix(), nodes, graphLabels, null, graphProperties);
        animalGraph.hide();
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
