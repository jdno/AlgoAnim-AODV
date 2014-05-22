package generators.network.aodv;

import algoanim.exceptions.NotEnoughNodesException;
import algoanim.primitives.generators.Language;
import algoanim.util.Coordinates;
import algoanim.util.Node;

/**
 * This class provides some basic geometry functionality for processing GUI elements
 * @author sascha
 *
 */
public class GeometryToolBox {

	private Language lang;

	public GeometryToolBox(Language lang){
		this.lang = lang;
	}
	
	public algoanim.primitives.Polygon drawVerticalLine(
			Coordinates startPoint, int length) {
		return getPolygon(startPoint, length, true);
	}

	public algoanim.primitives.Polygon drawHorizontalLie(
			Coordinates startPoint, int length) {
		return getPolygon(startPoint, length, false);
	}

	public algoanim.primitives.Polygon getPolygon(Coordinates startPoint,
			int length, boolean vertical) {

		Node[] nodes = new Node[2];
		nodes[0] = startPoint;
		if (vertical) {
			nodes[1] = new Coordinates(startPoint.getX(), startPoint.getY()
					+ length);
		} else {
			nodes[1] = new Coordinates(startPoint.getX() + length,
					startPoint.getY());
		}

		algoanim.primitives.Polygon line = null;

		try {
			line = lang.newPolygon(nodes, "line", null);
		} catch (NotEnoughNodesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return line;
	}
	
	/**
	 * Moves the given point by x and y in the 2-dimensional space
	 * @param point
	 * 			the point to be moved
	 * @param x
	 * 			the amount of movement in the x-direction
	 * @param y
	 * 			the amount of movement in the y-direction
	 * @return
	 * 			moved coordinate
	 */
	public Coordinates moveCoordinate(Coordinates point, int moveX, int moveY) {
		return new Coordinates(point.getX() + moveX, point.getY() + moveY);
	}
	
	
}
