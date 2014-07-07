package generators.network.aodv;

import algoanim.animalscript.AnimalScript;
import algoanim.primitives.Graph;
import algoanim.primitives.generators.Language;
import generators.framework.Generator;
import generators.framework.GeneratorType;
import generators.framework.properties.AnimationPropertiesContainer;
import translator.Translator;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Locale;

/**
 * The class AODVRoutingGenerator represents a generator for the AODV routing algorithm with a concrete language
 */
public class AODVRoutingGenerator implements Generator{

    private Language lang;
    private GUIController controller;
    private AODVGraph aodvGraph;
    private String[][] routeDiscoveries;


    private Translator translator;
    private Locale locale;


    /**
     * Cunstructs an AODVRoutingGenerator in a concrete language
     * @param locale
     *          language for the translation, currently DE and EN are available as a translation
     */
    public AODVRoutingGenerator(Locale locale){
        translator = new Translator("ressources/AlgoAnimAODV", locale);
        this.locale = locale;
    }

    public void init() {
        // nothing to be done
    }

    public String generate(AnimationPropertiesContainer props, Hashtable<String, Object> primitives) {

        lang = new AnimalScript("Ad-hoc Optimized Vector Routing",
                "Sascha Bleidner, Jan David Nose", 1200, 800);

        lang.setStepMode(true);

        Graph loadedGraph = (Graph) primitives.get("graph");
        routeDiscoveries = (String[][]) primitives.get("StartandEndnodes");


        controller = new GUIController(lang,loadedGraph,translator,props);

        controller.drawStartPage();
        controller.hideStartPage();

        controller.drawGUIGraph();
        aodvGraph = new AODVGraph(controller.getAnimalGraph(),controller);

        controller.drawInfoTable(aodvGraph.getAODVNodes());
        controller.drawStatisticTable(translator.translateMessage("statTableTitle"));
        controller.drawInfoBox(translator.translateMessage("infoBox"));

        AODVNode startNode;
        AODVNode destinationNode;

        if (routeDiscoveries.length % 2 != 0) {
            System.err.println("Start and end nodes not properly declared");
        } else {
            for (String[] startEndNodes : routeDiscoveries){
                if (startEndNodes.length != 2 ){
                    System.err.println("Start and end nodes not properly declared");
                } else {
                    startNode = aodvGraph.getNode(startEndNodes[0]);
                    destinationNode = aodvGraph.getNode(startEndNodes[1]);
                    if (startNode != null && destinationNode != null) {
                        startAodvRouting(startNode, destinationNode);
                        controller.unhighlightAll();
                    }
                }
            }
        }


        /**
         * Draws the end page with the complexity information of AODV
         */
        controller.drawEndPage();

        return lang.toString();
    }

    /**
     * Starts the AODV Routing Algorithm
     * @param startNode
     *          Node which starts a route request to the destination node
     * @param destinationNode
     *          Node which is set as the destination for the route request.
     */
    public void startAodvRouting(AODVNode startNode, AODVNode destinationNode) {
        startNode.startRouteDiscovery(destinationNode);
        lang.nextStep();

        int idleNodes = 0;
        ArrayList<AODVNode> workingNodes = new ArrayList<AODVNode>(aodvGraph.getAODVNodes().size());

        //for(int i = 0; i < 4; i++) {
        while (idleNodes < aodvGraph.getAODVNodes().size()) {
            idleNodes = 0;
            workingNodes.clear();

            for (AODVNode node : aodvGraph.getAODVNodes()) {
                if (node.getCachedMessage() != null) {
                    workingNodes.add(node);
                } else {
                    idleNodes++;
                }
            }

            for(AODVNode node : workingNodes) {
                controller.unhighlightAll();
                node.process();
                lang.nextStep();
            }
        }
    }


    public String getName() {
        return translator.translateMessage("algoName");
    }

    public String getAlgorithmName() {
        return translator.translateMessage("algoName");
    }

    public String getAnimationAuthor() {
        return "Sascha Bleidner, Jan David Nose";
    }

    public String getDescription() {
        return translator.translateMessage("algoDesc");
    }

    public String getCodeExample() {
        return "CodeExample";
    }

    public String getFileExtension() {
        return "asu";
    }

    public Locale getContentLocale() {
        return locale;
    }

    public GeneratorType getGeneratorType() {
        return new GeneratorType(GeneratorType.GENERATOR_TYPE_NETWORK);
    }

    public String getOutputLanguage() {
        return Generator.JAVA_OUTPUT;
    }

    
}
