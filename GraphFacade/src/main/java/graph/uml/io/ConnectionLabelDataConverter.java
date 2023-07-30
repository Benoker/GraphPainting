package graph.uml.io;

import graph.uml.ConnectionLabel;
import graph.uml.ConnectionLabelConfiguration;
import graph.uml.ItemKey;
import graph.uml.intern.AbstractConnection;
import graph.uml.intern.DefaultConnectionLabel;
import graph.uml.intern.DefaultUmlDiagram;
import graph.uml.intern.keys.ConnectionLabelKey;

/**
 * Converter for {@link ConnectionLabel}s.
 * @author Benjamin Sigg
 */
public class ConnectionLabelDataConverter implements DataConverter<ConnectionLabel, ConnectionLabelData, DefaultConnectionLabel>{
	@Override
	public ConnectionLabelData toData( DefaultConnectionLabel item ) {
		ConnectionLabelData data = new ConnectionLabelData( item.getKey() );
		data.setText( item.getText() );
		data.setConfigurationId( item.getConfiguration().getId() );
		data.setConnection( item.getConnection().getKey() );
		return data;
	}

	@Override
	public DefaultConnectionLabel toItem( ConnectionLabelData data, DefaultUmlDiagram diagram ) {
		AbstractConnection connection = (AbstractConnection)diagram.getItem( data.getConnection() );
		if(connection == null){
			throw new IllegalStateException( "cannot find connection of label" );
		}
		
		DefaultConnectionLabel label = new DefaultConnectionLabel( diagram, data.getKey(), connection );
		label.setText( data.getText() );
		label.setConfiguration( new ConnectionLabelConfiguration( data.getConfigurationId() ) );
		return label;
	}

	@Override
	public boolean isKey( String uniqueId ) {
		return ConnectionLabelKey.isKey( uniqueId );
	}

	@Override
	public ItemKey<ConnectionLabel> readKey( String uniqueId ) {
		return ConnectionLabelKey.readKey( uniqueId );
	}
}
