package generators.network.aodv.guielements;

import algoanim.primitives.generators.Language;
import algoanim.util.Coordinates;

/**
 * Created by sascha on 07.06.14.
 */
public class GUIPositionElement extends GUIElement {

    protected final Coordinates position;

    public GUIPositionElement(Language lang, Coordinates position){
        super(lang);
        this.position = position;
    }

}
