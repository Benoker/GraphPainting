package graph.uml.config;

import java.awt.Stroke;

import graph.uml.Connection;

/**
 * Configuration for a {@link Connection}. Note that some combinations of properties may not work.
 * @author Benjamin Sigg
 */
public interface ConnectionConfiguration {
	/**
	 * Describes how the path of a {@link Connection} looks like.
	 * @author Benjamin Sigg
	 */
	public static enum PathShape{
		/** The path takes the most direct route from source to target. */
		DIRECT,
		/** The path consists of only vertical and horizontal parts */
		EDGED,
		/** The path makes a curve from source to target */
		CURVED
	}
	
	/**
	 * Access to the configuration of the source endpoint.
	 * @return the source endpoint
	 */
	public EndPointConfiguration source();
	
	/**
	 * Access to the configuration of the target endpoint.
	 * @return the target endpoint
	 */
	public EndPointConfiguration target();
	
	/**
	 * Sets the {@link Stroke} with which lines are drawn.
	 * @param stroke new stroke, may be <code>null</code>
	 */
	public void setStroke(Stroke stroke);
	
	/**
	 * Gets the {@link Stroke} with which lines are drawn.
	 * @return the stroke, can be <code>null</code>
	 */
	public Stroke getStroke();
	
	/**
	 * Sets the {@link Stroke} with which selected lines are underlined.
	 * @param stroke the new stroke, may be <code>null</code>
	 */
	public void setSelectionStroke( Stroke stroke );
	
	/**
	 * Gets the {@link Stroke} with which selected lines are underlined.
	 * @return the stroke, may be <code>null</code>
	 */
	public Stroke getSelectionStroke();
	
	/**
	 * The line is drawn continuously.
	 */
	public void withContinuousLine();
	
	/**
	 * The line is dotted.
	 */
	public void withDottedLine();
	
	/**
	 * Sets how the path between two points is drawn.
	 * @param shape the shape of the path, not <code>null</code>
	 */
	public void setPathShape( PathShape shape );
	
	/**
	 * Gets the shape of the path.
	 * @return the shape, not <code>null</code>
	 */
	public PathShape getPathShape();
	
}
