package generators.network.aodv;

import java.util.ArrayList;

import algoanim.exceptions.NotEnoughNodesException;
import algoanim.primitives.Rect;
import algoanim.primitives.Text;
import algoanim.primitives.generators.Language;
import algoanim.util.Coordinates;
import algoanim.util.Node;

public class InfoTable {

	int verticalMove = 15;
	int distanceColumns = 30;
	int numRows = 3;
	int height = verticalMove * numRows * 2;

	String[] titles = new String[] { "N", "DS", "HC", "NH" };
	ArrayList<ArrayList<Text>> content;
	ArrayList<ArrayList<Rect>> cells;

	Coordinates startPoint;
	Coordinates currentLine;
	Language lang;
	AODVNode ownNode;
	int[][] adjacencyMatrix;

	public InfoTable(Language lang, AODVNode ownNode, Coordinates startPoint,
			int[][] adjacencyMatrix) {
		this.lang = lang;
		this.ownNode = ownNode;
		this.startPoint = startPoint;
		this.currentLine = startPoint;
		this.adjacencyMatrix = adjacencyMatrix;
		initContent();
	}

	private void initContent() {

		int numNodes = adjacencyMatrix[0].length;
		this.content = new ArrayList<ArrayList<Text>>();

		lang.newText(startPoint, "Node: " + ownNode.getNodeIdentifier(),
				"Tablename", null);

		nextLine();
		for (int i = 0; i <= numRows; i++) {
			lang.newText(moveCoordinate(currentLine, distanceColumns * i, 0),
					titles[i], "", null);
			if (i != 0) {
				getVerticalLine(
						moveCoordinate(currentLine, distanceColumns * i - 5, 0),
						height);
			}
		}
		// switch to the next line
		nextLine();
		getHorizontalLine(currentLine, distanceColumns * (numRows + 1));
		for (int i = 0; i < numNodes; i++) {
			ArrayList<Text> lineContent = new ArrayList<Text>();

			for (int z = 0; z <= numRows; z++) {
				lineContent.add(lang.newText(
						moveCoordinate(currentLine, distanceColumns * z, 0),
						"A", "", null));
			}
			content.add(lineContent);
			nextLine();
		}

	}

	private void nextLine() {
		currentLine = moveCoordinate(currentLine, 0, verticalMove);
	}

	public void updateTable() {
		ArrayList<RoutingTableEntry> currentRoutingTable = ownNode
				.getRoutingTable();
		// for (int i = 0; i < currentRoutingTable.size(); i++){
		//
		// lang.newText(upperLeft, text, name,
		// display)currentEntry.getNodeIdentifier()
		// }

	}

	private algoanim.primitives.Polygon getVerticalLine(Coordinates startPoint,
			int length) {
		return getPolygon(startPoint, length, true);
	}

	private algoanim.primitives.Polygon getHorizontalLine(
			Coordinates startPoint, int length) {
		return getPolygon(startPoint, length, false);
	}

	private algoanim.primitives.Polygon getPolygon(Coordinates startPoint,
			int length, boolean vertical) {

		Node[] nodes = new Node[2];
		nodes[0] = startPoint;
		if (vertical) {
			nodes[1] = new Coordinates(startPoint.getX(), startPoint.getY()
					+ length);
		} else {
			nodes[1] = new Coordinates(startPoint.getX() + length,
					startPoint.getY());
		}

		algoanim.primitives.Polygon line = null;

		try {
			line = lang.newPolygon(nodes, "line", null);
		} catch (NotEnoughNodesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return line;
	}

	private Coordinates moveCoordinate(Coordinates point, int x, int y) {
		return new Coordinates(point.getX() + x, point.getY() + y);
	}

}