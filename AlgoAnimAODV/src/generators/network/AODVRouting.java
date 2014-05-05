package generators.network;

import generators.framework.Generator;
import generators.framework.GeneratorType;

import java.util.Locale;

import algoanim.primitives.Graph;
import algoanim.primitives.generators.Language;
import algoanim.properties.GraphProperties;
import algoanim.util.Coordinates;
import algoanim.util.Node;

import java.util.Hashtable;

import generators.framework.properties.AnimationPropertiesContainer;
import algoanim.animalscript.AnimalScript;

// TODO: Tabellen anzeigen
// TODO: Private Klasse für Tabellen
// TODO: Graphen Properties erkunden
// TODO: Infobox anzeigen und verändern


public class AODVRouting implements Generator {
    private Language lang;
    private int[][] adjacencyMatrix;

    public void init(){
        lang = new AnimalScript("Ad-hoc Optimized Vector Routing", "Sascha Bleidner, Jan David Nose", 800, 600);
    }

    public String generate(AnimationPropertiesContainer props,Hashtable<String, Object> primitives) {
        adjacencyMatrix = (int[][])primitives.get("adjacencyMatrix");
        
//        GraphProperties graphProps = (GraphProperties) props.getPropertiesByName("graphProps");
        getDefaultGraph(null);
        return lang.toString();
    }
    
    private Graph getDefaultGraph(GraphProperties graphProps){
    	Graph defaultGraph;
    	
    	Node[] nodes = new Node[4];
    	nodes[0] = new Coordinates(40, 100);
	    nodes[1] = new Coordinates(40, 250);
	    nodes[2] = new Coordinates(190, 100);
	    nodes[3] = new Coordinates(190, 250);
	    
    	String[] graphLabels = {"A", "B", "C", "D"}; 
	    
    	defaultGraph = lang.newGraph("AODV-Graph", adjacencyMatrix, nodes, graphLabels, null);
//    	defaultGraph.hide();
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

    public String getDescription(){
        return "AODV ist ein reaktiver Routingalgorithmus f??r mobile Ad-hoc Netze.";
    }

    public String getCodeExample(){
        return "CodeExample";
    }

    public String getFileExtension(){
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