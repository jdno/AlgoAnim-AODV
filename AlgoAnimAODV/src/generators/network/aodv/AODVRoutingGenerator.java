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


public class AODVRoutingGenerator implements Generator {
	private Language lang;
	private GUIController controller;
	private AODVGraph aodvGraph;
	
	Color highlightColor = Color.ORANGE;

	public AODVRoutingGenerator(Language language) {
		this.lang = language;
	}

	public AODVRoutingGenerator() {
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
		
		GraphProperties graphProps = new GraphProperties();
		graphProps.set(AnimationPropertiesKeys.HIGHLIGHTCOLOR_PROPERTY,
				highlightColor);
		graphProps.set(AnimationPropertiesKeys.FILL_PROPERTY, Color.WHITE);
		graphProps.set(AnimationPropertiesKeys.DIRECTED_PROPERTY, true);

		aodvGraph = new AODVGraph(lang, (Graph) primitives.get("graph"));

		aodvGraph.show();

		lang.nextStep();
		aodvGraph.highlightNode(0);
		aodvGraph.highlightNode(3);
		aodvGraph.highlightEdge(0, 1);
		lang.nextStep();

		controller.drawInfoTable(aodvGraph.getAODVNodes());

		controller.drawInfoBox("Erläuterung");
		controller.updateInfoBoxText("Hallo");
		lang.nextStep();
		controller.updateInfoBoxText("Wuhuuu");
		lang.nextStep();
		
		return lang.toString();
	}
	
	public void startAodvRouting() {
		lang.nextStep();
		controller.drawInfoBox("Der Startknoten beginnt die Route Discovery, in dem er ein Route Request (RREQ) an seine Nachbarn schickt.");
		
		// TODO initialize correctly
		AODVNode startNode = aodvGraph.getNode(0);
		AODVNode destinationNode = aodvGraph.getNode(aodvGraph.getAODVNodes().size() - 1);
		
		startNode.startRouteDiscovery(destinationNode);
		int idleNodes = 0;
		
		while(idleNodes < aodvGraph.getAODVNodes().size()) {
			idleNodes = 0;
			
			for(AODVNode node: aodvGraph.getAODVNodes()) {
				if(node.getCachedMessage() != null) {
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