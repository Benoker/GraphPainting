package graph.uml.io;

import graph.uml.Box;
import graph.uml.Connection;
import graph.uml.ConnectionType;
import graph.uml.ItemKey;

/**
 * Data describing a {@link Connection}.
 * @author Benjamin Sigg
 */
public class ConnectionData extends Data<Connection> {
	private ConnectionType connectionType;
	private ItemKey<? extends Box> source;
	private ItemKey<? extends Box> target;

	public ConnectionData( ItemKey<Connection> key ) {
		super( key );
	}

	public ItemKey<? extends Box> getSource() {
		return source;
	}

	public void setSource( ItemKey<? extends Box> source ) {
		this.source = source;
	}

	public ItemKey<? extends Box> getTarget() {
		return target;
	}

	public void setTarget( ItemKey<? extends Box> target ) {
		this.target = target;
	}

	public ConnectionType getConnectionType() {
		return connectionType;
	}

	public void setConnectionType( ConnectionType connectionType ) {
		this.connectionType = connectionType;
	}

	@Override
	public void validate() {
		super.validate();
		if( connectionType == null ) {
			throw new IllegalStateException( "connectionType must not be null" );
		}
		if( source == null ) {
			throw new IllegalStateException( "source must not be null" );
		}
		if( target == null ) {
			throw new IllegalStateException( "target must not be null" );
		}
	}
	
	@Override
	public void visit( DataVisitor visitor ) {
		visitor.visit( this );	
	}
}
