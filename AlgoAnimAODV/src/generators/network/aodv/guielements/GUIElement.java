package generators.network.aodv.guielements;

import algoanim.primitives.generators.Language;
import algoanim.util.Coordinates;

public class GUIElement {

	protected Language lang;
	protected Coordinates position;
	
	public GUIElement(Language lang, Coordinates position){
		this.lang = lang;
		this.position = position;
	}
}
