package generators.network;

import generators.framework.Generator;
import generators.framework.GeneratorType;
import generators.framework.properties.AnimationPropertiesContainer;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import algoanim.animalscript.AnimalScript;
import algoanim.primitives.Graph;
import algoanim.primitives.Rect;
import algoanim.primitives.SourceCode;
import algoanim.primitives.Text;
import algoanim.primitives.generators.Language;
import algoanim.properties.AnimationPropertiesKeys;
import algoanim.properties.GraphProperties;
import algoanim.properties.RectProperties;
import algoanim.properties.SourceCodeProperties;
import algoanim.properties.TextProperties;
import algoanim.util.Coordinates;
import algoanim.util.Node;
import algoanim.util.Offset;

public class AODVRoutingGenerator implements Generator{

	  /**
	   * The concrete language object used for creating output
	   */
	  private Language             language;

	  /**
	   * The header text including the headline
	   */
	  private Text                 header;

	  /**
	   * The rectangle around the headline
	   */
	  private Rect                 hRect;

	  /**
	   * Globally defined text properties
	   */
	  private TextProperties       textProps;

	  /**
	   * the source code shown in the animation
	   */
	  private SourceCode           src;

	  /**
	   * Globally defined source code properties
	   */
	  private SourceCodeProperties sourceCodeProps;

	  public void init() {
	    
	  }

	  /**
	   * Default constructor
	   * 
	   * @param l
	   *          the concrete language object used for creating output
	   */
	  public AODVRoutingGenerator(Language language) {
	    this.language = language;
	    // This initializes the step mode. Each pair of subsequent steps has to
	    // be divdided by a call of lang.nextStep();
	    language.setStepMode(true);
	  }

	  public AODVRoutingGenerator() {
	    language = new AnimalScript("Prim [DE]", "Irina Smidt, Simon Sprankel",
	        800, 600);
	    language.setStepMode(true);
	  }

