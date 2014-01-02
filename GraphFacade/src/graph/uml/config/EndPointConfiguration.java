package graph.uml.config;

import graph.uml.Box;
import graph.uml.Connection;

/**
 * Configuration for one of the endpoints of a {@link Connection}. Note that some combinations of properties may
 * not work.
 * @author Benjamin Sigg
 */
public interface EndPointConfiguration {
	/**
	 * Decoration of an endpoint.
	 * @author Benjamin Sigg
	 */
	public static enum Decoration {
		/** There is no decoration (the endpoint is not visible) */
		NONE,
		/** The decoration is an open arrow */
		OPEN_ARROW,
		/** The decoration is a closed, white arrow */
		CLOSE_ARROW,
		/** The decoration is a closed, black arrow */
		FILLED_ARROW,
		/** The decoration is a closed, white diamond */
		DIAMOND,
		/** The decoration is a closed, black diamond */
		FILLED_DIAMOND;
	}
	
	/**
	 * Tells in which direction a {@link Connection} starts.
	 * @author Benjamin Sigg
	 */
	public static enum Direction{
		/** The path starts going directly to its target */
		DIRECT,
		/** The path starts being orthogonal to the surface of the {@link Box} to which the {@link Connection} is attached */
		ORTHOGONAL
	}

	/**
	 * Sets how to paint the endpoint.
	 * @param decoration the decoration, not <code>null</code>
	 */
	public void setDecoration( Decoration decoration );

	/**
	 * Tells how the endpoint is painted.
	 * @return the decoration, not <code>null</code>
	 */
	public Decoration getDecoration();

	/**
	 * Sets in which direction the path of the {@link Connection} starts.
	 * @param direction the direction, not <code>null</code>
	 */
	public void setDirection( Direction direction );
	
	/**
	 * Gets the {@link Direction} in which the path starts.
	 * @return the direction, not <code>null</code>
	 */
	public Direction getDirection();
}
