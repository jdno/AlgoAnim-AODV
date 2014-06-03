package generators.network.aodv.guielements;

import generators.network.aodv.AODVNode;
import generators.network.aodv.RoutingTableEntry;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import algoanim.primitives.Rect;
import algoanim.primitives.Text;
import algoanim.primitives.generators.Language;
import algoanim.properties.AnimationPropertiesKeys;
import algoanim.properties.RectProperties;
import algoanim.util.Coordinates;

public class InfoTable {

	/**
	 * The concrete language object used for creating output
	 */
	private Language lang;

	/**
	 * Height of a cell
	 */
	private int cellHeight = 15;

	/**
	 * Width of a column
	 */
	private int distanceColumns = 30;

	/**
	 * Color for highlighted cells
	 */
	private Color highlightColor = Color.ORANGE;

	/**
	 * Strings for the title row
	 */
	private String[] titles = new String[] { "N", "DS", "HC", "NH" };

	/**
	 * Shortcut for the number of rows in the table
	 */
	private int numRows = titles.length;

	/**
	 * Shortcut for the approx. height of the whole table.
	 */
	private int height;

	/**
	 * List to store the text in
	 */
	private ArrayList<ArrayList<Text>> textContent;

	/**
	 * List to access the rectangle of each cell for highlight purposes
	 */
	private ArrayList<ArrayList<Rect>> cells;

	/**
	 * Represents the currentLine in the GUI
	 */
	private Coordinates currentLine;

	private ArrayList<Point> lastChanged;
	
	AODVNode ownNode;
	int numNodes;
	private GUIController controller;

	public InfoTable(Language lang, GUIController controller, AODVNode nodeForThisTable,
			Coordinates startPoint, int numOfNodes) {
		this.lang = lang;
		this.controller = controller;
		this.ownNode = nodeForThisTable;
		this.ownNode.addTable(this);
		this.currentLine = startPoint;
		this.numNodes = numOfNodes;
		height = cellHeight * (numNodes + 1);
		this.textContent = new ArrayList<ArrayList<Text>>();
		this.cells = new ArrayList<ArrayList<Rect>>();
		this.lastChanged = new ArrayList<Point>();
		initContent();
	}

	private void initContent() {

		RectProperties rectProps = new RectProperties();
		rectProps.set(AnimationPropertiesKeys.FILLED_PROPERTY, true);
		rectProps.set(AnimationPropertiesKeys.FILL_PROPERTY, highlightColor);
		rectProps.set(AnimationPropertiesKeys.COLOR_PROPERTY, highlightColor);

		lang.newText(currentLine, "Node: " + ownNode.getNodeIdentifier(),
				"Tablename", null);

		nextLine();
		for (int i = 0; i < numRows; i++) {
			lang.newText(
					GeometryToolBox.moveCoordinate(currentLine, distanceColumns
							* i, 0), titles[i], "", null);

			if (i != 0) {
				GeometryToolBox.drawVerticalLine(
						GeometryToolBox.moveCoordinate(currentLine,
								distanceColumns * i - 5, 0), height);
			}
		}
		// switch to the next line
		nextLine();
		GeometryToolBox.drawHorizontalLie(currentLine, distanceColumns
				* numRows);

		for (int i = 0; i < numNodes; i++) {
			ArrayList<Text> textInOneLine = new ArrayList<Text>();
			ArrayList<Rect> cellsInOneLine = new ArrayList<Rect>();

			for (int z = 0; z < numRows; z++) {

				// create rectangles as cells for highlighting
				Coordinates cellUpperLeft = GeometryToolBox.moveCoordinate(
						currentLine, distanceColumns * z - 2, -1);
				Coordinates cellDownRight = GeometryToolBox.moveCoordinate(
						currentLine,
						distanceColumns * z + distanceColumns - 10,
						cellHeight - 1);

				Rect newRect = lang.newRect(cellUpperLeft, cellDownRight, "",
						null, rectProps);
				newRect.hide();

				cellsInOneLine.add(newRect);

				// create text inside every cell
				String textToAdd;
				if (z == 0) {
					textToAdd = "A";
				} else {
					textToAdd = "-";
				}

				textInOneLine.add(lang.newText(GeometryToolBox.moveCoordinate(
						currentLine, distanceColumns * z, 0), textToAdd, "",
						null));
			}
			textContent.add(textInOneLine);
			cells.add(cellsInOneLine);
			nextLine();
		}

	}

	private void nextLine() {
		currentLine = GeometryToolBox
				.moveCoordinate(currentLine, 0, cellHeight);
	}

	public void updateTable() {
		controller.tableRefresh();
		ArrayList<RoutingTableEntry> currentRoutingTable = ownNode
				.getRoutingTable();
		boolean updated = false;
		for (int i = 0; i < textContent.size(); i++) {

			if (currentRoutingTable.get(i) != null) {
				// check node identifier
				String nodeID = currentRoutingTable.get(i).getIdentifier();
				if (!nodeID.equals(textContent.get(i).get(0).getText())) {
					textContent.get(i).get(0).setText(nodeID, null, null);
					highlightCell(i, 0, true);
					lastChanged.add(new Point(i,0));
					updated = true;
					
				} 

				// check destination sequence number
				String DestinationSeq = Integer.toString(currentRoutingTable
						.get(i).getDestinationSequence());
				if (!DestinationSeq.equals(textContent.get(i).get(1).getText())) {
					textContent.get(i).get(1)
							.setText(DestinationSeq, null, null);
					highlightCell(i, 1, true);
					lastChanged.add(new Point(i,1));
					updated = true;
					
				} 

				// check hop count
				int hopCount = currentRoutingTable.get(i).getHopCount();
				if (!Integer.toString(hopCount).equals(
						textContent.get(i).get(2).getText())) {
					String hopString;
					if (hopCount == Integer.MAX_VALUE) {
						hopString = "inf";
					} else {
						hopString = Integer.toString(hopCount);
					}

					textContent.get(i).get(2).setText(hopString, null, null);
					highlightCell(i, 2, true);
					lastChanged.add(new Point(i,2));
					updated = true;
					
				} 

				// check next hop
				String nextHop = currentRoutingTable.get(i).getNextHop();
				if (!nextHop.equals(textContent.get(i).get(3).getText())) {
					textContent.get(i).get(3).setText(nextHop, null, null);
					highlightCell(i, 3, true);
					lastChanged.add(new Point(i,3));
					updated = true;
				} 
			}
		}
		if (updated){
		controller.tableUpdated(this);
		}
		lang.nextStep();
	}

	public void highlightCell(int row, int column, boolean highlight) {
		if (highlight) {
			cells.get(row).get(column).show();
		} else {
			cells.get(row).get(column).hide();
		}
	}
	
	public void refresh(){
		for (Point currentPoint : lastChanged){
			highlightCell((int)currentPoint.getX(),(int)currentPoint.getY(),false);
		}
		lastChanged.clear();
	}

	public AODVNode getAODVNode() {
		return ownNode;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return distanceColumns * numRows;
	}

}