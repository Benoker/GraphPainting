package graph.uml.intern;

import graph.items.ConnectionFlavor;
import graph.items.PathedGraphConnection;
import graph.model.connection.ConnectionArray;
import graph.uml.Connection;
import graph.uml.ConnectionType;
import graph.uml.ItemKey;
import graph.uml.intern.config.DefaultUmlConfiguration;

/**
 * A connection between a class that implements an interface.
 * @author Benjamin Sigg
 */
public class ImplementsConnection extends AbstractConnection implements Connection {
	public static ConnectionFlavor IMPLEMENTS = new ConnectionFlavor( "implements" );

	public ImplementsConnection( DefaultUmlDiagram diagram, DefaultBox<?> sourceBox, ConnectionArray source, DefaultBox<?> targetBox, ConnectionArray target ) {
		super( diagram, sourceBox, source, targetBox, target, null );
	}

	public ImplementsConnection( DefaultUmlDiagram diagram ) {
		this( diagram, null );
	}

	public ImplementsConnection( DefaultUmlDiagram diagram, ItemKey<Connection> key ) {
		super( diagram, null, null, null, null, key );
	}

	@Override
	protected PathedGraphConnection createLine( DefaultUmlConfiguration configuration ) {
		return configuration.getImplementation().buildLine();
	}

	@Override
	public ConnectionType getConnectionType() {
		return ConnectionType.IMPLEMENTATION;
	}

	@Override
	public ConnectionFlavor getFlavor() {
		return IMPLEMENTS;
	}
}
