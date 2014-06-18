package generators.network.aodv.guielements.Tables;

import algoanim.primitives.generators.Language;
import algoanim.properties.RectProperties;
import algoanim.util.Coordinates;
import generators.network.aodv.guielements.GUIPositionElement;
import generators.network.aodv.guielements.GeometryToolBox;

/**
 * Created by sascha on 17.06.14.
 */
public abstract class GUITable extends GUIPositionElement {

    protected int cellHeight;
    protected int cellWidth;

    protected Coordinates currentLine;

    protected String title;
    protected String[] titles;

    protected RectProperties highlight;

    public GUITable(Language lang, Coordinates position, RectProperties highlight){
        super(lang,position);
        currentLine = position;
        this.highlight = highlight;
    }

    protected void nextLine() {
        currentLine = GeometryToolBox
                .moveCoordinate(currentLine, 0, cellHeight);
    }

    protected Coordinates moveToCell(int numOfCell){
        return GeometryToolBox.moveCoordinate(currentLine,cellWidth*numOfCell,0);
    }

    protected void drawTitles(int height){
        lang.newText(currentLine, title,
                "Tablename", null);
        nextLine();
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
