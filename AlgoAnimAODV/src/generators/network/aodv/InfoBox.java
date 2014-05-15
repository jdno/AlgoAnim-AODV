package generators.network.aodv;

import java.awt.Font;

import algoanim.primitives.Text;
import algoanim.primitives.generators.Language;
import algoanim.properties.AnimationPropertiesKeys;
import algoanim.properties.TextProperties;
import algoanim.util.Coordinates;

public class InfoBox {

	Text displayedText;

	public InfoBox(Language lang, String title, Coordinates upperLeft, Coordinates lowerRight) {
		TextProperties textProps = new TextProperties();
		textProps.set(AnimationPropertiesKeys.FONT_PROPERTY, new Font(
				"Monospaced", Font.PLAIN, 12));
		lang.newText(upperLeft, title, "Title", null, textProps);
		lang.newRect(moveCoordinate(upperLeft, 0, 20), lowerRight, "InfoBox",
				null);
		this.displayedText = lang.newText(moveCoordinate(upperLeft, 10, 25),
				"", "Text", null, textProps);
	}

	public void updateText(String text) {
		displayedText.setText(text, null, null);
	}
	
	private Coordinates moveCoordinate(Coordinates point, int x, int y) {
		return new Coordinates(point.getX() + x, point.getY() + y);
	}

}