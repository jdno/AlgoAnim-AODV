package generators.network.aodv.guielements;

import algoanim.primitives.Graph;
import algoanim.properties.AnimationPropertiesKeys;
import algoanim.properties.RectProperties;
import algoanim.properties.TextProperties;
import generators.network.aodv.AODVNode;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import algoanim.primitives.generators.Language;
import algoanim.util.Coordinates;
import generators.network.aodv.AODVNodeListener;
import generators.network.aodv.guielements.Tables.InfoTable;
import generators.network.aodv.guielements.Tables.StatisticTable;
import translator.Translator;

public class GUIController implements AODVNodeListener{

	private  Language lang;
    private Translator translator;

    /**
     * AODV Nodes with corresponding InfoTable objects
     */
	private HashMap<AODVNode, InfoTable> tables;
    private ArrayList<InfoTable> lastUpdated;


    /**
     * Coordinates for the GUI Elements
     */
	private final Coordinates infoBoxUpperLeft = new Coordinates(40, 470);
	private final Coordinates infoBoxLowerRight = new Coordinates(840, 600);
	private final Coordinates tableStartingPont = new Coordinates(500, 20);
    private final Coordinates statisticTableStartingPoint = new Coordinates(500,400);
	private final int distanceBetweenTables = 30;

    /**
     * GUI Elements
     */
    private StatisticTable statTable;
    private InfoBox info;
    private GUIGraph graph;

    private RectProperties highlightCellProps;

	public GUIController(Language language, Graph animalGraph,Translator translator,RectProperties rectProps) {
		lang = language;
		tables = new HashMap<AODVNode, InfoTable>();
		lastUpdated = new ArrayList<InfoTable>();
        graph = new GUIGraph(lang,animalGraph);
        this.translator = translator;
        this.highlightCellProps = rectProps;
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
				tableStartingPont, nodes.size(), highlightCellProps);
		int offsetX = distanceBetweenTables + table.getWidth();
		int offsetY = distanceBetweenTables + table.getHeight();

		for (int i = 1; i < nodes.size(); i++) {
			table = new InfoTable(lang, this, nodes.get(i),
					(GeometryToolBox.moveCoordinate(tableStartingPont, i
							% numOfTablesX * offsetX, i / numOfTablesX
							* offsetY)), nodes.size(), highlightCellProps);
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

    public void showStartPage(){
        TextProperties bigTitle = new TextProperties();
        bigTitle.set(AnimationPropertiesKeys.FONT_PROPERTY, new Font(
                Font.SANS_SERIF, Font.PLAIN, 25));
        bigTitle.set(AnimationPropertiesKeys.COLOR_PROPERTY,Color.ORANGE);

        TextProperties title = new TextProperties();
        title.set(AnimationPropertiesKeys.FONT_PROPERTY, new Font(
                Font.SANS_SERIF, Font.PLAIN, 20));
        title.set(AnimationPropertiesKeys.COLOR_PROPERTY,Color.ORANGE);


        lang.newText(new Coordinates(50,30),translator.translateMessage("startPageTitle"),"startTitle",null,bigTitle);

        lang.newText(new Coordinates(50,50),translator.translateMessage("algoTitle"),"algoTitle",null,title);
        lang.newText(new Coordinates(50,70),translator.translateMessage("animDesc"),"Description",null);

        lang.newText(new Coordinates(50,200),translator.translateMessage("startFunctionality"),"startF",null,title);
        lang.newText(new Coordinates(50,220),translator.translateMessage("aodvFunc"),"Description",null);

        lang.newText(new Coordinates(50,400),translator.translateMessage("startAnimation"),"startA",null,title);
        lang.newText(new Coordinates(50,420),translator.translateMessage("aodvAnimation"),"Animation",null);
        lang.nextStep();
    }

    public void hideStartPage(){
        lang.hideAllPrimitives();
        lang.nextStep();
    }


    public void drawStatisticTable(String title){
         statTable = new StatisticTable(lang,statisticTableStartingPoint, title, highlightCellProps);
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
