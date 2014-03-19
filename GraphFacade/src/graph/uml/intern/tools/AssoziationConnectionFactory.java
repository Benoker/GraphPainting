package graph.uml.intern.tools;

import graph.items.ConnectionFlavor;
import graph.model.connection.GraphConnection;
import graph.uml.intern.AssoziationConnection;

/**
 * A factory that creates new {@link AssoziationConnection}s.
 * @author Benjamin Sigg
 */
public class AssoziationConnectionFactory extends AbstractUmlConnectionFactory{
	@Override
	public ConnectionFlavor getFlavor() {
		return AssoziationConnection.ASSOZIATION;
	}
	
	@Override
	public GraphConnection createConnection() {
		return new AssoziationConnection( getDiagram() );
	}
}
