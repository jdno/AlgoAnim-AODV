package generators.network.aodv.guielements;

import generators.network.aodv.AODVGraph;
import generators.network.aodv.AODVNode;
import generators.network.aodv.RoutingTableEntry;

import java.awt.Color;
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
	
	
	AODVNode ownNode;
	int numNodes;

	public InfoTable(Language lang, AODVNode nodeForThisTable, Coordinates startPoint,
			int numOfNodes) {
		this.lang = lang;
		this.ownNode = nodeForThisTable;
		this.currentLine = startPoint;
		this.numNodes = numOfNodes;
		height =  cellHeight * (numNodes+1);
		this.textContent = new ArrayList<ArrayList<Text>>();
		this.cells = new ArrayList<ArrayList<Rect>>();
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
			lang.newText(GeometryToolBox.moveCoordinate(currentLine, distanceColumns * i, 0),
					titles[i], "", null);

			if (i != 0) {
				GeometryToolBox.drawVerticalLine(
						GeometryToolBox.moveCoordinate(currentLine, distanceColumns * i - 5, 0),
						height);
			}
		}
		// switch to the next line
		nextLine();
		GeometryToolBox.drawHorizontalLie(currentLine, distanceColumns * numRows);

		for (int i = 0; i < numNodes; i++) {
			ArrayList<Text> textInOneLine = new ArrayList<Text>();
			ArrayList<Rect> cellsInOneLine = new ArrayList<Rect>();

			for (int z = 0; z < numRows; z++) {

				// create rectangles as cells for highlighting
				Coordinates cellUpperLeft = GeometryToolBox.moveCoordinate(currentLine,
						distanceColumns * z - 2, -1);
				Coordinates cellDownRight = GeometryToolBox.moveCoordinate(currentLine,
						distanceColumns * z + distanceColumns - 10,
						cellHeight - 1);

				Rect newRect = lang.newRect(cellUpperLeft, cellDownRight, "",
						null, rectProps);
				newRect.hide();

				cellsInOneLine.add(newRect);

				// create text inside every cell
				String textToAdd;
				if (z == 0){
					textToAdd = AODVGraph.graphLabels[i];
				} else {
					textToAdd = "-";
				}
				
				textInOneLine.add(lang.newText(
						GeometryToolBox.moveCoordinate(currentLine, distanceColumns * z, 0),
						textToAdd, "", null));
			}
			textContent.add(textInOneLine);
			cells.add(cellsInOneLine);
			nextLine();
		}

	}

	private void nextLine() {
		currentLine = GeometryToolBox.moveCoordinate(currentLine, 0, cellHeight);
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
	
	
	public AODVNode getAODVNode(){
		return ownNode;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getWidth(){
		return distanceColumns*numRows;
	}


}