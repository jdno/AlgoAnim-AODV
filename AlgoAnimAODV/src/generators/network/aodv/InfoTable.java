package generators.network.aodv;

import java.awt.Color;
import java.util.ArrayList;

import algoanim.exceptions.NotEnoughNodesException;
import algoanim.primitives.Rect;
import algoanim.primitives.Text;
import algoanim.primitives.generators.Language;
import algoanim.properties.AnimationPropertiesKeys;
import algoanim.properties.RectProperties;
import algoanim.util.Coordinates;
import algoanim.util.Node;

public class InfoTable {

	int cellHeight = 15;
	int distanceColumns = 30;

	Color highlightColor = Color.ORANGE;
	
	String[] titles = new String[] { "N", "DS", "HC", "NH" };
	int numRows = titles.length;
	int height = cellHeight * numRows * 2;
	ArrayList<ArrayList<Text>> textContent;
	ArrayList<ArrayList<Rect>> cells;

	Coordinates currentLine;
	Language lang;
	AODVNode ownNode;
	int numNodes;

	public InfoTable(Language lang, AODVNode nodeForThisTable, Coordinates startPoint,
			int numOfNodes) {
		this.lang = lang;
		this.ownNode = nodeForThisTable;
		this.currentLine = startPoint;
		this.numNodes = numOfNodes;
		initContent();
	}

	private void initContent() {

		this.textContent = new ArrayList<ArrayList<Text>>();
		this.cells = new ArrayList<ArrayList<Rect>>();

		RectProperties rectProps = new RectProperties();
		rectProps.set(AnimationPropertiesKeys.FILLED_PROPERTY, true);
		rectProps.set(AnimationPropertiesKeys.FILL_PROPERTY, highlightColor);
		rectProps.set(AnimationPropertiesKeys.COLOR_PROPERTY, highlightColor);

		lang.newText(currentLine, "Node: " + ownNode.getNodeIdentifier(),
				"Tablename", null);

		nextLine();
		for (int i = 0; i < numRows; i++) {
			lang.newText(moveCoordinate(currentLine, distanceColumns * i, 0),
					titles[i], "", null);

			if (i != 0) {
				drawVerticalLine(
						moveCoordinate(currentLine, distanceColumns * i - 5, 0),
						height);
			}
		}
		// switch to the next line
		nextLine();
		drawHorizontalLie(currentLine, distanceColumns * (numRows + 1));

		for (int i = 0; i < numNodes; i++) {
			ArrayList<Text> textInOneLine = new ArrayList<Text>();
			ArrayList<Rect> cellsInOneLine = new ArrayList<Rect>();

			for (int z = 0; z < numRows; z++) {

				// create rectangles as cells for highlighting
				Coordinates cellUpperLeft = moveCoordinate(currentLine,
						distanceColumns * z - 2, -1);
				Coordinates cellDownRight = moveCoordinate(currentLine,
						distanceColumns * z + distanceColumns - 10,
						cellHeight - 1);

				Rect newRect = lang.newRect(cellUpperLeft, cellDownRight, "",
						null, rectProps);
				newRect.hide();

				cellsInOneLine.add(newRect);

				// create text inside every cell
				textInOneLine.add(lang.newText(
						moveCoordinate(currentLine, distanceColumns * z, 0),
						"A", "", null));
			}
			textContent.add(textInOneLine);
			cells.add(cellsInOneLine);
			nextLine();
		}

	}

	private void nextLine() {
		currentLine = moveCoordinate(currentLine, 0, cellHeight);
	}

	public void updateTable() {
		ArrayList<RoutingTableEntry> currentRoutingTable = ownNode
				.getRoutingTable();
		for (int i = 0; i < textContent.size(); i++) {

			if (currentRoutingTable.get(i) != null) {
				// check node identifier
				if (currentRoutingTable.get(i).getIdentifier() != textContent
						.get(i).get(0).getText()) {
					textContent
							.get(i)
							.get(0)
							.setText(
									currentRoutingTable.get(i).getIdentifier(),
									null, null);
					highlightCell(i, 0, true);
				} else {
					highlightCell(i, 0, false);
				}

				// check destination sequence number
				if (Integer.toString(currentRoutingTable.get(i)
						.getDestinationSequence()) != textContent.get(i).get(1)
						.getText()) {
					textContent
							.get(i)
							.get(1)
							.setText(
									Integer.toString(currentRoutingTable.get(i)
											.getDestinationSequence()), null,
									null);
					highlightCell(i, 1, true);
				} else {
					highlightCell(i, 1, false);
				}

				// check hop count
				if (Integer.toString(currentRoutingTable.get(i).getHopCount()) != textContent
						.get(i).get(2).getText()) {
					textContent
							.get(i)
							.get(2)
							.setText(
									Integer.toString(currentRoutingTable.get(i)
											.getHopCount()), null, null);
					highlightCell(i, 2, true);
				} else {
					highlightCell(i, 2, false);
				}

				// check next hop
				if (currentRoutingTable.get(i).getNextHop() != textContent
						.get(i).get(3).getText()) {
					textContent
							.get(i)
							.get(3)
							.setText(currentRoutingTable.get(i).getNextHop(),
									null, null);
					highlightCell(i, 3, true);
				} else {
					highlightCell(i, 3, false);
				}
			}
		}
	}

	public void highlightCell(int row, int column, boolean highlight) {
		if (highlight) {
			cells.get(row).get(column).show();
		} else {
			cells.get(row).get(column).hide();
		}
	}

	private algoanim.primitives.Polygon drawVerticalLine(
			Coordinates startPoint, int length) {
		return getPolygon(startPoint, length, true);
	}

	private algoanim.primitives.Polygon drawHorizontalLie(
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

	private Coordinates moveCoordinate(Coordinates point, int moveX, int moveY) {
		return new Coordinates(point.getX() + moveX, point.getY() + moveY);
	}

}