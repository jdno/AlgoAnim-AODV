package generators.network.aodv;

import generators.framework.Generator;
import generators.framework.GeneratorType;
import generators.framework.properties.AnimationPropertiesContainer;
import generators.network.aodv.AODVMessage.MessageType;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Locale;

import algoanim.animalscript.AnimalScript;
import algoanim.exceptions.NotEnoughNodesException;
import algoanim.primitives.Graph;
import algoanim.primitives.Rect;
import algoanim.primitives.Text;
import algoanim.primitives.generators.Language;
import algoanim.properties.AnimationPropertiesKeys;
import algoanim.properties.GraphProperties;
import algoanim.properties.RectProperties;
import algoanim.properties.TextProperties;
import algoanim.util.Coordinates;
import algoanim.util.Node;

// TODO: Tabellen anzeigen
// TODO: Private Klasse für Tabellen
// TODO: Graphen Properties erkunden
// TODO: Infobox anzeigen und verändern

public class AODVRouting implements Generator {
	private Language lang;
	private int[][] adjacencyMatrix;

	Color highlightColor = Color.ORANGE;

	public AODVRouting(Language language) {
		this.lang = language;
	}

	public AODVRouting() {
		lang = new AnimalScript("Ad-hoc Optimized Vector Routing",
				"Sascha Bleidner, Jan David Nose", 1200, 800);
	}

	public void init() {
		lang.setStepMode(true);
	}

	public String generate(AnimationPropertiesContainer props,
			Hashtable<String, Object> primitives) {
		adjacencyMatrix = (int[][]) primitives.get("adjacencyMatrix");

		GraphProperties graphProps = new GraphProperties();
		graphProps.set(AnimationPropertiesKeys.HIGHLIGHTCOLOR_PROPERTY,
				highlightColor);
		graphProps.set(AnimationPropertiesKeys.FILL_PROPERTY, Color.WHITE);
		graphProps.set(AnimationPropertiesKeys.DIRECTED_PROPERTY, true);
		graphProps.set(AnimationPropertiesKeys.EDGECOLOR_PROPERTY, Color.GREEN);

		Graph defaultGraph = getDefaultGraph(graphProps);

		defaultGraph.show();

		defaultGraph.highlightNode(0, null, null);
		defaultGraph.highlightNode(3, null, null);
		defaultGraph.highlightEdge(0, 1, null, null);
		// defaultGraph.showNode(2, null, null);
		// defaultGraph.show();
		lang.nextStep();

		Table table1 = new Table(new AODVNode("A"), (new Coordinates(350, 50)));

		// drawTable(new Coordinates(580,50));
		// drawTable(new Coordinates(350,250));
		// drawTable(new Coordinates(580,250));

		InfoBox info = new InfoBox(lang, "Erläuterung", new Coordinates(40, 420),
				new Coordinates(660, 600));
		info.updateText("Hallo");
		lang.nextStep();
		info.updateText("Wuhuuu");
		return lang.toString();
	}

	private Graph getDefaultGraph(GraphProperties graphProps) {
		Graph defaultGraph;

		Node[] nodes = new Node[4];
		nodes[0] = new Coordinates(40, 100);
		nodes[1] = new Coordinates(40, 250);
		nodes[2] = new Coordinates(190, 100);
		nodes[3] = new Coordinates(190, 250);

		String[] graphLabels = { "A", "B", "C", "D" };

		defaultGraph = lang.newGraph("AODV-Graph", adjacencyMatrix, nodes,
				graphLabels, null, graphProps);
		defaultGraph.hide();

		return defaultGraph;
	}

	private class Table {

		int verticalMove = 15;
		int distanceColumns = 30;
		int numRows = 3;
		int height = verticalMove * numRows * 2;

		String[] titles = new String[] { "N", "DS", "HC", "NH" };
		ArrayList<ArrayList<Text>> content;
		ArrayList<ArrayList<Rect>> cells;

		Coordinates startPoint;
		Coordinates currentLine;
		AODVNode ownNode;

		public Table(AODVNode ownNode, Coordinates startPoint) {

			this.ownNode = ownNode;
			this.startPoint = startPoint;
			this.currentLine = startPoint;
			initContent();
		}

		private void initContent() {

			int numNodes = adjacencyMatrix[0].length;
			this.content = new ArrayList<ArrayList<Text>>();
			 
			lang.newText(startPoint, "Node: " + ownNode.getNodeIdentifier(), "Tablename", null);
			
			nextLine();
			for (int i = 0; i <= numRows; i++) {
				lang.newText(
						moveCoordinate(currentLine, distanceColumns * i,
								0), titles[i], "", null);
				if (i != 0) {
					getVerticalLine(
							moveCoordinate(currentLine, distanceColumns * i - 5,
									0), height);
				}
			}
			// switch to the next line
			nextLine();
			getHorizontalLine(currentLine, distanceColumns*(numRows+1));
			for (int i = 0; i < numNodes; i++) {
				ArrayList<Text> lineContent = new ArrayList<Text>();

				for (int z = 0; z <= numRows; z++) {
					lineContent.add(lang.newText(
							moveCoordinate(currentLine, distanceColumns * z,0), "A", "", null));
				}
				content.add(lineContent);
				nextLine();
			}

		}
		
		private void nextLine(){
			currentLine = moveCoordinate(currentLine,0,verticalMove);
		}

		public void updateTable() {
			ArrayList<RoutingTableEntry> currentRoutingTable = ownNode
					.getRoutingTable();
			// for (int i = 0; i < currentRoutingTable.size(); i++){
			//
			// lang.newText(upperLeft, text, name,
			// display)currentEntry.getNodeIdentifier()
			// }

		}

		private algoanim.primitives.Polygon getVerticalLine(
				Coordinates startPoint, int length) {
			return getPolygon(startPoint, length, true);
		}

		private algoanim.primitives.Polygon getHorizontalLine(
				Coordinates startPoint, int length) {
			return getPolygon(startPoint, length, false);
		}

		private algoanim.primitives.Polygon getPolygon(Coordinates startPoint,
				int length, boolean vertical) {

			Node[] nodes = new Node[2];
			nodes[0] = startPoint;
			if (vertical) {
				nodes[1] = new Coordinates(startPoint.getX(), startPoint.getY()
						+ length);
			} else {
				nodes[1] = new Coordinates(startPoint.getX() + length,
						startPoint.getY());
			}

			algoanim.primitives.Polygon line = null;

			try {
				line = lang.newPolygon(nodes, "line", null);
			} catch (NotEnoughNodesException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return line;
		}

	}

	private Coordinates moveCoordinate(Coordinates point, int x, int y) {
		return new Coordinates(point.getX() + x, point.getY() + y);
	}

	public String getName() {
		return "Ad-hoc Optimized Vector Routing";
	}

	public String getAlgorithmName() {
		return "AODV";
	}

	public String getAnimationAuthor() {
		return "Sascha Bleidner, Jan David Nose";
	}

	public String getDescription() {
		return "AODV ist ein reaktiver Routingalgorithmus für mobile Ad-hoc Netze.";
	}

	public String getCodeExample() {
		return "CodeExample";
	}

	public String getFileExtension() {
		return "asu";
	}

	public Locale getContentLocale() {
		return Locale.GERMAN;
	}

	public GeneratorType getGeneratorType() {
		return new GeneratorType(GeneratorType.GENERATOR_TYPE_MORE);
	}

	public String getOutputLanguage() {
		return Generator.JAVA_OUTPUT;
	}

	
	
	

	
}
