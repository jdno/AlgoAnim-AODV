package generators.network.aodv;

import algoanim.primitives.Graph;
import algoanim.primitives.generators.Language;
import algoanim.properties.AnimationPropertiesKeys;
import algoanim.properties.GraphProperties;
import algoanim.util.Node;

import java.awt.*;
import java.util.ArrayList;

public class AODVGraph {


    private Language lang;
    private Graph animalGraph;
    private String[] graphLabels = {};
    private ArrayList<AODVNode> aodvNodes = new ArrayList<AODVNode>();
    private AODVNodeListener listener;


    public AODVGraph(Language lang, Graph animalGraph,AODVNodeListener listener) {
        this.lang = lang;
        this.animalGraph = animalGraph;
        this.listener = listener;
        this.graphLabels = new String[animalGraph.getSize()];
        initialize();

    }



    private void initialize() {
        AODVNode aodv;
        for (int i = 0; i < animalGraph.getSize(); i++) {
            aodv = new AODVNode(animalGraph.getNodeLabel(i),i,listener);
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


    public AODVNode getNode(int index) {
        return aodvNodes.get(index);
    }

    public Graph getAnimalGraph(){return animalGraph;}
  
    public void printGraph(){
    	for (AODVNode node : aodvNodes){
    		System.out.println(node.getNeighborsAsString());
    	}
    }




    
}
