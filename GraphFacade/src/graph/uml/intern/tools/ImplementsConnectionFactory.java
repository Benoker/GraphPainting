package graph.uml.intern.tools;

import graph.items.ConnectionFlavor;
import graph.model.connection.GraphConnection;
import graph.uml.intern.ImplementsConnection;

/**
 * A factory that creates new {@link ImplementsConnection}s.
 * @author Benjamin Sigg
 */
public class ImplementsConnectionFactory extends AbstractUmlConnectionFactory {
	@Override
	public ConnectionFlavor getFlavor() {
		return ImplementsConnection.IMPLEMENTS;
	}

	@Override
	public GraphConnection createConnection() {
		return new ImplementsConnection( getDiagram() );
	}
}
