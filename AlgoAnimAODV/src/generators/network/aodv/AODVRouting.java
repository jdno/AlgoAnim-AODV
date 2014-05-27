package generators.network.aodv;

import generators.framework.Generator;
import generators.framework.GeneratorType;
import generators.framework.properties.AnimationPropertiesContainer;
import generators.network.aodv.guielements.GUIController;
import generators.network.aodv.guielements.GeometryToolBox;
import generators.network.aodv.guielements.InfoBox;
import generators.network.aodv.guielements.InfoTable;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Locale;

import algoanim.animalscript.AnimalScript;
import algoanim.primitives.Graph;
import algoanim.primitives.generators.Language;
import algoanim.properties.AnimationPropertiesKeys;
import algoanim.properties.GraphProperties;
import algoanim.util.Coordinates;
import algoanim.util.Node;
import animal.vhdl.logic.test;

// TODO: Tabellen anzeigen
// TODO: Private Klasse für Tabellen
// TODO: Graphen Properties erkunden
// TODO: Infobox anzeigen und verändern

public class AODVRouting implements Generator {
	private Language lang;
	private int[][] adjacencyMatrix;
	private GUIController controller;

	Color highlightColor = Color.ORANGE;

	public AODVRouting(Language language) {
		this.lang = language;
	}

	public AODVRouting() {
		lang = new AnimalScript("Ad-hoc Optimized Vector Routing",
				"Sascha Bleidner, Jan David Nose", 1200, 800);
		controller = new GUIController(lang);
		GeometryToolBox.init(lang);
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

		ArrayList<AODVNode> nodes = new ArrayList<AODVNode>();
		nodes.add(new AODVNode("A"));
		nodes.add(new AODVNode("B"));
		nodes.add(new AODVNode("C"));
		nodes.add(new AODVNode("D"));
		nodes.add(new AODVNode("E"));
		nodes.add(new AODVNode("F"));
		nodes.add(new AODVNode("G"));
		nodes.add(new AODVNode("H"));
		
		controller.drawInfoTable(nodes, 4, 2);


	
		// drawTable(new Coordinates(580,50));
		// drawTable(new Coordinates(350,250));
		// drawTable(new Coordinates(580,250));

		controller.drawInfoBox("Erläuterung");
		controller.updateInfoBoxText("Hallo");
		lang.nextStep();
		controller.updateInfoBoxText("Wuhuuu");
		lang.nextStep();
		
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
