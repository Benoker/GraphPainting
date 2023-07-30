package graph.uml.intern.tools;

import graph.items.ConnectionFlavor;
import graph.model.connection.GraphConnection;
import graph.uml.intern.CompositionConnection;

/**
 * A factory that creates new {@link CompositionConnection}s.
 * @author Benjamin Sigg
 */
public class CompositionConnectionFactory extends AbstractUmlConnectionFactory {
	@Override
	public ConnectionFlavor getFlavor() {
		return CompositionConnection.COMPOSITION;
	}

	@Override
	public GraphConnection createConnection() {
		return new CompositionConnection( getDiagram() );
	}
}
