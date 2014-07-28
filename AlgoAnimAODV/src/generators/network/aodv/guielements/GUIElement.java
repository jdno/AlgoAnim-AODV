package generators.network.aodv.guielements;

import algoanim.primitives.generators.Language;
import algoanim.util.Coordinates;

/**
 * Abstract class for all GUI elements. It provides attributes needed by all elements that
 * should be drawn on the canvas.
 *
 * @author Sascha Bleidner, Jan David Nose
 */
public abstract class GUIElement {

    /**
     * The language object of AnimalScript
     */
    protected final Language lang;

    /**
     * The coordinates of the element
     */
    protected final Coordinates position;

    /**
     * Configure the attributes of the GUI element
     *
     * @param lang The language object
     * @param position The position on the canvas
     */
    public GUIElement(Language lang, Coordinates position){
        this.lang = lang;
        this.position = position;
    }

}
