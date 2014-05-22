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


	 /**
	   * The concrete language object used for creating output
	   */
	private Language lang;

	/**
	 * ToolBox for basic geometry functionalities. 
	 */
	private GeometryToolBox tools;
	
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
	private int height = cellHeight * numRows * 2;
	
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
	
	
	AODVNode ownNode;
	int numNodes;

	public InfoTable(Language lang, AODVNode nodeForThisTable, Coordinates startPoint,
			int numOfNodes) {
		this.tools = new GeometryToolBox(lang);
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
			lang.newText(tools.moveCoordinate(currentLine, distanceColumns * i, 0),
					titles[i], "", null);

			if (i != 0) {
				tools.drawVerticalLine(
						tools.moveCoordinate(currentLine, distanceColumns * i - 5, 0),
						height);
			}
		}
		// switch to the next line
		nextLine();
		tools.drawHorizontalLie(currentLine, distanceColumns * (numRows + 1));

		for (int i = 0; i < numNodes; i++) {
			ArrayList<Text> textInOneLine = new ArrayList<Text>();
			ArrayList<Rect> cellsInOneLine = new ArrayList<Rect>();

			for (int z = 0; z < numRows; z++) {

				// create rectangles as cells for highlighting
				Coordinates cellUpperLeft = tools.moveCoordinate(currentLine,
						distanceColumns * z - 2, -1);
				Coordinates cellDownRight = tools.moveCoordinate(currentLine,
						distanceColumns * z + distanceColumns - 10,
						cellHeight - 1);

				Rect newRect = lang.newRect(cellUpperLeft, cellDownRight, "",
						null, rectProps);
				newRect.hide();

				cellsInOneLine.add(newRect);

				// create text inside every cell
				textInOneLine.add(lang.newText(
						tools.moveCoordinate(currentLine, distanceColumns * z, 0),
						"-", "", null));
			}
			textContent.add(textInOneLine);
			cells.add(cellsInOneLine);
			nextLine();
		}

	}

	private void nextLine() {
		currentLine = tools.moveCoordinate(currentLine, 0, cellHeight);
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



}