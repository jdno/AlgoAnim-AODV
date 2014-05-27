package generators.network.aodv;

import java.util.ArrayList;

import algoanim.primitives.Graph;
import algoanim.primitives.generators.Language;
import algoanim.properties.GraphProperties;
import algoanim.util.Coordinates;
import algoanim.util.Node;

public class AODVGraph {

	private Graph animalGraph;
	private Node[] nodesOfGraph;
	private Language lang;
	private String[] graphLabels = { "A", "B", "C", "D" , "E", "F", "G", "H"};
	private ArrayList<AODVNode> aodvNodes;

	public AODVGraph(Language lang, GraphProperties graphProps,
			int[][] adjacencyMatrix) {
		this.lang = lang;
		initDefaultGraph(graphProps, adjacencyMatrix);
	}

	public Graph getGraph() {
		return animalGraph;
	}

	private void initDefaultGraph(GraphProperties graphProps,
			int[][] adjacencyMatrix) {
		aodvNodes = new ArrayList<AODVNode>();
		for (int i = 0; i < graphLabels.length; i++) {
			aodvNodes.add(new AODVNode(graphLabels[i]));
		}
		nodesOfGraph = new Node[4];
		nodesOfGraph[0] = new Coordinates(200, 100);
		nodesOfGraph[1] = new Coordinates(200, 250);
		nodesOfGraph[2] = new Coordinates(390, 100);
		nodesOfGraph[3] = new Coordinates(390, 250);
		animalGraph = lang.newGraph("AODV-Graph", adjacencyMatrix,
				nodesOfGraph, graphLabels, null, graphProps);
		animalGraph.hide();
	}

	public void show() {
		animalGraph.show();
	}
	
	public ArrayList<AODVNode> getAODVNodes(){
		return aodvNodes;
	}

	public void highlightNode(int index) {
		animalGraph.highlightNode(index, null, null);
	}

	public void highlightEdge(int startNode, int endNode) {
		animalGraph.highlightEdge(startNode, endNode, null, null);
	}
	
	public AODVNode getNode(int index){
		return aodvNodes.get(index);
	}

}