	  /**
	   * Initializes the animation. Shows a start page with a description. Then,
	   * shows the graph and the source code and calls the prim algorithm.
	   * 
	   * @param graph
	   *          the graph
	   */
	  public void start(Graph graph) {
	    graph.hide();
	    // show the header with a heading surrounded by a rectangle
	    TextProperties headerProps = new TextProperties();
	    headerProps.set(AnimationPropertiesKeys.FONT_PROPERTY, new Font(
	        Font.SANS_SERIF, Font.BOLD, 24));
	    header = language.newText(new Coordinates(20, 30), "Algorithmus von Prim",
	        "header", null, headerProps);
	    RectProperties rectProps = new RectProperties();
	    rectProps.set(AnimationPropertiesKeys.FILLED_PROPERTY, true);
	    rectProps.set(AnimationPropertiesKeys.FILL_PROPERTY, Color.WHITE);
	    rectProps.set(AnimationPropertiesKeys.DEPTH_PROPERTY, 2);
	    hRect = language.newRect(new Offset(-5, -5, "header",
	        AnimalScript.DIRECTION_NW), new Offset(5, 5, "header", "SE"), "hRect",
	        null, rectProps);

	    // setup the start page with the description
	    language.nextStep();
	    textProps = new TextProperties();
	    textProps.set(AnimationPropertiesKeys.FONT_PROPERTY, new Font(
	        Font.SANS_SERIF, Font.PLAIN, 16));
	    language.newText(new Coordinates(10, 100),
	        "Der Algorithmus von Prim berechnet den minimalen Spannbaum eines",
	        "description1", null, textProps);
	    language.newText(new Offset(0, 25, "description1",
	        AnimalScript.DIRECTION_NW),
	        "zusammenhängenden und kantengewichteten Graphen. Ein minimaler",
	        "description2", null, textProps);
	    language.newText(new Offset(0, 25, "description2",
	        AnimalScript.DIRECTION_NW),
	        "Spannbaum ist ein Teilgraph, der alle Knoten des Ursprungsgraphen",
	        "description3", null, textProps);
	    language.newText(new Offset(0, 25, "description3",
	        AnimalScript.DIRECTION_NW),
	        "enthält und sie mit Kanten minimalen Gewichts verbindet.",
	        "description4", null, textProps);

	    language.nextStep();
	    language.newText(new Offset(0, 50, "description4",
	        AnimalScript.DIRECTION_NW),
	        "1. Gegeben sei ein Graph G mit Knotenmenge V und Kantenmenge E.",
	        "algo11", null, textProps);
	    language.newText(new Offset(25, 25, "algo11", AnimalScript.DIRECTION_NW),
	        "Wähle aus der Knotenmenge V einen beliebigen Startknoten v0",
	        "algo12", null, textProps);
	    language.newText(new Offset(0, 25, "algo12", AnimalScript.DIRECTION_NW),
	        "und füge ihn dem Spannbaum hinzu.", "algo13", null, textProps);

	    language.nextStep();
	    language.newText(new Offset(-25, 25, "algo13", AnimalScript.DIRECTION_NW),
	        "2. Betrachte alle Kanten, die von den Knoten aus dem Spannbaum",
	        "algo21", null, textProps);
	    language.newText(new Offset(25, 25, "algo21", AnimalScript.DIRECTION_NW),
	        "abgehen und füge die Kante mit geringstem Gewicht sowie den",
	        "algo22", null, textProps);
	    language.newText(new Offset(0, 25, "algo22", AnimalScript.DIRECTION_NW),
	        "Zielknoten zum Spannbaum hinzu.", "algo23", null, textProps);

	    language.nextStep();
	    language
	        .newText(
	            new Offset(-25, 25, "algo23", AnimalScript.DIRECTION_NW),
	            "3. Wiederhole Schritt 2, bis alle Knoten aus G im Spannbaum enthalten sind.",
	            "algo31", null, textProps);

	    // hide the description and show the graph and the code
	    language.nextStep();
	    // FIXME: this creates a blank page
	    language.hideAllPrimitives();
	    header.show();
	    hRect.show();
	    src = language.newSourceCode(new Coordinates(390, 50), "sourceCode",
	        null, sourceCodeProps);
	    src.addCodeLine("function prim(Set<Vertex> V, Set<Edge> E) {", null, 0,
	        null); // 0
	    src.addCodeLine("// Knoten und Kanten des Spannbaums", null, 1, null);
	    src.addCodeLine("Set<Vertex> Vs = {};", null, 1, null);
	    src.addCodeLine("Set<Edge> Es = {}", null, 1, null); // 3
	    src.addCodeLine("", null, 1, null); // 4
	    src.addCodeLine("// wähle beliebigen Startknoten", null, 1, null); // 5
	    src.addCodeLine("Vs.add(randomElement(V));", null, 1, null); // 6
	    src.addCodeLine("while (V.size() != Vs.size()) {", null, 1, null); // 7
	    src
	        .addCodeLine("List<Edge> possibleEdges = E.getEdges(Vs);", null, 2,
	            null); // 8
	    src.addCodeLine("// sortiere Kanten aufsteigend nach Gewichten", null, 2,
	        null); // 9
	    src.addCodeLine("possibleEdges.sort();", null, 2, null); // 10
	    src.addCodeLine("// wähle die Kante mit geringstem Gewicht", null, 2, null); // 11
	    src.addCodeLine("chosenEdge = possibleEdges.first();", null, 2, null); // 12
	    src.addCodeLine(
	        "// füge ausgewählte Kante und zugehörige Knoten zum Spannbaum hinzu",
	        null, 2, null); // 13
	    src.addCodeLine("Es.add(chosenEdge);", null, 2, null); // 14
	    src.addCodeLine("Vs.addAll(chosenEdge.getVertexes());", null, 2, null); // 15
	    src.addCodeLine("}", null, 1, null); // 16
	    src.addCodeLine("return Vs, Es;", null, 1, null); // 17
	    src.addCodeLine("}", null, 0, null); // 18

	    language.nextStep();
	    // call the prim algorithm
	    prim(graph);
	  }

