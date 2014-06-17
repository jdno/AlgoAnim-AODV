package generators.network.aodv.guielements;

import algoanim.primitives.Graph;
import generators.network.aodv.AODVGraph;
import generators.network.aodv.AODVNode;

import java.util.ArrayList;
import java.util.HashMap;

import algoanim.primitives.generators.Language;
import algoanim.util.Coordinates;
import generators.network.aodv.AODVNodeListener;

public class GUIController implements AODVNodeListener{

	private static Language lang;
	private HashMap<AODVNode, InfoTable> tables;
	private InfoBox info;
	private Coordinates infoBoxUpperLeft = new Coordinates(40, 470);
	private Coordinates infoBoxLowerRight = new Coordinates(840, 600);
	private Coordinates tableStartingPont = new Coordinates(500, 20);
    private Coordinates statisticTableStartingPoint = new Coordinates(500,400);
	private int distanceBetweenTables = 30;
    private StatisticTable statTable;
	private ArrayList<InfoTable> lastUpdated;
    private GUIGraph graph;

	public GUIController(Language language, Graph animalGraph) {
		lang = language;
		tables = new HashMap<AODVNode, InfoTable>();
		lastUpdated = new ArrayList<InfoTable>();
        graph = new GUIGraph(lang,animalGraph);
	}

	public void drawGUIGraph(){
        graph.show();
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

    public Graph getAnimalGraph(){
        return graph.getAnimalGraph();
    }

    public void drawStatisticTable(){
         statTable = new StatisticTable(lang,statisticTableStartingPoint);
    }

    @Override
    public void updateInfoTable(AODVNode node) {
        if (tables.get(node) != null) {
            tables.get(node).updateTable();
            statTable.updateStatisticTable();
        }
    }

    @Override
    public void highlightNode(AODVNode node) {
        graph.unHighlightLastChange();
        graph.highlightNode(node);
    }

    @Override
    public void highlightEgde(AODVNode startNode, AODVNode endNode) {
        graph.unHighlightLastChange();
        graph.highlightEdge(startNode,endNode);
    }


}
