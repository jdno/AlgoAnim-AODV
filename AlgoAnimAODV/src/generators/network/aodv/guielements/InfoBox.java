package generators.network.aodv.guielements;

import java.util.ArrayList;
import java.util.StringTokenizer;

import algoanim.primitives.Text;
import algoanim.primitives.generators.Language;
import algoanim.properties.RectProperties;
import algoanim.properties.TextProperties;
import algoanim.util.Coordinates;

/**
 * This class represents a GUI box where information about the current step in
 * the algorithm can be displayed on the screen
 * 
 * @author Sascha Bleidner, Jan David Nose
 */
public class InfoBox extends GUIElement{

	/**
	 * The Text to be displayed in the InfoBox
	 */
	private ArrayList<Text> textLines;

    /**
     * Create a new InfoBox. It automatically prints it at the given position.
     *
     * @param lang The language object
     * @param title The title of the box
     * @param upperLeft The position of the upper-left corner of the box
     * @param lowerRight The position of the lower-right corner of the box
     * @param textProps The look & feel of the text
     * @param rectProps The look & feel of the box
     */
	public InfoBox(Language lang, String title, Coordinates upperLeft,
			Coordinates lowerRight, TextProperties textProps, RectProperties rectProps) {

        super(lang,upperLeft);

		lang.newText(upperLeft, title, "Title", null, textProps);
		lang.newRect(GeometryToolBox.moveCoordinate(upperLeft, 0, 20), lowerRight, "InfoBox", null, rectProps);

		this.textLines = new ArrayList<Text>();

        for (int i = 0; i < 5; i++) {
			textLines.add(lang.newText(
					GeometryToolBox.moveCoordinate(upperLeft, 10, 25 + (i * 20)), "",
					"Text", null, textProps));
		}
	}

	/**
	 * Updates the text inside the InfoBox
	 * 
	 * @param text the text to be displayed
	 */
	public void updateText(String text) {

		removeText();
		
		if (text.length() <= 100) {
			textLines.get(0).setText(text, null, null);
		} else {
			int charCounter = 0;
			int line = 0;
			StringBuffer strBuffer = new StringBuffer();
			StringTokenizer strToken = new StringTokenizer(text);
			while (strToken.hasMoreElements()) {
				if (charCounter <= 100) {
					int before = strBuffer.length();
					strBuffer.append(strToken.nextElement()).append(" ");
					charCounter += strBuffer.length() - before;
				} else {
					charCounter = 0;
					textLines.get(line).setText(strBuffer.toString(), null, null);
					strBuffer = new StringBuffer();
					line++;
				}
				
			}
			if (strBuffer.length() != 0){
				textLines.get(line).setText(strBuffer.toString(), null, null);
				}
		}
	}

    /**
     * Removes the current text and leaves a blank box.
     */
	private void removeText(){
		for (Text text : textLines){
			text.setText("",null,null);
		}
	}

}