package generators.network.aodv.guielements.Tables;

import algoanim.properties.RectProperties;
import generators.network.aodv.AODVNode;
import generators.network.aodv.RoutingTableEntry;

import java.util.ArrayList;

import algoanim.primitives.generators.Language;
import algoanim.util.Coordinates;
import generators.network.aodv.GUIController;

public class InfoTable extends GUITable {


	/**
	 * Shortcut for the approx. height of the whole table.
	 */
	private final int height;


	private final ArrayList<InfoTableEntry> tableEntries;

	private final AODVNode ownNode;
	private final int numOFNodes;
	private final GUIController controller;

	public InfoTable(Language lang, GUIController controller,
			AODVNode nodeForThisTable, Coordinates startPoint, int numOfNodes, RectProperties highlight) {
		super(lang, startPoint,highlight);
		this.controller = controller;
		this.ownNode = nodeForThisTable;
		this.numOFNodes = numOfNodes;
        title = "Node: " + ownNode.getNodeIdentifier();
        cellHeight = 15;
        cellWidth = 30;
        titles = new String[] { "N", "DS", "HC", "NH" };
		this.height = cellHeight * (numOFNodes + 1);
		this.tableEntries = new ArrayList<InfoTableEntry>();
		initContent();
	}

	private void initContent() {
		drawTitles(numOFNodes+1);
		for (int row = 0; row < numOFNodes; row++) {
			tableEntries.add(new InfoTableEntry(lang, currentLine,
                    cellWidth, cellHeight, highlight));
			nextLine();
		}

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
		return cellWidth * titles.length;
	}

}