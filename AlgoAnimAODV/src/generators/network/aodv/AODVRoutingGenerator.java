package generators.network.aodv;

import algoanim.animalscript.AnimalScript;
import algoanim.primitives.Graph;
import algoanim.primitives.generators.Language;
import generators.framework.Generator;
import generators.framework.GeneratorType;
import generators.framework.properties.AnimationPropertiesContainer;
import generators.network.aodv.guielements.GUIController;
import generators.network.aodv.guielements.GeometryToolBox;

import java.util.Hashtable;
import java.util.Locale;

public class AODVRoutingGenerator implements Generator {
    private Language lang;
    private GUIController controller;
    private AODVGraph aodvGraph;

    public AODVRoutingGenerator() {
        lang = new AnimalScript("Ad-hoc Optimized Vector Routing",
                "Sascha Bleidner, Jan David Nose", 1200, 800);
        controller = new GUIController(lang);
        GeometryToolBox.init(lang);
    }

    public void init() {
        lang.setStepMode(true);
    }

    public String generate(AnimationPropertiesContainer props, Hashtable<String, Object> primitives) {

        Graph loadedGraph = (Graph) primitives.get("graph");

        aodvGraph = new AODVGraph(lang, loadedGraph);
        aodvGraph.show();

        lang.nextStep();

        controller.drawInfoTable(aodvGraph.getAODVNodes());
        controller.drawInfoBox("Erläuterung");

        startAodvRouting();

        return lang.toString();
    }

    public void startAodvRouting() {
        controller.updateInfoBoxText("Der Startknoten beginnt die Route Discovery, in dem er ein Route Request (RREQ) an seine Nachbarn schickt.");

        // TODO initialize correctly
        AODVNode startNode = aodvGraph.getNode(0);
        AODVNode destinationNode = aodvGraph.getNode(aodvGraph.getAODVNodes().size() - 1);

        startNode.startRouteDiscovery(destinationNode);
        int idleNodes = 0;

        for(int i = 0; i < 4; i++) {
        //while (idleNodes < aodvGraph.getAODVNodes().size()) {
            idleNodes = 0;

            for (AODVNode node : aodvGraph.getAODVNodes()) {
                if (node.getCachedMessage() != null) {
                    node.process();
                } else {
                    idleNodes++;
                }
            }
        }
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