	  /**
	   * Executes the prim algorithm on the given graph.
	   * 
	   * @param graph
	   *          the graph on which the prim algorithm should be executed
	   */
	  private void prim(Graph graph) {
	    graph.show();
	    // highlight the code lines with the initialization
	    src.highlight(0);
	    src.highlight(2);
	    src.highlight(3);
	    // show the nodes and edges already contained in the spanning tree
	    Text spannbaumKnoten = language.newText(new Coordinates(10, 470),
	        "Knoten des Spannbaums: { }", "spannbaumKnoten", null, textProps);
	    language.newText(new Offset(0, 25, "spannbaumKnoten",
	        AnimalScript.DIRECTION_NW), "Kanten des Spannbaums:",
	        "spannbaumKanten", null, textProps);
	    Text spannbaumKanten2 = language.newText(new Offset(10, 25,
	        "spannbaumKanten", AnimalScript.DIRECTION_NW), "{  }",
	        "spannbaumKanten2", null, textProps);
	    int graphWeight = 0;
	    Text graphWeightText = language.newText(new Offset(-10, 25,
	        "spannbaumKanten2", AnimalScript.DIRECTION_NW),
	        "Gesamtgewicht des Spannbaums: " + graphWeight, "spannbaumKanten2",
	        null, textProps);

	    // pick a random start node and highlight it
	    language.nextStep();
	    src.unhighlight(0);
	    src.unhighlight(2);
	    src.unhighlight(3);
	    src.highlight(6);
	    int startNode = (int) Math.round((graph.getSize() - 1) * Math.random());
	    graph.highlightNode(startNode, null, null);
	    spannbaumKnoten.setText("Knoten des Spannbaums: { "
	        + graph.getNodeLabel(startNode) + " }", null, null);

	    // execute the main loop
	    language.nextStep();
	    src.unhighlight(6);
	    src.highlight(7);
	    src.highlight(8);
	    src.highlight(10);
	    src.highlight(12);
	    src.highlight(14);
	    src.highlight(15);
	    src.highlight(16);
	    Set<Integer> vertexes = new HashSet<Integer>();
	    vertexes.add(startNode);
	    List<ArrayList<Integer>> edges = new ArrayList<ArrayList<Integer>>();
	    while (vertexes.size() != graph.getSize()) {
	      // compute edge with the minimal weight
	      // list of possible edges, e.g. [ (node1, node2, weight), ... ]
	      List<ArrayList<Integer>> possibleEdges = getEdges(graph
	          .getAdjacencyMatrix(), vertexes, edges);
	      // sort the edges by their weight
	      Collections.sort(possibleEdges, new Comparator<ArrayList<Integer>>() {
	        public int compare(ArrayList<Integer> l1, ArrayList<Integer> l2) {
	          if (l1.get(2) < l2.get(2))
	            return -1;
	          else if (l1.get(2) > l2.get(2))
	            return 1;
	          return 0;
	        }
	      });
	      // choose the edge with the minimal weight
	      ArrayList<Integer> chosenEdge = possibleEdges.get(0);
	      // add the edge to the spanning tree
	      edges.add(chosenEdge);
	      // add the weight of the chosen edge to the total weight
	      graphWeight += chosenEdge.get(2);
	      graph.highlightEdge(chosenEdge.get(0), chosenEdge.get(1), null, null);
	      graph.highlightEdge(chosenEdge.get(1), chosenEdge.get(0), null, null);
	      // add the vertexes of the edge to the spanning tree
	      vertexes.add(chosenEdge.get(0));
	      vertexes.add(chosenEdge.get(1));
	      graph.highlightNode(chosenEdge.get(0), null, null);
	      graph.highlightNode(chosenEdge.get(1), null, null);
	      spannbaumKnoten.setText("Knoten des Spannbaums: { "
	          + getNodeLabels(vertexes, graph) + " }", null, null);
	      spannbaumKanten2.setText("{ " + getEdgeLabels(edges, graph) + " }", null,
	          null);
	      graphWeightText.setText("Gesamtgewicht des Spannbaums: " + graphWeight,
	          null, null);

	      language.nextStep();
	    }

	    // unhighlight all nodes and egdes
	    // and hide all edges which are not contained in the spanning tree
	    for (int i = 0; i < graph.getSize(); i++) {
	      graph.unhighlightNode(i, null, null);
	      for (int j = 0; j < graph.getSize(); j++) {
	        graph.unhighlightEdge(i, j, null, null);
	        List<Integer> edge = new ArrayList<Integer>();
	        edge.add(i);
	        edge.add(j);
	        edge.add(graph.getAdjacencyMatrix()[i][j]);
	        if (!edges.contains(edge))
	          graph.hideEdge(i, j, null, null);
	      }
	    }

	    src.unhighlight(7);
	    src.unhighlight(8);
	    src.unhighlight(10);
	    src.unhighlight(12);
	    src.unhighlight(14);
	    src.unhighlight(15);
	    src.unhighlight(16);
	    src.highlight(17);

	    // hide the graph and the code and show a "summary"
	    language.nextStep();
	    graph.hide();
	    language.hideAllPrimitives();
	    header.show();
	    hRect.show();
	    language.newText(new Coordinates(10, 100),
	        "Der Algorithmus lässt sich auf zusammenhängende, ungerichtete und",
	        "resultText1", null, textProps);
	    language
	        .newText(
	            new Offset(0, 25, "resultText1", AnimalScript.DIRECTION_NW),
	            "kantengewichtete Graphen anwenden. Eine Alternative zum Prim Algorithmus",
	            "resultText2", null, textProps);
	    language.newText(
	        new Offset(0, 25, "resultText2", AnimalScript.DIRECTION_NW),
	        "ist der Algorithmus von Kruskal.", "resultText3", null, textProps);
	    language.newText(
	        new Offset(0, 50, "resultText3", AnimalScript.DIRECTION_NW),
	        "Bei extrem effizienter Implementierung des Algorithmus lässt sich",
	        "resultText4", null, textProps);
	    language
	        .newText(
	            new Offset(0, 25, "resultText4", AnimalScript.DIRECTION_NW),
	            "eine Komplexität von O(|V| + |E|) erreichen, wobei |V| die Anzahl der",
	            "resultText5", null, textProps);
	    language.newText(
	        new Offset(0, 25, "resultText5", AnimalScript.DIRECTION_NW),
	        "Knoten und |E| die Anzahl der Kanten ist.", "resultText6", null,
	        textProps);
	  }

