package generators.network.aodv.guielements.Tables;

import algoanim.primitives.Rect;
import algoanim.primitives.Text;
import algoanim.primitives.generators.Language;
import algoanim.properties.RectProperties;
import algoanim.util.Coordinates;
import generators.network.aodv.guielements.GUIElement;
import generators.network.aodv.guielements.GeometryToolBox;

/**
 * This class represents a cell in a GUITable.
 *
 * @author Sascha Bleidner, Jan David Nose
 */
public class TableCell extends GUIElement {

    /**
     * The content of the cell
     */
	private Text entry;

    /**
     * The rectangle that gets highlighted. It is placed below the text, and gets
     * hidden when no hightlight is necessary.
     */
	private final Rect highlightBox;

    /**
     * The look & feel of the hightlight box
     */
    private static RectProperties boxProperties;

    /**
     * Create a new TableCell.
     *
     * @param lang The language object
     * @param text The content of the cell
     * @param position The position of the cell
     * @param width The width of the cell
     * @param height The height of the cell
     * @param rectProps The look & feel of the highlight
     */
	public TableCell(Language lang, String text, Coordinates position, int width, int height, RectProperties rectProps){
		super(lang,position);

        boxProperties = rectProps;

		Coordinates upperLeft = GeometryToolBox.moveCoordinate(position, -2, 1);
        Coordinates lowerRight = GeometryToolBox.moveCoordinate(position, width-5, height-1);
		this.highlightBox = lang.newRect(upperLeft, lowerRight, "rect", null, boxProperties);
		this.highlightBox.hide();
		this.entry = lang.newText(position, text, "cell", null);
	}

    /**
     * Highlight the cell
     */
	public void highlightCell(){
		highlightBox.show();
	}

    /**
     * Reset the highlight of the cell
     */
	public void unhighlightCell(){
		highlightBox.hide();
	}

    /**
     * @return the text
     */
	public String getText(){
		return entry.getText();
	}

    /**
     * @param text the text
     */
	public void setText(String text){
        entry.hide();
        entry = lang.newText(position, text,"cell",null);
	}

    /**
     * Update the text only if it has changed
     * @param text the text
     */
    public void updateText(String text){
        if (!text.equals(entry)){
            setText(text);
        }
    }
	
}
