package graph.uml.intern.keys;

import graph.uml.ConnectionLabel;
import graph.uml.intern.DefaultUmlDiagram;

/**
 * A key representing a {@link ConnectionLabel}.
 * @author Benjamin Sigg
 */
public class ConnectionLabelKey extends DefaultItemKey<ConnectionLabel> {
	private static final String LABEL = "connectionLabel";

	public static boolean isKey( String uniqueString ) {
		return isKey( uniqueString, LABEL );
	}

	public static ConnectionLabelKey readKey( String uniqueString ) {
		return new ConnectionLabelKey( uniqueString );
	}

	public ConnectionLabelKey( DefaultUmlDiagram diagram ) {
		super( diagram, LABEL );
	}

	private ConnectionLabelKey( String uniqueString ) {
		super( uniqueString, LABEL );
	}
}
