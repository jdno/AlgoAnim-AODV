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

public class InfoTable extends GUIElement {

	/**
	 * Height of a cell
	 */
	private int cellHeight = 15;

	/**
	 * Width of a column
	 */
	private int distanceColumns = 30;

	/**
	 * Strings for the title row
	 */
	private String[] titles = new String[] { "N", "DS", "HC", "NH" };


	/**
	 * Shortcut for the approx. height of the whole table.
	 */
	private int height;

	/**
	 * Represents the currentLine in the GUI
	 */
	private Coordinates currentLine;

	private ArrayList<InfoTableEntry> tableEntries;

	private AODVNode ownNode;
	private int numOFNodes;
	private GUIController controller;

	public InfoTable(Language lang, GUIController controller,
			AODVNode nodeForThisTable, Coordinates startPoint, int numOfNodes) {
		super(lang, startPoint);
		this.controller = controller;
		this.ownNode = nodeForThisTable;
		this.ownNode.addTable(this);
		this.currentLine = startPoint;
		this.numOFNodes = numOfNodes;
		this.height = cellHeight * (numOFNodes + 1);
		this.tableEntries = new ArrayList<InfoTableEntry>();
		initContent();
	}

	private void initContent() {
		lang.newText(currentLine, "Node: " + ownNode.getNodeIdentifier(),
				"Tablename", null);

		nextLine();
		for (int i = 0; i < titles.length; i++) {
			lang.newText(
					GeometryToolBox.moveCoordinate(currentLine, distanceColumns
							* i, 0), titles[i], "", null);

			if (i != 0) {
				GeometryToolBox.drawVerticalLine(lang,
						GeometryToolBox.moveCoordinate(currentLine,
								distanceColumns * i - 5, 0), height);
			}
		}
		// switch to the next line
		nextLine();
		GeometryToolBox.drawHorizontalLie(lang,currentLine, distanceColumns
				* titles.length);

		for (int row = 0; row < numOFNodes; row++) {
			tableEntries.add(new InfoTableEntry(lang, "A", currentLine,
					distanceColumns, cellHeight));
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

		if (currentRoutingTable.size() != tableEntries.size()) {
			System.err.println("There are only " + currentRoutingTable.size()
					+ " RoutingTable entries, but must be "
					+ tableEntries.size());
		}

		boolean updated = false;

		for (int entry = 0; entry < currentRoutingTable.size(); entry++) {
			InfoTableEntry infoTableEntry = tableEntries.get(entry);
			RoutingTableEntry routingTableEntry = currentRoutingTable
					.get(entry);
			if (infoTableEntry.updateInfoTableEntry(routingTableEntry)) {
				updated = true;
			}
		}
		if (updated) {
			controller.tableUpdated(this);
		}
		lang.nextStep();
	}

	
	public void refresh() {
		for (InfoTableEntry entry : tableEntries) {
			entry.unhighlight();
		}
	}

	public AODVNode getAODVNode() {
		return ownNode;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return distanceColumns * titles.length;
	}

}