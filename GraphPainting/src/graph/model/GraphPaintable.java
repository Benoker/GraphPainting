package graph.model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Describes the capability of painting on the screen.
 * @author Benjamin Sigg
 */
public interface GraphPaintable {
	/**
	 * Paints this item onto the screen.
	 * @param g the graphics context to use
	 */
	public void paint( Graphics2D g );
	
	/***
	 * Paints this item onto the overlay.
	 * @param g the graphics context to use
	 */
	public void paintOverlay( Graphics2D g );
	
	/**
	 * Gets the boundaries of this paintable element, the boundaries are required to calculate
	 * the size of an image into which all the items fit.<br>
	 * This method is optional, a result of <code>null</code> is allowed.
	 * @param g the graphics context in which the boundaries are required
	 * @return the visible boundaries, or <code>null</code>
	 */
	public Rectangle getVisibleBoundaries( Graphics g );
}
