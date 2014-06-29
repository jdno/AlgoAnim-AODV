package generators.network.aodv.guielements;

import algoanim.primitives.generators.Language;
import algoanim.properties.TextProperties;
import algoanim.util.Coordinates;

import java.util.StringTokenizer;

/**
 * Created by sascha on 29.06.14.
 */
public class TextToolBox {

    public static int multipleTextLines(Language lang, Coordinates startPoint, String text, TextProperties props, int lengthOfLine) {
        if (text.length() <= lengthOfLine) {
            System.out.println(lang == null);
            lang.newText(startPoint, text, "text", null, props);
        } else {
            int charCounter = 0;
            int line = 0;
            int lineHeight = 25;
            StringBuffer strBuffer = new StringBuffer();
            StringTokenizer strToken = new StringTokenizer(text);
            while (strToken.hasMoreElements()) {
                if (charCounter <= lengthOfLine) {
                    int before = strBuffer.length();
                    strBuffer.append(strToken.nextElement()).append(" ");
                    charCounter += strBuffer.length() - before;
                } else {
                    charCounter = 0;
                    lang.newText(GeometryToolBox.moveCoordinate(startPoint, 0, lineHeight * line), strBuffer.toString(), "text", null, props);
                    strBuffer = new StringBuffer();
                    line++;
                }

            }
            if (strBuffer.length() != 0) {
                lang.newText(GeometryToolBox.moveCoordinate(startPoint, 0, lineHeight * line), strBuffer.toString(), "text", null, props);
            }
            return GeometryToolBox.moveCoordinate(startPoint, 0, lineHeight * line).getY();
        }
        return startPoint.getY();
    }
}
