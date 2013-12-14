package graph.items;

import java.awt.geom.Path2D;

import graph.model.connection.GraphConnection;

/**
 * A {@link PathedGraphConnection} is a connection whose shape can be described by a {@link Path2D}.
 * @author Benjamin Sigg
 */
public interface PathedGraphConnection extends GraphConnection{
	/**
	 * Gets the path that represents this connection. The resulting path must be closed, 
	 * otherwise the intersection algorithm automatically adds an additional line to 
	 * close the path.
	 * @return the shape of this connection, can be <code>null</code>
	 */
	public Path2D getClosedConnectionPath();
}
