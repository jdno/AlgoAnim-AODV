package generators.network.aodv;

import generators.framework.Generator;
import generators.framework.GeneratorType;
import generators.framework.properties.AnimationPropertiesContainer;

import java.awt.Color;
import java.util.Hashtable;
import java.util.Locale;

import algoanim.animalscript.AnimalScript;
import algoanim.primitives.Graph;
import algoanim.primitives.generators.Language;
import algoanim.properties.AnimationPropertiesKeys;
import algoanim.properties.GraphProperties;
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

		InfoTable table1 = new InfoTable(lang, new AODVNode("A"),
				(new Coordinates(350, 50)), adjacencyMatrix);

		// drawTable(new Coordinates(580,50));
		// drawTable(new Coordinates(350,250));
		// drawTable(new Coordinates(580,250));

		InfoBox info = new InfoBox(lang, "Erläuterung",
				new Coordinates(40, 420), new Coordinates(660, 600));
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

	// TODO refactor as static method in some helper class, and remove from
	// InfoBox/InfoTable
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
