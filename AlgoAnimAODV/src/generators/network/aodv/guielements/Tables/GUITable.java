package generators.network.aodv.guielements.Tables;

import algoanim.primitives.generators.Language;
import algoanim.properties.RectProperties;
import algoanim.util.Coordinates;
import generators.network.aodv.guielements.GUIElement;
import generators.network.aodv.guielements.GeometryToolBox;

/**
 * This abstract class represents a table that can be drawn using AnimalScript.
 *
 * @author Sascha Bleidner, Jan David Nose
 */
public abstract class GUITable extends GUIElement {

    /**
     * The height of the cell
     */
    protected int cellHeight;

    /**
     * The width of the cell
     */
    protected int cellWidth;

    /**
     * The current line is a pointer that is moved when painting the table. Its coordinates
     * are the top-left corner where new objects are placed, and moving it by a cell's height
     * allows to paint beautiful tables from scratch.
     */
    protected Coordinates currentLine;

    /**
     * The title of the table
     */
    protected String tableTitle;

    /**
     * The title of the columns
     */
    protected String[] columnTitles;

    /**
     * This property can be configured in the wizard, and changes the look & feel of the table.
     */
    protected RectProperties highlight;

    /**
     * The constructor initializes the attributes position and highlight with the given properties.
     *
     * @param lang The language object
     * @param position The position where the first line should be drawn
     * @param highlight The look & feel of the table
     */
    public GUITable(Language lang, Coordinates position, RectProperties highlight){
        super(lang,position);
        currentLine = position;
        this.highlight = highlight;
    }

    /**
     * Move the current line pointer to the next row (beneath it).
     */
    protected void nextLine() {
        currentLine = GeometryToolBox.moveCoordinate(currentLine, 0, cellHeight);
    }

    /**
     * Move the current line pointer into the given cell.
     *
     * @param numOfCell The cell number (starts with 0)
     * @return The position of the pointer
     */
    protected Coordinates moveToCell(int numOfCell){
        return GeometryToolBox.moveCoordinate(currentLine,cellWidth*numOfCell,0);
    }

    /**
     * Print the titles. The table's title is above the table, while the column titles are above their
     * respective column (as with every other table).
     *
     * @param height The height of the table
     */
    protected void drawTitles(int height){
        lang.newText(currentLine, tableTitle, "Tablename", null);

        nextLine();

        for (int i = 0; i < columnTitles.length; i++) {
            lang.newText(moveToCell(i), columnTitles[i], "", null);

            if (i != 0) {
                GeometryToolBox.drawVerticalLine(lang, GeometryToolBox.moveCoordinate(moveToCell(i), -5, 0), cellHeight * height);
            }
        }

        nextLine();
        GeometryToolBox.drawHorizontalLie(lang, currentLine, cellWidth * columnTitles.length);
    }

}
