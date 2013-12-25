package graph.uml.intern.tools;

import graph.items.capability.ConnectionFactory;
import graph.uml.Connection;
import graph.uml.intern.DefaultBox;

/**
 * A factory dedicated to create new connections between {@link DefaultBox}es.
 * @author Benjamin Sigg
 */
public interface UmlConnectionFactory extends ConnectionFactory{
	/**
	 * Allows this factory to modify <code>connection</code> after it has been built.
	 * @param connection a new connection
	 * @param source the source of the connection
	 * @param target the target of the connection
	 */
	public void finalize( Connection connection, DefaultBox<?> source, DefaultBox<?> target );
}
