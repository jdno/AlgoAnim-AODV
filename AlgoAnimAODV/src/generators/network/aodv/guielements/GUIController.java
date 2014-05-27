package generators.network.aodv.guielements;

import generators.network.aodv.AODVNode;

import java.util.ArrayList;
import java.util.HashMap;

import algoanim.primitives.generators.Language;
import algoanim.util.Coordinates;

public class GUIController {

	private static Language lang;
	private HashMap<AODVNode,InfoTable> tables;
	private InfoBox info;
	private Coordinates infoBoxUpperLeft = new Coordinates(40,420);
	private Coordinates infoBoxLowerRight = new Coordinates(660,600);
	private Coordinates tableStartingPont = new Coordinates(500,20);
	
	public GUIController(Language language){
		lang = language;
		tables = new HashMap<AODVNode,InfoTable>();
	}
	
	public void drawInfoTable(ArrayList<AODVNode> nodes, int numOfTablesX, int numOfTablesY){
		if (numOfTablesX * numOfTablesY != nodes.size()){
			System.err.println("Number of nodes for table does not fit the target number of tables");
		}
		for (int y = 0; y < numOfTablesY; y++){
			for (int x = 0; x < numOfTablesX; x++){
			InfoTable table = new InfoTable(lang, nodes.get(x+(y*(numOfTablesX))),
					(GeometryToolBox.moveCoordinate(tableStartingPont,x*150,y*180)), nodes.size());
			tables.put(nodes.get(x+y),table);
			}
		}
	}
	
	public void highlightCell(AODVNode node, int cellX, int cellY){
		tables.get(node).highlightCell(cellX, cellY, true);
	}
	
	public void unhighlightCell(AODVNode node, int cellX, int cellY){
		tables.get(node).highlightCell(cellX, cellY, false);
	}
	
	public void drawInfoBox(String titleOfBox){
		info = new InfoBox(lang, titleOfBox,
				infoBoxUpperLeft, infoBoxLowerRight);
	}
	
	public void updateInfoBoxText(String update){
		info.updateText(update);
	}
	
}
