package graph.model.connection;

import graph.model.GraphItem;

/**
 * Describes a connection between two {@link ConnectionArray}s.
 * @author Benjamin Sigg
 */
public interface GraphConnection extends GraphItem{
	/**
	 * The {@link EndPoint} where the connection starts.
	 * @return the start, not <code>null</code>
	 */
	public EndPoint getSourceEndPoint();
	
	/**
	 * The {@link EndPoint} where the connection ends.
	 * @return the end, not <code>null</code>
	 */
	public EndPoint getTargetEndPoint();
}
