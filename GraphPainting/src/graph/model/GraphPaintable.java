package graph.model;

import java.awt.Graphics2D;

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
}
