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
	private String[] graphLabels = { "S", "D", "I", "II" , "III", "IV", "V", "VI"};
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
		nodesOfGraph = new Node[8];
		nodesOfGraph[0] = new Coordinates(250, 50);
		nodesOfGraph[1] = new Coordinates(50, 400);
		nodesOfGraph[2] = new Coordinates(200, 100);
		nodesOfGraph[3] = new Coordinates(400, 100);
		nodesOfGraph[4] = new Coordinates(250, 200);
		nodesOfGraph[5] = new Coordinates(400, 170);
		nodesOfGraph[6] = new Coordinates(250, 250);
		nodesOfGraph[6] = new Coordinates(350, 250);
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
