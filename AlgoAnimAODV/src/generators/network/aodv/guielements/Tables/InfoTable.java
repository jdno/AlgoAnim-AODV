package generators.network.aodv.guielements.Tables;

import algoanim.properties.RectProperties;
import generators.network.aodv.AODVNode;
import generators.network.aodv.RoutingTableEntry;

import java.util.ArrayList;

import algoanim.primitives.generators.Language;
import algoanim.util.Coordinates;
import generators.network.aodv.GUIController;

/**
 * The InfoTable is the graphical representation of a Routing Table. It extends
 * GUITable to draw custom tables in the canvas.
 *
 * Every InfoTable has exactly one instance of AODVNode, whose routing table it displays.
 *
 * @author Sascha Bleidner, Jan David Nose
 */
public class InfoTable extends GUITable {

	/**
	 * The height of the whole table
	 */
	private final int height;

    /**
     * The rows of the InfoTable
     */
	private final ArrayList<InfoTableEntry> tableEntries;

    /**
     * The instance of AODVNode, whose routing table gets displayed
     */
	private final AODVNode ownNode;

    /**
     * The total number of nodes in the graph. This influences the height of
     * the table.
     */
	private final int numOfNodes;

    /**
     * The GUIController instance in charge for this InfoTable
     */
	private final GUIController controller;

    /**
     * Creates a new instance of InfoTable. It automatically prints routing table of the given AODVNode
     * at the given coordinates.
     *
     * @param lang The language object
     * @param controller The GUIController handling this instance
     * @param nodeForThisTable The AODVNode whose routing table should be printed
     * @param startPoint The top-left coordinate where the table should be placed
     * @param numOfNodes The total number of nodes in the graph
     * @param highlight The look & feel of the table
     */
	public InfoTable(Language lang, GUIController controller,
			AODVNode nodeForThisTable, Coordinates startPoint, int numOfNodes, RectProperties highlight) {

		super(lang, startPoint,highlight);
		this.controller = controller;
		this.ownNode = nodeForThisTable;
		this.numOfNodes = numOfNodes;
        tableTitle = controller.getTranslator().translateMessage("node") + ": " + ownNode.getNodeIdentifier();
        cellHeight = 15;
        cellWidth = 30;
        columnTitles = new String[] { "N", "DS", "HC", "NH" };
		this.height = cellHeight * (this.numOfNodes + 1);
		this.tableEntries = new ArrayList<InfoTableEntry>();
		initContent();
	}

    /**
     * Initialize this instance.
     */
	private void initContent() {
		drawTitles(numOfNodes +1);

        // Create new entries for every row
		for (int row = 0; row < numOfNodes; row++) {
			tableEntries.add(new InfoTableEntry(lang, currentLine, ownNode.getRoutingTable().get(row),
                    cellWidth, cellHeight, highlight));
			nextLine();
		}
	}

    /**
     * Update the InfoTable. This checks if any fields have changed, and repaints them if necessary.
     */
	public void updateTable() {
		controller.tableRefresh();

		ArrayList<RoutingTableEntry> currentRoutingTable = ownNode.getRoutingTable();

		if (currentRoutingTable.size() != tableEntries.size()) {
			System.err.println("There are only " + currentRoutingTable.size()
					+ " RoutingTable entries, but must be "
					+ tableEntries.size());
		}

		boolean updated = false;

		for (int entry = 0; entry < currentRoutingTable.size(); entry++) {
			InfoTableEntry infoTableEntry = tableEntries.get(entry);
			RoutingTableEntry routingTableEntry = currentRoutingTable.get(entry);

            if (infoTableEntry.updateInfoTableEntry(routingTableEntry)) {
				updated = true;
			}
		}

		if (updated) {
			controller.tableUpdated(this);
		}
	}

    /**
     * Reset the current highlighting in the table.
     */
	public void refresh() {
		for (InfoTableEntry entry : tableEntries) {
			entry.unhighlight();
		}
	}

    /**
     * @return the height
     */
	public int getHeight() {
		return height;
	}

    /**
     *
     * @return the width
     */
	public int getWidth() {
		return cellWidth * columnTitles.length;
	}

}