	  /**
	   * Computes the edges that are accessible from the given set of vertexes and
	   * that are not contained in the given list.
	   * 
	   * @param adjacencyMatrix
	   *          the graph's adjacency matrix
	   * @param vertexes
	   *          the vertexes from which the resulting edges should be accessible
	   * @param excludeTheseEdges
	   *          the edges that should not be contained in the result list
	   * @return the edges in the form [ (node1, node2, weight), ... ] that are
	   *         accessible from the given set of vertexes and that are not
	   *         contained in the given list.
	   */
	  private List<ArrayList<Integer>> getEdges(int[][] adjacencyMatrix,
	      Set<Integer> vertexes, List<ArrayList<Integer>> excludeTheseEdges) {
	    List<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
	    for (int vertex : vertexes) {
	      for (int i = 0; i < adjacencyMatrix.length; i++) {
	        int weight = adjacencyMatrix[vertex][i];
	        ArrayList<Integer> currentVertex1 = new ArrayList<Integer>();
	        currentVertex1.add(vertex);
	        currentVertex1.add(i);
	        currentVertex1.add(weight);
	        ArrayList<Integer> currentVertex2 = new ArrayList<Integer>();
	        currentVertex2.add(i);
	        currentVertex2.add(vertex);
	        currentVertex2.add(weight);
	        // check whether the edge is contained in one or another
	        // direction and if not, add it to the result list
	        if (weight != 0 && !excludeTheseEdges.contains(currentVertex1)
	            && !excludeTheseEdges.contains(currentVertex2))
	          result.add(currentVertex1);
	      }
	    }
	    return result;
	  }

	  /**
	   * Computes a string containing a comma-separated list of names of the given
	   * vertexes.
	   * 
	   * @param vertexes
	   *          the vertexes that should be contained in the result string
	   * @param graph
	   *          the graph, in which the given vertexes are contained
	   * @return a string containing a comma-separated list of names of the given
	   *         vertexes
	   */
	  private String getNodeLabels(Set<Integer> vertexes, Graph graph) {
	    if (vertexes.size() == 0)
	      return "";
	    String result = "";
	    for (int vertex : vertexes)
	      result += graph.getNodeLabel(vertex) + ", ";
	    result = result.substring(0, result.length() - 2);
	    return result;
	  }

