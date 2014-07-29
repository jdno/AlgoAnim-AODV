package generators.network.aodv;

import algoanim.animalscript.AnimalScript;
import algoanim.primitives.Graph;
import algoanim.primitives.generators.Language;
import generators.framework.Generator;
import generators.framework.GeneratorType;
import generators.framework.ValidatingGenerator;
import generators.framework.properties.AnimationPropertiesContainer;
import generators.network.aodv.animal.Statistics;
import translator.Translator;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Locale;

/**
 * The class AODVRoutingGenerator represents a generator for the AODV routing algorithm with a concrete language
 *
 * @author Sascha Bleidner, Jan David Nose
 */
public class AODVRoutingGenerator implements ValidatingGenerator {

    /**
     * The language object
     */
    private Language lang;

    /**
     * The GUI controller handling the application
     */
    private GUIController controller;

    /**
     * The AODV graph of the application
     */
    private AODVGraph aodvGraph;

    /**
     * The list of route discoveries to perform. They should be in the following format:
     * [
     * ["A", "H"],
     * ["C", "A"]
     * ]
     */
    private String[][] routeDiscoveries;

    /**
     * The translator instance
     */
    private Translator translator;

    /**
     * The current locale of the application
     */
    private Locale locale;

    /**
     * Cunstructs an AODVRoutingGenerator in a concrete language
     *
     * @param locale language for the translation, currently DE and EN are available as a translation
     */
    public AODVRoutingGenerator(Locale locale) {
        translator = new Translator("ressources/AlgoAnimAODV", locale);
        this.locale = locale;
    }

    /**
     * Initialize the generator
     */
    public void init() {
        // nothing to be done
    }

    /**
     * Generate the AODV animation
     *
     * @param props      The properties to use for the look & feel
     * @param primitives The preconfigured primitives from the wizard
     * @return The animation as a string in AnimalScript
     */
    public String generate(AnimationPropertiesContainer props, Hashtable<String, Object> primitives) {

        controller.drawStartPage();
        controller.hideStartPage();


        controller.drawGUIGraph();


        controller.drawInfoTable(aodvGraph.getAODVNodes());
        controller.drawStatisticTable(translator.translateMessage("statTableTitle"));
        controller.drawInfoBox(translator.translateMessage("infoBox"));

        AODVNode startNode;
        AODVNode destinationNode;

        // Reset the statistics
        Statistics.sharedInstance().reset();

        for (String[] startEndNodes : routeDiscoveries) {
            startNode = aodvGraph.getNode(startEndNodes[0]);
            destinationNode = aodvGraph.getNode(startEndNodes[1]);
            if (startNode != null && destinationNode != null) {
                controller.drawNodeInfo(startNode, destinationNode);
                startAodvRouting(startNode, destinationNode);
                controller.unhighlightAll();
            }
        }

        // Draws the end page with the complexity information of AODV
        controller.drawEndPage();

        return lang.toString();
    }

    /**
     * Starts the AODV Routing Algorithm
     *
     * @param startNode       Node which starts a route request to the destination node
     * @param destinationNode Node which is set as the destination for the route request.
     */
    public void startAodvRouting(AODVNode startNode, AODVNode destinationNode) {
        startNode.startRouteDiscovery(destinationNode);

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

            for (AODVNode node : workingNodes) {
                controller.unhighlightAll();
                node.process();
                lang.nextStep();
            }
        }
    }

    /**
     * Validates the properties and constructs thereby Objects for the later animation generation
     * @param animationPropertieses
     * @param stringObjectHashtable
     * @return false if the properties are not valid
     * @throws IllegalArgumentException
     */
    @Override
    public boolean validateInput(AnimationPropertiesContainer animationPropertieses, Hashtable<String, Object> stringObjectHashtable) throws IllegalArgumentException {


        lang = new AnimalScript("Ad-hoc Optimized Vector Routing",
                "Sascha Bleidner, Jan David Nose", 1200, 800);

        lang.setStepMode(true);


        routeDiscoveries = (String[][]) stringObjectHashtable.get("StartandEndnodes");

        Graph loadedGraph = (Graph) stringObjectHashtable.get("graph");
        controller = new GUIController(lang, loadedGraph, translator, animationPropertieses);

        aodvGraph = new AODVGraph(controller.getAnimalGraph(), controller);


        for (String[] startEndNodes : routeDiscoveries) {
            if (startEndNodes.length != 2) {
                showErrorMessage(translator.translateMessage("errorMessageWrongNumberNodes"));
                return false;
            } else {
                AODVNode startNode = aodvGraph.getNode(startEndNodes[0]);
                AODVNode destinationNode = aodvGraph.getNode(startEndNodes[1]);
                if (startNode == null || !aodvGraph.containsNode(startNode)) {
                    showErrorMessage(translator.translateMessage("errorMessageStartNodeNotFound"));
                    return false;
                } else {
                    if (destinationNode == null || !aodvGraph.containsNode(destinationNode)) {
                        showErrorMessage(translator.translateMessage("errorMessageDestinationNodeNotFound"));
                        return false;
                    }
                }

            }
        }
        return true;
    }


    /**
     * Displays an error message on the screen as a JOptionPane
     * @param message message to be displayed to help the user investigating on the error
     */
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null,
                message,
                translator.translateMessage("errorMessageTitle"),
                JOptionPane.ERROR_MESSAGE);

    }


    /**
     * @return the name
     */
    public String getName() {
        return translator.translateMessage("algoName");
    }

    /**
     * @return the algorithm name
     */
    public String getAlgorithmName() {
        return translator.translateMessage("algoName");
    }

    /**
     * @return the author
     */
    public String getAnimationAuthor() {
        return "Sascha Bleidner, Jan David Nose";
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return translator.translateMessage("algoDesc");
    }

    /**
     * @return the code example
     */
    public String getCodeExample() {
        return "StartNode.startRouteDiscovery do\n\tflood RREQ\nend\n\nNodes.each do\n\tif Node != DestinationNode\n\t\tflood RREQ\n\telse\n\t\treply RREP\n\tend\nend\n";
    }

    /**
     * @return the file extension
     */
    public String getFileExtension() {
        return "asu";
    }

    /**
     * @return the locale
     */
    public Locale getContentLocale() {
        return locale;
    }

    /**
     * @return the generator type
     */
    public GeneratorType getGeneratorType() {
        return new GeneratorType(GeneratorType.GENERATOR_TYPE_NETWORK);
    }

    /**
     * @return the language
     */
    public String getOutputLanguage() {
        return Generator.JAVA_OUTPUT;
    }

}
