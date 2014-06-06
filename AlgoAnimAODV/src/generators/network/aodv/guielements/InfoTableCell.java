package generators.network.aodv.guielements;

import java.awt.Color;

import algoanim.primitives.Rect;
import algoanim.primitives.Text;
import algoanim.primitives.generators.Language;
import algoanim.properties.AnimationPropertiesKeys;
import algoanim.properties.RectProperties;
import algoanim.util.Coordinates;

public class InfoTableCell extends GUIElement{

	private Text entry;
	private Rect highlightBox;
	private RectProperties boxProperties;
	private Coordinates upperLeft;
	private Coordinates lowerRight;
	
	/**
	 * Color for highlighted cells
	 */
	private Color highlightColor = Color.ORANGE;
	
	public InfoTableCell(Language lang, String text, Coordinates position, int width, int height){
		super(lang,position);
		this.boxProperties = new RectProperties();
		boxProperties.set(AnimationPropertiesKeys.FILLED_PROPERTY, true);
		boxProperties.set(AnimationPropertiesKeys.FILL_PROPERTY, highlightColor);
		boxProperties.set(AnimationPropertiesKeys.COLOR_PROPERTY, highlightColor);
		this.upperLeft = GeometryToolBox.moveCoordinate(position, -2, 1);
		this.lowerRight = GeometryToolBox.moveCoordinate(position, width-5, height-1);
		this.highlightBox = lang.newRect(upperLeft, lowerRight, "rect", null, boxProperties);
		this.highlightBox.hide();
		this.entry = lang.newText(position, text, "cell", null);
	}
	
	public void highlightCell(){
		highlightBox.show();
	}
	
	public void unhighlightCell(){
		highlightBox.hide();
	}
	
	public String getText(){
		return entry.getText();
	}
	
	public void setText(String text){
		entry.setText(text, null, null);
	}
	
}
