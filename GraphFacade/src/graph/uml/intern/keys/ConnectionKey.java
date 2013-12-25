package graph.uml.intern.keys;

import graph.uml.Connection;
import graph.uml.intern.DefaultUmlDiagram;

/**
 * A key representing a {@link Connection}.
 * @author Benjamin Sigg
 */
public class ConnectionKey extends DefaultItemKey<Connection> {
	public ConnectionKey( DefaultUmlDiagram diagram ) {
		super( diagram, "connection" );
	}
}
