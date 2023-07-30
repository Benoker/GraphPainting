package graph.uml.io;

import graph.uml.Box;
import graph.uml.CommentBox;
import graph.uml.Connection;
import graph.uml.ConnectionLabel;
import graph.uml.ConnectionType;
import graph.uml.ItemKey;
import graph.uml.TypeBox;
import graph.uml.xml.XElement;
import graph.uml.xml.XIO;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Writes {@link UmlDiagramData} into a xml file.
 * @author Benjamin Sigg
 */
public class XmlFormat implements Format {

	@Override
	public UmlDiagramData read( InputStream in, UmlDiagramConverter converter ) throws IOException {
		XElement xdiagram = XIO.readUTF( in );
		return readXml( xdiagram, converter );
	}
	
	public UmlDiagramData readXml( XElement xdiagram, UmlDiagramConverter converter ){
		UmlDiagramData diagram = new UmlDiagramData();
		diagram.setNextUnqiueId( xdiagram.getElement( "next-unique-id" ).getInt() );

		XElement xitems = xdiagram.getElement( "items" );
		for( XElement xdata : xitems ) {
			switch( xdata.getName() ){
				case "connection":
					diagram.addData( readConnection( xdata, converter ) );
					break;
				case "connectionLabel":
					diagram.addData( readConnectionLabel( xdata, converter ) );
					break;
				case "comment":
					diagram.addData( readComment( xdata, converter ) );
					break;
				case "type":
					diagram.addData( readType( xdata, converter ) );
					break;
			}
		}
		return diagram;
	}

	private ConnectionData readConnection( XElement xconnection, UmlDiagramConverter converter ) {
		ItemKey<Connection> key = converter.readKey( xconnection.getString( "key" ), Connection.class );
		ConnectionData connection = new ConnectionData( key );
		connection.setConnectionType( ConnectionType.byPersistentName( xconnection.getString( "type" ) ) );
		connection.setSource( converter.readBoundedKey( xconnection.getElement( "source" ).getString(), Box.class ) );
		connection.setTarget( converter.readBoundedKey( xconnection.getElement( "target" ).getString(), Box.class ) );
		return connection;
	}

	private ConnectionLabelData readConnectionLabel( XElement xlabel, UmlDiagramConverter converter ) {
		ItemKey<ConnectionLabel> key = converter.readKey( xlabel.getString( "key" ), ConnectionLabel.class );
		ConnectionLabelData label = new ConnectionLabelData( key );
		label.setText( xlabel.getElement( "text" ).getString() );
		label.setConfigurationId( xlabel.getElement( "configuration" ).getString() );
		label.setConnection( converter.readKey( xlabel.getElement( "connection" ).getString(), Connection.class ) );
		return label;
	}

	private CommentData readComment( XElement xcomment, UmlDiagramConverter converter ) {
		ItemKey<CommentBox> key = converter.readKey( xcomment.getString( "key" ), CommentBox.class );
		CommentData data = new CommentData( key );
		readBounds( data, xcomment );
		data.setText( xcomment.getElement( "text" ).getString() );
		return data;
	}

	private TypeData readType( XElement xtype, UmlDiagramConverter converter ) {
		ItemKey<TypeBox> key = converter.readKey( xtype.getString( "key" ), TypeBox.class );
		TypeData data = new TypeData( key );
		readBounds( data, xtype );
		data.setText( xtype.getElement( "text" ).getString() );
		return data;
	}

	private void readBounds( BoxData<?> data, XElement xparent ) {
		XElement xbounds = xparent.getElement( "bounds" );
		data.setX( xbounds.getElement( "x" ).getInt() );
		data.setY( xbounds.getElement( "y" ).getInt() );
		data.setWidth( xbounds.getElement( "width" ).getInt() );
		data.setHeight( xbounds.getElement( "height" ).getInt() );
	}

	@Override
	public void write( UmlDiagramData data, OutputStream out ) throws IOException {
		XIO.writeUTF( writeXml( data ), out );
	}
	
	public XElement writeXml( UmlDiagramData data ){
		WriteVisitor visitor = new WriteVisitor();
		data.visit( visitor );
		return visitor.getXdiagram();
	}

	private static class WriteVisitor implements DataVisitor {
		private XElement xdiagram;
		private XElement xitems;

		public WriteVisitor() {
			xdiagram = new XElement( "diagram" );
			xitems = xdiagram.addElement( "items" );
		}

		public XElement getXdiagram() {
			return xdiagram;
		}

		@Override
		public void visit( UmlDiagramData diagram ) {
			xdiagram.addElement( "next-unique-id" ).setInt( diagram.getNextUnqiueId() );
		}

		@Override
		public void visit( ConnectionData connection ) {
			XElement xconnection = xitems.addElement( "connection" );
			addKey( connection, xconnection );
			xconnection.addString( "type", connection.getConnectionType().getPersistentName() );
			xconnection.addElement( "source" ).setString( connection.getSource().toUniqueString() );
			xconnection.addElement( "target" ).setString( connection.getTarget().toUniqueString() );
		}

		@Override
		public void visit( ConnectionLabelData label ) {
			XElement xlabel = xitems.addElement( "connectionLabel" );
			addKey( label, xlabel );
			xlabel.addElement( "text" ).setString( label.getText() );
			xlabel.addElement( "configuration" ).setString( label.getConfigurationId() );
			xlabel.addElement( "connection" ).setString( label.getConnection().toUniqueString() );
		}

		@Override
		public void visit( CommentData comment ) {
			XElement xcomment = xitems.addElement( "comment" );
			addKey( comment, xcomment );
			addBoundaries( comment, xcomment );
			xcomment.addElement( "text" ).setString( comment.getText() );
		}

		@Override
		public void visit( TypeData type ) {
			XElement xtype = xitems.addElement( "type" );
			addKey( type, xtype );
			addBoundaries( type, xtype );
			xtype.addElement( "text" ).setString( type.getText() );
		}

		private void addKey( Data<?> data, XElement xparent ) {
			xparent.addString( "key", data.getKey().toUniqueString() );
		}

		private void addBoundaries( BoxData<?> box, XElement xparent ) {
			XElement xbounds = xparent.addElement( "bounds" );
			xbounds.addElement( "x" ).setInt( box.getX() );
			xbounds.addElement( "y" ).setInt( box.getY() );
			xbounds.addElement( "width" ).setInt( box.getWidth() );
			xbounds.addElement( "height" ).setInt( box.getHeight() );
		}
	}
}
