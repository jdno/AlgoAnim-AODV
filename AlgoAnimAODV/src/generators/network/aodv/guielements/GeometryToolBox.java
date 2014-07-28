package generators.network.aodv.guielements;

import algoanim.exceptions.NotEnoughNodesException;
import algoanim.primitives.generators.Language;
import algoanim.properties.TextProperties;
import algoanim.util.Coordinates;
import algoanim.util.Node;

import java.util.StringTokenizer;

/**
 * This class provides some basic geometry functionality for processing GUI elements.
 *
 * @author Sascha Bleidner, Jan David Nose
 */
public class GeometryToolBox {

    /**
     * Draws a vertical line from the given startPoint with the specified length
     *
     * @param lang language object to draw on
     * @param startPoint starting point fo the line
     * @param length length of the drawn line
     * @return The polygon that represents the line
     */
	public static algoanim.primitives.Polygon drawVerticalLine(Language lang, Coordinates startPoint, int length) {
		return getPolygon(lang,startPoint, length, true);
	}

    /**
     * Draws a horizontal line from the given startPoint with the specified length
     *
     * @param lang language object to draw on
     * @param startPoint starting point fo the line
     * @param length length of the drawn line
     * @return The polygon that represents the line
     */
	public static algoanim.primitives.Polygon drawHorizontalLie(Language lang, Coordinates startPoint, int length) {
		return getPolygon(lang,startPoint, length, false);
	}

    /**
     * Returns a polygon object with the given parameters
     *
     * @param lang language object to draw on
     * @param startPoint starting point of the polygon
     * @param length length of the polygon
     * @param vertical is vertical
     * @return The polygon with the given attributes
     */
	private static algoanim.primitives.Polygon getPolygon(Language lang, Coordinates startPoint, int length, boolean vertical) {

		Node[] nodes = new Node[2];
		nodes[0] = startPoint;
		if (vertical) {
			nodes[1] = new Coordinates(startPoint.getX(), startPoint.getY() + length);
		} else {
			nodes[1] = new Coordinates(startPoint.getX() + length, startPoint.getY());
		}

		algoanim.primitives.Polygon line = null;

		try {
			line = lang.newPolygon(nodes, "line", null);
		} catch (NotEnoughNodesException e) {
			e.printStackTrace();
		}
		return line;
	}
	
	/**
	 * Moves the given point by x and y in the 2-dimensional space
     *
	 * @param point the point to be moved
	 * @param moveX the amount of movement in the x-direction
	 * @param moveY the amount of movement in the y-direction
	 * @return moved coordinate
	 */
	public static Coordinates moveCoordinate(Coordinates point, int moveX, int moveY) {
		return new Coordinates(point.getX() + moveX, point.getY() + moveY);
	}

}
