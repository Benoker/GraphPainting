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
 * Describes an aggregation between two types
 * @author Benjamin Sigg
 */
public class AggregationConnection extends AbstractConnection implements Connection {
	public static final ConnectionFlavor AGGREGATION = new ConnectionFlavor( "aggregation" );

	public AggregationConnection( DefaultUmlDiagram diagram, DefaultBox<?> sourceBox, ConnectionArray source, DefaultBox<?> targetBox, ConnectionArray target ) {
		super( diagram, sourceBox, source, targetBox, target, null );
	}

	public AggregationConnection( DefaultUmlDiagram diagram ) {
		this( diagram, null );
	}

	public AggregationConnection( DefaultUmlDiagram diagram, ItemKey<Connection> key ) {
		super( diagram, null, null, null, null, key );
	}

	@Override
	protected PathedGraphConnection createLine( DefaultUmlConfiguration configuration ) {
		return configuration.getAggregation().buildLine();
	}
	
	@Override
	protected Stroke getSelectionStroke( DefaultUmlConfiguration configuration ) {
		return configuration.getAggregation().getSelectionStroke();
	}

	@Override
	public ConnectionType getConnectionType() {
		return ConnectionType.AGGREGATION;
	}

	@Override
	public ConnectionFlavor getFlavor() {
		return AGGREGATION;
	}
}
