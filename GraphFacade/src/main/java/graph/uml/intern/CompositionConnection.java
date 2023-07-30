package graph.uml.intern;

import java.awt.Stroke;

import graph.items.ConnectionFlavor;
import graph.items.PathedGraphConnection;
import graph.model.connection.ConnectionArray;
import graph.uml.Connection;
import graph.uml.ConnectionType;
import graph.uml.ItemKey;
import graph.uml.intern.config.DefaultUmlConfiguration;

/**
 * Describes a composition between two types
 * @author Benjamin Sigg
 */
public class CompositionConnection extends AbstractConnection implements Connection {
	public static final ConnectionFlavor COMPOSITION = new ConnectionFlavor( "composition" );

	public CompositionConnection( DefaultUmlDiagram diagram, DefaultBox<?> sourceBox, ConnectionArray source, DefaultBox<?> targetBox, ConnectionArray target ) {
		super( diagram, sourceBox, source, targetBox, target, null );
	}

	public CompositionConnection( DefaultUmlDiagram diagram ) {
		this( diagram, null );
	}

	public CompositionConnection( DefaultUmlDiagram diagram, ItemKey<Connection> key ) {
		super( diagram, null, null, null, null, key );
	}

	@Override
	protected PathedGraphConnection createLine( DefaultUmlConfiguration configuration ) {
		return configuration.getComposition().buildLine();
	}
	
	@Override
	protected Stroke getSelectionStroke( DefaultUmlConfiguration configuration ) {
		return configuration.getComposition().getSelectionStroke();
	}

	@Override
	public ConnectionType getConnectionType() {
		return ConnectionType.COMPOSITION;
	}

	@Override
	public ConnectionFlavor getFlavor() {
		return COMPOSITION;
	}
}
