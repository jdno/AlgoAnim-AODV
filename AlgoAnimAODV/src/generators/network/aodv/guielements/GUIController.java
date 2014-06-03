package generators.network.aodv.guielements;

import generators.network.aodv.AODVNode;

import java.util.ArrayList;
import java.util.HashMap;

import algoanim.primitives.generators.Language;
import algoanim.util.Coordinates;

public class GUIController {

	private static Language lang;
	private HashMap<AODVNode, InfoTable> tables;
	private InfoBox info;
	private Coordinates infoBoxUpperLeft = new Coordinates(40, 470);
	private Coordinates infoBoxLowerRight = new Coordinates(840, 600);
	private Coordinates tableStartingPont = new Coordinates(500, 20);
	private int distanceBetweenTables = 30;
	private ArrayList<InfoTable> lastUpdated;

	public GUIController(Language language) {
		lang = language;
		tables = new HashMap<AODVNode, InfoTable>();
		lastUpdated = new ArrayList<InfoTable>();
	}

	public void drawInfoTable(ArrayList<AODVNode> nodes) {

		/**
		 * Check how many tables have to be drawn in a row
		 */
		int numOfTablesX = (int) Math.round((nodes.size() + 0.5) / 2);

		/**
		 * Draw initial table in order to get the width and height for the
		 * following tables
		 */
		InfoTable table = new InfoTable(lang, this, nodes.get(0),
				tableStartingPont, nodes.size());
		int offsetX = distanceBetweenTables + table.getWidth();
		int offsetY = distanceBetweenTables + table.getHeight();

		for (int i = 1; i < nodes.size(); i++) {
			table = new InfoTable(lang, this, nodes.get(i),
					(GeometryToolBox.moveCoordinate(tableStartingPont, i
							% numOfTablesX * offsetX, i / numOfTablesX
							* offsetY)), nodes.size());
			tables.put(nodes.get(i), table);
		}

	}

	public void highlightCell(AODVNode node, int cellX, int cellY) {
		tables.get(node).highlightCell(cellX, cellY, true);
	}

	public void unhighlightCell(AODVNode node, int cellX, int cellY) {
		tables.get(node).highlightCell(cellX, cellY, false);
	}

	public void drawInfoBox(String titleOfBox) {
		info = new InfoBox(lang, titleOfBox, infoBoxUpperLeft,
				infoBoxLowerRight);
	}

	public void updateInfoBoxText(String update) {
		info.updateText(update);
	}

	public void tableUpdated(InfoTable table) {
		lastUpdated.add(table);
	}

	public void tableRefresh() {
			for (InfoTable table : lastUpdated) {
				table.refresh();
			}
			lastUpdated.clear();
		}
	

}
