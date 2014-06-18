package generators.network.aodv.guielements;

import algoanim.primitives.generators.Language;
import algoanim.util.Coordinates;

/**
 * Abstract class for all GUIElements
 */
public abstract class GUIElement {

    protected final Language lang;
    protected final Coordinates position;

    public GUIElement(Language lang, Coordinates position){
        this.lang = lang;
        this.position = position;
    }

}
