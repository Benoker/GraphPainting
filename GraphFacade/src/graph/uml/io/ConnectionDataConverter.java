package graph.uml.io;

import graph.items.ConnectionableCapability;
import graph.model.capability.CapabilityName;
import graph.model.connection.ConnectionArray;
import graph.uml.Connection;
import graph.uml.ConnectionType;
import graph.uml.ItemKey;
import graph.uml.intern.AbstractConnection;
import graph.uml.intern.AggregationConnection;
import graph.uml.intern.AssoziationConnection;
import graph.uml.intern.CommentConnection;
import graph.uml.intern.CompositionConnection;
import graph.uml.intern.DefaultBox;
import graph.uml.intern.DefaultUmlDiagram;
import graph.uml.intern.ExtendsConnection;
import graph.uml.intern.ImplementsConnection;
import graph.uml.intern.keys.ConnectionKey;

/**
 * Converter for {@link Connection}s.
 * @author Benjamin Sigg
 */
public class ConnectionDataConverter implements DataConverter<Connection, ConnectionData, AbstractConnection> {
	@Override
	public ConnectionData toData( AbstractConnection item ) {
		if( item.getSourceItem() == null || item.getTargetItem() == null ){
			// in such cases the user is still drawing the item - do not save it.
			return null;
		}
		
		ConnectionData data = new ConnectionData( item.getKey() );
		data.setConnectionType( item.getConnectionType() );
		data.setSource( item.getSourceItem().getKey() );
		data.setTarget( item.getTargetItem().getKey() );
		return data;
	}

	@Override
	public AbstractConnection toItem( ConnectionData data, DefaultUmlDiagram diagram ) {
		AbstractConnection connection = connection( data.getConnectionType(), data.getKey(), diagram );
		
		DefaultBox<?> source = diagram.getDefaultBox( data.getSource() );
		DefaultBox<?> target = diagram.getDefaultBox( data.getTarget() );
		
		connection.setSourceItem( source );
		connection.setTargetItem( target );
		
		ConnectionableCapability sourceConnectionable = source.getCapability( CapabilityName.CONNECTABLE );
		ConnectionableCapability targetConnectionable = target.getCapability( CapabilityName.CONNECTABLE );
		
		ConnectionArray sourceArray = sourceConnectionable.getSourceArray( connection.getFlavor() );
		ConnectionArray targetArray = targetConnectionable.getTargetArray( connection.getFlavor() );
		
		sourceArray.add( connection.getSourceEndPoint() );
		targetArray.add( connection.getTargetEndPoint() );
		
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
			case ASSOZIATION:
				return new AssoziationConnection( diagram, key );
			default:
				throw new IllegalStateException( "unknown enumeration constant: " + type );
		}
	}
	
	@Override
	public boolean isKey( String uniqueId ) {
		return ConnectionKey.isKey( uniqueId );
	}
	
	@Override
	public ItemKey<Connection> readKey( String uniqueId ) {
		return ConnectionKey.readKey( uniqueId );
	}
}
