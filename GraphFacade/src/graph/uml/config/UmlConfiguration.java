package graph.uml.config;

import graph.uml.Connection;
import graph.uml.ConnectionType;

/**
 * {@link UmlConfiguration} allows clients to configure some details of the diagram. The API of this
 * interface is deliberately limit such that clients are unable to create configurations that do not work.
 * @author Benjamin Sigg
 */
public interface UmlConfiguration {
	/**
	 * Allows access to the configuration of {@link Connection}s of type {@link ConnectionType#IMPLEMENTATION}.
	 * @return the configuration
	 */
	public ConnectionConfiguration getImplementation();

	/**
	 * Allows access to the configuration of {@link Connection}s of type {@link ConnectionType#INHERITANCE}.
	 * @return the configuration
	 */
	public ConnectionConfiguration getInheritance();

	/**
	 * Allows access to the configuration of {@link Connection}s of type {@link ConnectionType#COMPOSITION}.
	 * @return the configuration
	 */
	public ConnectionConfiguration getComposition();

	/**
	 * Allows access to the configuration of {@link Connection}s of type {@link ConnectionType#AGGREGATION}.
	 * @return the configuration
	 */
	public ConnectionConfiguration getAggregation();

	/**
	 * Allows access to the configuration of {@link Connection}s of type {@link ConnectionType#COMMENT}.
	 * @return the configuration
	 */
	public ConnectionConfiguration getComment();
}
