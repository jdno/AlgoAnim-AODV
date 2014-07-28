package generators.network.aodv.guielements.Tables;

import algoanim.properties.RectProperties;
import generators.network.aodv.AODVNode;
import generators.network.aodv.RoutingTableEntry;
import algoanim.primitives.generators.Language;
import algoanim.util.Coordinates;
import generators.network.aodv.guielements.GUIElement;
import generators.network.aodv.guielements.GeometryToolBox;

/**
 * This class represents a row in the InfoTable. A row has four columns: node identifier,
 * the node's destination sequence, the hop count and the next hop to the node.
 *
 * @author Sascha Bleidner, Jan David Nose
 */
public class InfoTableEntry extends GUIElement {

    /**
     * The cell for the node identifier
     */
	private TableCell nodeIDCell;

    /**
     * The cell for the destination sequence
     */
	private TableCell destSeqCell;

    /**
     * The cell for the hop count
     */
	private TableCell hopCountCell;

    /**
     * The cell for the next hop
     */
	private TableCell nextHopCell;

    /**
     * Create a new instance of InfoTableEntry.
     *
     * @param lang The language object
     * @param position The position of the row
     * @param entry The routing table entry to print
     * @param columnWidth The width of a column
     * @param rowHeight The height of a row
     * @param highlight The look & feel of the table
     */
	public InfoTableEntry(Language lang, Coordinates position, RoutingTableEntry entry,
			int columnWidth, int rowHeight, RectProperties highlight) {

        super(lang, position);

		this.nodeIDCell = new TableCell(lang, entry.getIdentifier(), position,
				columnWidth, rowHeight,highlight);
		this.destSeqCell = new TableCell(lang, Integer.toString(entry.getDestinationSequence()),
				GeometryToolBox.moveCoordinate(position, columnWidth, 0),
				columnWidth, rowHeight,highlight);
		this.hopCountCell = new TableCell(lang, "inf",
				GeometryToolBox.moveCoordinate(position, columnWidth * 2, 0),
				columnWidth, rowHeight,highlight);
		this.nextHopCell = new TableCell(lang, entry.getNextHop(),
				GeometryToolBox.moveCoordinate(position, columnWidth * 3, 0),
				columnWidth, rowHeight,highlight);
	}

    /**
     * Update the InfoTableEntry. This checks if anything has changed, updates it accordingly and
     * highlights the cell.
     *
     * @param entry The routing table entry to print
     * @return True if any changes were made, false otherwise
     */
	public boolean updateInfoTableEntry(RoutingTableEntry entry) {
		boolean updated = false;

		String currentString = entry.getIdentifier();

		if (checkCellForUpdate(currentString, nodeIDCell)) {
			updated = true;
		}

		currentString = Integer.toString(entry.getDestinationSequence());
		if (checkCellForUpdate(currentString, destSeqCell)) {
			updated = true;
		}

		currentString = Integer.toString(entry.getHopCount());
		if (entry.getHopCount() == Integer.MAX_VALUE) {
            if (!hopCountCell.getText().equals("inf")) {
                hopCountCell.setText("inf");
                hopCountCell.highlightCell();
                updated = true;
            }
		} else {
			if (checkCellForUpdate(currentString, hopCountCell)) {
				updated = true;
			}
		}

		currentString = entry.getNextHop();
		if (checkCellForUpdate(currentString, nextHopCell)) {
			updated = true;
		}
		return updated;

	}

    /**
     * Checks whether the content of a cell has changed. If so, it automatically
     * updates it!
     *
     * @param text The current text in the routing table entry
     * @param cell The cell to highlight if anything changed
     * @return True if changes were made, false otherwise
     */
	private boolean checkCellForUpdate(String text, TableCell cell) {
		if (!text.equals(cell.getText())) {
			cell.setText(text);
			cell.highlightCell();
			return true;
		}
		return false;
	}

    /**
     * Reset all hightligts
     */
	public void unhighlight() {
		nodeIDCell.unhighlightCell();
		destSeqCell.unhighlightCell();
		hopCountCell.unhighlightCell();
		nextHopCell.unhighlightCell();
	}

}
