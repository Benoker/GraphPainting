package graph.uml.intern;

import java.awt.Stroke;

import graph.items.ConnectionFlavor;
import graph.items.PathedGraphConnection;
import graph.model.connection.ConnectionArray;
import graph.uml.Connection;
import graph.uml.ConnectionType;
import graph.uml.ItemKey;
import graph.uml.intern.config.DefaultUmlConfiguration;

public class AssoziationConnection extends AbstractConnection implements Connection{
	public static final ConnectionFlavor ASSOZIATION = new ConnectionFlavor( "assoziation" );
	

	public AssoziationConnection( DefaultUmlDiagram diagram, DefaultBox<?> sourceBox, ConnectionArray source, DefaultBox<?> targetBox, ConnectionArray target ) {
		super( diagram, sourceBox, source, targetBox, target, null );
	}

	public AssoziationConnection( DefaultUmlDiagram diagram ) {
		this( diagram, null );
	}

	public AssoziationConnection( DefaultUmlDiagram diagram, ItemKey<Connection> key ) {
		super( diagram, null, null, null, null, key );
	}

	@Override
	protected PathedGraphConnection createLine( DefaultUmlConfiguration configuration ) {
		return configuration.getAssoziation().buildLine();
	}
	
	@Override
	protected Stroke getSelectionStroke( DefaultUmlConfiguration configuration ) {
		return configuration.getAssoziation().getSelectionStroke();
	}

	@Override
	public ConnectionType getConnectionType() {
		return ConnectionType.ASSOZIATION;
	}

	@Override
	public ConnectionFlavor getFlavor() {
		return ASSOZIATION;
	}
}