	  /**
	   * Computes a string containing a list of the given edges in the form
	   * "(node1, node2, weight), (node1, node2, weight), ...".
	   * 
	   * @param edges
	   *          the edges that should be contained in the result string
	   * @param graph
	   *          the graph, in which the given edges are contained
	   * @return a string containing a list of the given edges in the form
	   *         "(node1, node2, weight), (node1, node2, weight), ...".
	   */
	  private String getEdgeLabels(List<ArrayList<Integer>> edges, Graph graph) {
	    if (edges.size() == 0)
	      return "";
	    String result = "";
	    for (ArrayList<Integer> edge : edges)
	      result += "(" + graph.getNodeLabel(edge.get(0)) + ", "
	          + graph.getNodeLabel(edge.get(1)) + "), ";
	    result = result.substring(0, result.length() - 2);
	    return result;
	  }

	  public String generate(AnimationPropertiesContainer props,
	      Hashtable<String, Object> primitives) {
	    // get the user defined primitives and properties
	    Graph graph = (Graph) primitives.get("graph");

	    System.out.println("-----------------DEBUG-----------------");
	    System.out.println("Warum ist die Adjanzenmatrix mit Nullen gefüllt?");
	    int[][] matrix = graph.getAdjacencyMatrix();
	    for (int i = 0; i < matrix.length; i++) {
	      for (int j = 0; j < matrix[0].length; j++) {
	        System.out.print(matrix[i][j] + "\t");
	      }
	      System.out.println();
	    }
	    
	    boolean isNull = true;
	    for (int i = 0; i < matrix.length; i++)
	      for (int j = 0; j < matrix[0].length; j++)
	        if (matrix[i][j] != 0)
	          isNull = false;
	    
	    GraphProperties graphProps = (GraphProperties) props
	        .getPropertiesByName("graphProps");
	    if (isNull)
	      graph = getDefaultGraph(graphProps);
	    // create the graph again in order to be able to set the graph properties
	    int size = graph.getSize();
	    Node[] nodes = new Node[size];
	    String[] nodeLabels = new String[size];
	    for (int i = 0; i < size; i++) {
	      nodes[i] = graph.getNode(i);
	      nodeLabels[i] = graph.getNodeLabel(i);
	    }
	    graph = language.newGraph(graph.getName(), graph.getAdjacencyMatrix(),
	        nodes, nodeLabels, graph.getDisplayOptions(), graphProps);
	    // TODO: add the source code properties again (they seem to be the reason
	    // for our problems)
	    // sourceCodeProps = (SourceCodeProperties) props
	    // .getPropertiesByName("sourceCodeProps");
	    sourceCodeProps = new SourceCodeProperties();
	    sourceCodeProps.set(AnimationPropertiesKeys.FONT_PROPERTY, new Font(
	        Font.SANS_SERIF, Font.PLAIN, 16));
	    sourceCodeProps.set(AnimationPropertiesKeys.HIGHLIGHTCOLOR_PROPERTY, Color.RED);

	    // I think we do not need to create an object of the class right here
	    // PrimAPIGenerator primObject = new PrimAPIGenerator(language);
	    start(graph);

	    return language.toString();
	  }
	  
	  private Graph getDefaultGraph(GraphProperties graphProps) {
	    // define the edges of the graph
	    int[][] graphAdjacencyMatrix = new int[7][7];
	    // initialize adjacency matrix with zeros
	    for (int i = 0; i < graphAdjacencyMatrix.length; i++)
	      for (int j = 0; j < graphAdjacencyMatrix[0].length; j++)
	        graphAdjacencyMatrix[i][j] = 0;
	    // set the edges with the corresponding weights
	    graphAdjacencyMatrix[0][1] = 8;
	    graphAdjacencyMatrix[1][0] = 8;
	    graphAdjacencyMatrix[0][2] = 2;
	    graphAdjacencyMatrix[2][0] = 2;
	    graphAdjacencyMatrix[1][2] = 5;
	    graphAdjacencyMatrix[2][1] = 5;
	    graphAdjacencyMatrix[1][3] = 4;
	    graphAdjacencyMatrix[3][1] = 4;
	    graphAdjacencyMatrix[2][4] = 1;
	    graphAdjacencyMatrix[4][2] = 1;
	    graphAdjacencyMatrix[3][4] = 6;
	    graphAdjacencyMatrix[4][3] = 6;
	    graphAdjacencyMatrix[3][5] = 3;
	    graphAdjacencyMatrix[5][3] = 3;
	    graphAdjacencyMatrix[3][6] = 2;
	    graphAdjacencyMatrix[6][3] = 2;
	    graphAdjacencyMatrix[4][5] = 7;
	    graphAdjacencyMatrix[5][4] = 7;
	    graphAdjacencyMatrix[5][6] = 6;
	    graphAdjacencyMatrix[6][5] = 6;
	    
	    // define the nodes and their positions
	    Node[] graphNodes = new Node[7];
	    graphNodes[0] = new Coordinates(40, 100);
	    graphNodes[1] = new Coordinates(40, 250);
	    graphNodes[2] = new Coordinates(190, 100);
	    graphNodes[3] = new Coordinates(190, 250);
	    graphNodes[4] = new Coordinates(340, 100);
	    graphNodes[5] = new Coordinates(340, 250);
	    graphNodes[6] = new Coordinates(340, 400);
	    
	    // define the names of the nodes
	    String[] labels = { "A", "B", "C", "D", "E", "F", "G" };
	    
	    Graph g = language.newGraph("graph", graphAdjacencyMatrix, graphNodes, labels, null, graphProps);
	    g.hide();
	    
	    return g;
	  }

