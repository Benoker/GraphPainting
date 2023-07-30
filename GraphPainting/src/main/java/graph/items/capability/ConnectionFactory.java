package graph.items.capability;

import graph.items.ConnectionFlavor;
import graph.model.connection.GraphConnection;

/**
 * Interface to be implemented by the client, configures the {@link ConnectionableCapabilityHandler}.
 * @author Benjamin Sigg
 */
public interface ConnectionFactory {
	
	/**
	 * Gets the flavor that should be used for creating the new connection. This method is only called
	 * if {@link #isEnabled()} is <code>true</code>
	 * @return the flavor of the next connection that is being created
	 */
	public ConnectionFlavor getFlavor();
	
	/**
	 * Creates a new connection for the current {@link #getFlavor() flavor}. This method is only called
	 * if {@link #isEnabled()} is <code>true</code>
	 * @return the new connection, must not be <code>null</code>
	 */
	public GraphConnection createConnection();
}
