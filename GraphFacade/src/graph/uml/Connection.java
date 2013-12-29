package graph.uml;

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
	
	
}
