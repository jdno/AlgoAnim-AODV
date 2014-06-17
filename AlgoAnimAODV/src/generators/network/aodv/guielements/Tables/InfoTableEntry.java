package generators.network.aodv.guielements.Tables;

import generators.network.aodv.RoutingTableEntry;
import algoanim.primitives.generators.Language;
import algoanim.util.Coordinates;
import generators.network.aodv.guielements.GUIPositionElement;
import generators.network.aodv.guielements.GeometryToolBox;

public class InfoTableEntry extends GUIPositionElement {

	private TableCell nodeIDCell;
	private TableCell destSeqCell;
	private TableCell hopCountCell;
	private TableCell nextHopCell;

	public InfoTableEntry(Language lang, Coordinates position,
			int coloumnWidth, int rowHeight) {
		super(lang, position);
		this.nodeIDCell = new TableCell(lang, "-", position,
				coloumnWidth, rowHeight);
		this.destSeqCell = new TableCell(lang, "-",
				GeometryToolBox.moveCoordinate(position, coloumnWidth, 0),
				coloumnWidth, rowHeight);
		this.hopCountCell = new TableCell(lang, "-",
				GeometryToolBox.moveCoordinate(position, coloumnWidth * 2, 0),
				coloumnWidth, rowHeight);
		this.nextHopCell = new TableCell(lang, "-",
				GeometryToolBox.moveCoordinate(position, coloumnWidth * 3, 0),
				coloumnWidth, rowHeight);
	}

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

	private boolean checkCellForUpdate(String text, TableCell cell) {
		if (!text.equals(cell.getText())) {
			cell.setText(text);
			cell.highlightCell();
			return true;
		}
		return false;
	}

	public void unhighlight() {
		nodeIDCell.unhighlightCell();
		destSeqCell.unhighlightCell();
		hopCountCell.unhighlightCell();
		nextHopCell.unhighlightCell();
	}

}