	  public String getName() {
	    return "Prim [DE]";
	  }

	  public String getAlgorithmName() {
	    return "Prim [DE]";
	  }

	  public String getAnimationAuthor() {
	    return "Irina Smidt, Simon Sprankel";
	  }

	  public String getDescription() {
	    return "Der Algorithmus von Prim berechnet den minimalen Spannbaum eines "
	        + "\n"
	        + "zusammenh&auml;ngenden und kantengewichteten Graphen. Ein minimaler "
	        + "\n"
	        + "Spannbaum ist ein Teilgraph, der alle Knoten des Ursprungsgraphen "
	        + "\n"
	        + "enth&auml;lt und sie mit Kanten minimalen Gewichts verbindet."
	        + "\n"
	        + "\n"
	        + "1. Gegeben sei ein Graph G mit Knotenmenge V und Kantenmenge E."
	        + "\n"
	        + "    W&auml;hle aus der Knotenmenge V einen beliebigen Startknoten v0 "
	        + "\n"
	        + "    und f&uuml;ge ihn dem Spannbaum hinzu."
	        + "\n"
	        + "2. Betrachte alle Kanten, die von den Knoten aus dem Spannbaum"
	        + "\n"
	        + "    abgehen und f&uuml;ge die Kante mit geringstem Gewicht sowie den"
	        + "\n"
	        + "    Zielknoten zum Spannbaum hinzu."
	        + "\n"
	        + "3. Wiederhole Schritt 2, bis alle Knoten aus G im Spannbaum enthalten sind.";
	  }

	  public String getCodeExample() {
	    return "function prim(Set<Vertex> V, Set<Edge> E) {"
	        + "\n"
	        + "    // Knoten und Kanten des Spannbaums"
	        + "\n"
	        + "    Set<Vertex> Vs = {};"
	        + "\n"
	        + "    Set<Edge> Es = {};"
	        + "\n"
	        + "\n"
	        + "    // wähle beliebigen Startknoten"
	        + "\n"
	        + "    Vs.add(randomElement(V));"
	        + "\n"
	        + "    while (V.size() != Vs.size()) {"
	        + "\n"
	        + "        possibleEdges = E.getEdges(Vs);"
	        + "\n"
	        + "        // sortiere Kanten aufsteigend nach Gewichten"
	        + "\n"
	        + "        possibleEdges.sort();"
	        + "\n"
	        + "        // wähle die Kante mit geringstem Gewicht"
	        + "\n"
	        + "        chosenEdge = possibleEdges.first();"
	        + "\n"
	        + "        // füge ausgewählte Kante und zugehörige Knoten zum Spannbaum hinzu"
	        + "\n" + "        Es.add(chosenEdge);" + "\n"
	        + "        Vs.addAll(chosenEdge.getVertexes());" + "\n" + "    }"
	        + "\n" + "    return Vs, Es;" + "\n" + "}";
	  }

	  public String getFileExtension() {
	    return "asu";
	  }

	  public Locale getContentLocale() {
	    return Locale.GERMAN;
	  }

	  public GeneratorType getGeneratorType() {
	    return new GeneratorType(GeneratorType.GENERATOR_TYPE_GRAPH);
	  }

	  public String getOutputLanguage() {
	    return Generator.PSEUDO_CODE_OUTPUT;
	  }
}
