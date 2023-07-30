package graph.uml;

import java.util.List;

/**
 * A connection is a line drawn between two {@link Box}es.
 * @author Benjamin Sigg
 */
public interface Connection extends Item{
	@Override
	public ItemKey<Connection> getKey();
	
	/**
	 * Tells what kind of {@link Connection} this is.
	 * @return the kind of connection, not <code>null</code>
	 */
	public ConnectionType getConnectionType();
	
	/**
	 * Creates and adds a new {@link ConnectionLabel} to this item. Clients can remove
	 * the label using {@link ConnectionLabel#dispose()}
	 * @param configuration position, angle and other properties of the new label. While <code>null</code> is not allowed,
	 * clients can use {@link ConnectionLabelConfiguration#NONE} if they are not yet certain about the properties of the connection
	 * @return the new label
	 */
	public ConnectionLabel addLabel(ConnectionLabelConfiguration configuration);
	
	/**
	 * Gets all the {@link ConnectionLabel}s that were added to this item. The returned
	 * list is not editable. The labels not ordered.
	 * @return all the labels that are currently attached to this connection
	 */
	public List<ConnectionLabel> getLabels();
}
