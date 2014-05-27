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
	public static String[] graphLabels = { "A", "B", "C", "D" , "E", "F", "G", "H"};
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
		
		aodvNodes.get(0).setOriginatorSequence(10);
		
		nodesOfGraph = new Node[8];
		nodesOfGraph[0] = new Coordinates(150, 20);
		nodesOfGraph[1] = new Coordinates(220, 350);
		nodesOfGraph[2] = new Coordinates(50, 70);
		nodesOfGraph[3] = new Coordinates(250, 70);
		nodesOfGraph[4] = new Coordinates(100, 170);
		nodesOfGraph[5] = new Coordinates(250, 170);
		nodesOfGraph[6] = new Coordinates(100, 300);
		nodesOfGraph[7] = new Coordinates(300, 250);
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
