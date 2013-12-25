package graph.uml.intern.tools;

import graph.items.ConnectionFlavor;
import graph.model.connection.GraphConnection;
import graph.uml.intern.ExtendsConnection;

/**
 * A factory which will create new {@link ExtendsConnection}s.
 * @author Benjamin Sigg
 */
public class ExtendsConnectionFactory extends AbstractUmlConnectionFactory {
	@Override
	public ConnectionFlavor getFlavor() {
		return ExtendsConnection.EXTENDS;
	}

	@Override
	public GraphConnection createConnection() {
		return new ExtendsConnection( getDiagram() );
	}
}
