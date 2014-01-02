package graph.uml.intern;

import graph.items.ConnectionFlavor;
import graph.items.PathedGraphConnection;
import graph.model.connection.ConnectionArray;
import graph.uml.Connection;
import graph.uml.ConnectionType;
import graph.uml.ItemKey;
import graph.uml.intern.config.DefaultUmlConfiguration;

/**
 * A connection describing inheritance.
 * @author Benjamin Sigg
 */
public class ExtendsConnection extends AbstractConnection implements Connection {
	public static ConnectionFlavor EXTENDS = new ConnectionFlavor( "extends" );

	public ExtendsConnection( DefaultUmlDiagram diagram, DefaultBox<?> sourceBox, ConnectionArray source, DefaultBox<?> targetBox, ConnectionArray target ) {
		super( diagram, sourceBox, source, targetBox, target, null );
	}

	public ExtendsConnection( DefaultUmlDiagram diagram ) {
		this( diagram, null );
	}

	public ExtendsConnection( DefaultUmlDiagram diagram, ItemKey<Connection> key ) {
		super( diagram, null, null, null, null, key );
	}

	@Override
	protected PathedGraphConnection createLine( DefaultUmlConfiguration configuration ) {
		return configuration.getInheritance().buildLine();
	}

	@Override
	public ConnectionType getConnectionType() {
		return ConnectionType.INHERITANCE;
	}

	@Override
	public ConnectionFlavor getFlavor() {
		return EXTENDS;
	}
}
