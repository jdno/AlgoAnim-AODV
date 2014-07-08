package generators.network.aodv.guielements;

import algoanim.animalscript.AnimalScript;
import algoanim.primitives.Text;
import algoanim.primitives.generators.Language;
import algoanim.properties.TextProperties;
import algoanim.util.Coordinates;
import algoanim.util.Offset;

import java.util.StringTokenizer;

/**
 * Helper class for displaying text in multiple lines
 */
public class TextToolBox {

    /**
     * Displays the given text in multiple lines starting from the coordinate startPoint
     * @param lang
     *          Language object to display the text on
     * @param startPoint
     *          StartPoint for the text
     * @param text
     *          text to be split in multiple lines
     * @param props
     *          text properties for the text
     * @param lengthOfLine
     *          length of a single line
     * @return
     */
    public static int multipleTextLines(Language lang, Coordinates startPoint, String text, TextProperties props, int lengthOfLine) {

        Text firstLine = null;
        if (text.length() <= lengthOfLine) {
            System.out.println(lang == null);

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
                    if (line == 0 ){
                        firstLine = lang.newText(startPoint,strBuffer.toString(),"text",null,props);
                    } else {
                        lang.newText(new Offset(0,lineHeight*line,firstLine, AnimalScript.DIRECTION_BASELINE_START), strBuffer.toString(), "text", null,props);
                    }
                    strBuffer = new StringBuffer();
                    line++;
                }

            }
            if (strBuffer.length() != 0) {
                lang.newText(GeometryToolBox.moveCoordinate(startPoint, 0, lineHeight * line), strBuffer.toString(), "text", null,props);
            }
            return GeometryToolBox.moveCoordinate(startPoint, 0, lineHeight * line).getY();
        }
        return startPoint.getY();
    }
}
