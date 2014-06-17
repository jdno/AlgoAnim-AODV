package generators.network.aodv.guielements;

import algoanim.primitives.generators.Language;
import algoanim.util.Coordinates;

/**
 * Created by sascha on 17.06.14.
 */
public class GUITable extends GUIPositionElement{


    protected int cellHeight;
    protected int cellWidth;

    protected Coordinates currentLine;

    protected String[] titles;


    public GUITable(Language lang, Coordinates position){
        super(lang,position);
        currentLine = position;
    }

    protected void nextLine() {
        currentLine = GeometryToolBox
                .moveCoordinate(currentLine, 0, cellHeight);
    }

    protected Coordinates moveToCell(int numOfCell){
        return GeometryToolBox.moveCoordinate(currentLine,cellWidth*numOfCell,0);
    }

    protected void drawTitles(int height){
        for (int i = 0; i < titles.length; i++) {
            lang.newText(moveToCell(i), titles[i], "", null);

            if (i != 0) {
                GeometryToolBox.drawVerticalLine(lang,GeometryToolBox.moveCoordinate(moveToCell(i),-5,0),cellHeight*height);
            }
        }

        nextLine();
        GeometryToolBox.drawHorizontalLie(lang,currentLine, cellWidth
                * titles.length);
    }



}
