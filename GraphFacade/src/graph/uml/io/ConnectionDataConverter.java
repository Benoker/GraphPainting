package graph.uml.io;

import graph.uml.Connection;
import graph.uml.ConnectionType;
import graph.uml.ItemKey;
import graph.uml.intern.AbstractConnection;
import graph.uml.intern.AggregationConnection;
import graph.uml.intern.CommentConnection;
import graph.uml.intern.CompositionConnection;
import graph.uml.intern.DefaultUmlDiagram;
import graph.uml.intern.ExtendsConnection;
import graph.uml.intern.ImplementsConnection;

/**
 * Converter for {@link Connection}s.
 * @author Benjamin Sigg
 */
public class ConnectionDataConverter implements DataConverter<Connection, ConnectionData, AbstractConnection> {
	@Override
	public ConnectionData toData( AbstractConnection item ) {
		ConnectionData data = new ConnectionData( item.getKey() );
		data.setConnectionType( item.getConnectionType() );
		data.setSource( item.getSourceItem().getKey() );
		data.setTarget( item.getTargetItem().getKey() );
		return data;
	}

	@Override
	public AbstractConnection toItem( ConnectionData data, DefaultUmlDiagram diagram ) {
		AbstractConnection connection = connection( data.getConnectionType(), data.getKey(), diagram );
		
		connection.setSourceItem( diagram.getDefaultBox( data.getSource() ) );
		connection.setTargetItem( diagram.getDefaultBox( data.getTarget() ) );
		
		return connection;
	}

	private AbstractConnection connection( ConnectionType type, ItemKey<Connection> key, DefaultUmlDiagram diagram ) {
		switch( type ){
			case AGGREGATION:
				return new AggregationConnection( diagram, key );
			case COMMENT:
				return new CommentConnection( diagram, key );
			case COMPOSITION:
				return new CompositionConnection( diagram, key );
			case IMPLEMENTATION:
				return new ImplementsConnection( diagram, key );
			case INHERITANCE:
				return new ExtendsConnection( diagram, key );
			default:
				throw new IllegalStateException( "unknown enumeration constant: " + type );
		}
	}
}
