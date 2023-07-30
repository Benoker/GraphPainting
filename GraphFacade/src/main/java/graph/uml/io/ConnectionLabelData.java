package graph.uml.io;

import graph.uml.Connection;
import graph.uml.ConnectionLabel;
import graph.uml.ItemKey;

/**
 * Data describing a {@link ConnectionLabel}
 * @author Benjamin Sigg
 */
public class ConnectionLabelData extends Data<ConnectionLabel> {
	private String text;
	private String configurationId;
	private ItemKey<Connection> connection;

	public ConnectionLabelData( ItemKey<ConnectionLabel> key ) {
		super( key );
	}

	public String getText() {
		return text;
	}

	public void setText( String text ) {
		this.text = text;
	}

	public String getConfigurationId() {
		return configurationId;
	}

	public void setConfigurationId( String configurationId ) {
		this.configurationId = configurationId;
	}

	public ItemKey<Connection> getConnection() {
		return connection;
	}

	public void setConnection( ItemKey<Connection> connection ) {
		this.connection = connection;
	}

	@Override
	public void validate() {
		super.validate();
		if( connection == null ) {
			throw new IllegalStateException( "connection must not be null" );
		}
		if( configurationId == null ) {
			throw new IllegalStateException( "configurationId must not be null" );
		}
	}
	
	@Override
	public void visit( DataVisitor visitor ) {
		visitor.visit( this );	
	}
}
