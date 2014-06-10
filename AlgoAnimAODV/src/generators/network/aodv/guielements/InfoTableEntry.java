package generators.network.aodv.guielements;

import generators.graphics.helpers.Coordinate;
import generators.network.aodv.RoutingTableEntry;
import algoanim.primitives.Rect;
import algoanim.primitives.Text;
import algoanim.primitives.generators.Language;
import algoanim.util.Coordinates;

public class InfoTableEntry extends GUIPositionElement {

	private InfoTableCell nodeIDCell;
	private InfoTableCell destSeqCell;
	private InfoTableCell hopCountCell;
	private InfoTableCell nextHopCell;

	public InfoTableEntry(Language lang, Coordinates position,
			int coloumnWidth, int rowHeight) {
		super(lang, position);
		this.nodeIDCell = new InfoTableCell(lang, "-", position,
				coloumnWidth, rowHeight);
		this.destSeqCell = new InfoTableCell(lang, "-",
				GeometryToolBox.moveCoordinate(position, coloumnWidth, 0),
				coloumnWidth, rowHeight);
		this.hopCountCell = new InfoTableCell(lang, "-",
				GeometryToolBox.moveCoordinate(position, coloumnWidth * 2, 0),
				coloumnWidth, rowHeight);
		this.nextHopCell = new InfoTableCell(lang, "-",
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

	private boolean checkCellForUpdate(String text, InfoTableCell cell) {
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
