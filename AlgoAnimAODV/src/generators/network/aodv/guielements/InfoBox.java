package generators.network.aodv.guielements;

import java.util.ArrayList;
import java.util.StringTokenizer;

import algoanim.primitives.Text;
import algoanim.primitives.generators.Language;
import algoanim.properties.TextProperties;
import algoanim.util.Coordinates;

/**
 * This class represents a GUI box where information about the current step in
 * the algorithm can be displayed on the screen
 * 
 * @author sascha
 * 
 */
public class InfoBox {

	/**
	 * The Text to be displayed in the InfoBox
	 */
	private ArrayList<Text> textLines;

	public InfoBox(Language lang, String title, Coordinates upperLeft,
			Coordinates lowerRight) {

		TextProperties textProps = new TextProperties();

		// textProps.set(AnimationPropertiesKeys.FONT_PROPERTY, new Font(
		// "Monospaced", Font.PLAIN, 12));
		lang.newText(upperLeft, title, "Title", null, textProps);
		lang.newRect(GeometryToolBox.moveCoordinate(upperLeft, 0, 20),
				lowerRight, "InfoBox", null);
		this.textLines = new ArrayList<Text>();
		for (int i = 0; i < 5; i++) {
			textLines.add(lang.newText(
					GeometryToolBox.moveCoordinate(upperLeft, 10, 25+(i*20)), "",
					"Text", null, textProps));
		}
	}


	/**
	 * Updates the text inside the InfoBox
	 * 
	 * @param text
	 *            the text to be displayed
	 */
	public void updateText(String text) {

		removeText();
		
		if (text.length() <= 100) {
			textLines.get(0).setText(text, null, null);
			//textLines.get(0).show();
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
	
	private void removeText(){
		for (Text text : textLines){
			text.setText("",null,null);
		}
	}
	


}