package graph.uml.intern.keys;

import graph.uml.Connection;
import graph.uml.intern.DefaultUmlDiagram;

/**
 * A key representing a {@link Connection}.
 * @author Benjamin Sigg
 */
public class ConnectionKey extends DefaultItemKey<Connection> {
	private static final String CONNECTION = "connection";
	
	public static boolean isKey( String uniqueString ){
		return isKey( uniqueString, CONNECTION );
	}
	
	public static ConnectionKey readKey( String uniqueString ){
		return new ConnectionKey( uniqueString );
	}
	
	public ConnectionKey( DefaultUmlDiagram diagram ) {
		super( diagram, CONNECTION );
	}
	
	private ConnectionKey( String uniqueString ){
		super( uniqueString, CONNECTION );
	}
}
