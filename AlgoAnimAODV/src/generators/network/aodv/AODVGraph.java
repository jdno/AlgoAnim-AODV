package generators.network.aodv;

import java.util.ArrayList;

import algoanim.primitives.Graph;
import algoanim.primitives.generators.Language;
import algoanim.properties.GraphProperties;
import algoanim.util.Coordinates;
import algoanim.util.Node;

public class AODVGraph {

	private Graph animalGraph;
	private Language lang;
	private String[] graphLabels = {};
	private ArrayList<AODVNode> aodvNodes = new ArrayList<AODVNode>();

	public AODVGraph(Language lang, Graph animalGraph) {
		this.lang = lang;
		this.animalGraph = animalGraph;
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
	
	private void initialize() {
		Node node;
		AODVNode aodv;
		
		for(int i = 0; i < animalGraph.getSize(); i++) {
			node = animalGraph.getNode(i);
			aodv = new AODVNode(animalGraph.getNodeLabel(i));
			aodvNodes.add(aodv);
		}
		
		int[] neighbors;
		
		for(int i = 0; i < animalGraph.getSize(); i++) {
			neighbors = animalGraph.getEdgesForNode(i);
			aodv = aodvNodes.get(i);
			
			for(int j: neighbors) {
				aodv.addNeighbor(aodvNodes.get(j));
			}
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

}
