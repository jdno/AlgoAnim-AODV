package generators.network.aodv;

import algoanim.primitives.Graph;

import java.util.ArrayList;

/**
 * Class which represents an AODVGraph
 *
 * @author Sascha Bleidner, Jan David Nose
 */
public class AODVGraph {

    /**
     * List to store the AODVNodes
     */
    private ArrayList<AODVNode> aodvNodes = new ArrayList<AODVNode>();

    /**
     * Constructor for an AODVGraph object
     *
     * @param animalGraph the loaded animal Graph object
     * @param listener listener to call whenever a node or table updates
     */
    public AODVGraph(Graph animalGraph,AODVNodeListener listener) {
        initialize(animalGraph,listener);
    }

    /**
     * Initializes an AODVGraph from the given animalGraph;
     *
     * @param animalGraph Graph object to construct the AODVGraph from
     */
    private void initialize(Graph animalGraph, AODVNodeListener listener) {
        AODVNode aodv;

        for (int i = 0; i < animalGraph.getSize(); i++) {
            aodv = new AODVNode(animalGraph.getNodeLabel(i),i,listener);
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
    }

    /**
     * Initializes the initial RoutingTables
     */
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

    /**
     * Returns the list of AODVNodes in this graph
     *
     * @return list of ADOVNodes
     */
    public ArrayList<AODVNode> getAODVNodes() {
        return aodvNodes;
    }

    /**
     * Get node by the given node identifier
     *
     * @param nodeIdentifier Identifier of a node
     * @return AODVNode with the given Identifier
     */
    public AODVNode getNode(String nodeIdentifier) {
        for(AODVNode node: aodvNodes) {
            if (node.getNodeIdentifier().equals(nodeIdentifier)) {
                return node;
            }
        }

        return null;
    }

}
