package generators.network.aodv.guielements;

import java.awt.Font;

import algoanim.primitives.Text;
import algoanim.primitives.generators.Language;
import algoanim.properties.AnimationPropertiesKeys;
import algoanim.properties.TextProperties;
import algoanim.util.Coordinates;

/**
 * This class represents a GUI box where information about the current step in the algorithm can be displayed on the screen
 * @author sascha
 *
 */
public class InfoBox {
	
	/**
	 * The Text to be displayed in the InfoBox
	 */
	private Text displayedText;

	public InfoBox(Language lang, String title, Coordinates upperLeft, Coordinates lowerRight) {
		
		TextProperties textProps = new TextProperties();
		
		//textProps.set(AnimationPropertiesKeys.FONT_PROPERTY, new Font(
		//		"Monospaced", Font.PLAIN, 12));
		lang.newText(upperLeft, title, "Title", null, textProps);
		lang.newRect(GeometryToolBox.moveCoordinate(upperLeft, 0, 20), lowerRight, "InfoBox",
				null);
		this.displayedText = lang.newText(GeometryToolBox.moveCoordinate(upperLeft, 10, 25),
				"", "Text", null, textProps);
	}

	/**
	 * Updates the text inside the InfoBox
	 * @param text
	 * 			the text to be displayed
	 */
	public void updateText(String text) {
		displayedText.setText(text, null, null);
	}
	

}