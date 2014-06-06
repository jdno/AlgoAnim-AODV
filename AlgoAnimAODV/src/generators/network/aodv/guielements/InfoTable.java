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

public class InfoTable extends GUIElement{


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
	 * Shortcut for the number of rows in the table
	 */
	private int numRows = titles.length;

	/**
	 * Shortcut for the approx. height of the whole table.
	 */
	private int height;

	/**
	 * Represents the currentLine in the GUI
	 */
	private Coordinates currentLine;
	
	private ArrayList<InfoTableEntry> tableEntries;


	AODVNode ownNode;
	int numNodes;
	private GUIController controller;

	public InfoTable(Language lang, GUIController controller,
			AODVNode nodeForThisTable, Coordinates startPoint, int numOfNodes) {
		super(lang,startPoint);
		this.controller = controller;
		this.ownNode = nodeForThisTable;
		this.ownNode.addTable(this);
		this.currentLine = startPoint;
		this.numNodes = numOfNodes;
		height = cellHeight * (numNodes + 1);
		this.tableEntries = new ArrayList<InfoTableEntry>();
		initContent();
	}

	private void initContent() {
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
		
		for(int row = 0; row < numNodes; row++){
			tableEntries.add(new InfoTableEntry(lang, "A", currentLine, distanceColumns, cellHeight));
			nextLine();
		}

	}

	private void nextLine() {
		currentLine = GeometryToolBox
				.moveCoordinate(currentLine, 0, cellHeight);
	}
	
	public void updateTable(){
		controller.tableRefresh();
		ArrayList<RoutingTableEntry> currentRoutingTable = ownNode.getRoutingTable();
		
		if (currentRoutingTable.size() != tableEntries.size()){
			System.err.println("There are only "+ currentRoutingTable.size() + " RoutingTable entries, but must be " + tableEntries.size());
		}
		
		boolean updated = false;
		
		for (int entry = 0; entry < currentRoutingTable.size(); entry++){
			InfoTableEntry infoTableEntry = tableEntries.get(entry);
			RoutingTableEntry routingTableEntry = currentRoutingTable.get(entry);
			if (infoTableEntry.updateInfoTableEntry(routingTableEntry)){
				updated = true;
			}
		}
		if (updated) {
		controller.tableUpdated(this);
	}
	lang.nextStep();
	}
	
	

//	public void updateTable() {
//		controller.tableRefresh();
//		ArrayList<RoutingTableEntry> currentRoutingTable = ownNode
//				.getRoutingTable();
//		boolean updated = false;
//		for (int i = 0; i < textContent.size(); i++) {
//			System.out.println("RoutingTableEntries: " +currentRoutingTable.size());
//			if (i < currentRoutingTable.size()) {
//				if (currentRoutingTable.get(i) != null) {
//					// check node identifier
//					String nodeID = currentRoutingTable.get(i).getIdentifier();
//					if (!nodeID.equals(textContent.get(i).get(0).getText())) {
//						textContent.get(i).get(0).setText(nodeID, null, null);
//						highlightCell(i, 0, true);
//						lastChanged.add(new Point(i, 0));
//						updated = true;
//
//					}
//
//					// check destination sequence number
//					String DestinationSeq = Integer
//							.toString(currentRoutingTable.get(i)
//									.getDestinationSequence());
//					if (!DestinationSeq.equals(textContent.get(i).get(1)
//							.getText())) {
//						textContent.get(i).get(1)
//								.setText(DestinationSeq, null, null);
//						highlightCell(i, 1, true);
//						lastChanged.add(new Point(i, 1));
//						updated = true;
//
//					}
//
//					// check hop count
//					int hopCount = currentRoutingTable.get(i).getHopCount();
//					if (!Integer.toString(hopCount).equals(
//							textContent.get(i).get(2).getText())) {
//						String hopString;
//						if (hopCount == Integer.MAX_VALUE) {
//							hopString = "inf";
//						} else {
//							hopString = Integer.toString(hopCount);
//						}
//
//						textContent.get(i).get(2)
//								.setText(hopString, null, null);
//						highlightCell(i, 2, true);
//						lastChanged.add(new Point(i, 2));
//						updated = true;
//
//					}
//
//					// check next hop
//					String nextHop = currentRoutingTable.get(i).getNextHop();
//					if (!nextHop.equals(textContent.get(i).get(3).getText())) {
//						textContent.get(i).get(3).setText(nextHop, null, null);
//						highlightCell(i, 3, true);
//						lastChanged.add(new Point(i, 3));
//						updated = true;
//					}
//				}
//			}
//		}
//		if (updated) {
//			controller.tableUpdated(this);
//		}
//		lang.nextStep();
//	}


	public void refresh() {
		for (InfoTableEntry entry : tableEntries){
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
		return distanceColumns * numRows;
	}

}