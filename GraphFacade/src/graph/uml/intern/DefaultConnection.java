package graph.uml.intern;

import graph.model.connection.ConnectionArray;
import graph.ui.Graph;
import graph.uml.Connection;

/**
 * Describes a connection between two boxes.
 * @author Benjamin Sigg
 */
public abstract class DefaultConnection extends DefaultItem implements Connection{
	private ConnectionArray source;
	private ConnectionArray target;
	
	public DefaultConnection( Graph graph, ConnectionArray source, ConnectionArray target ){
		super( graph );
	}
	
	public ConnectionArray getSource() {
		return source;
	}
	
	public ConnectionArray getTarget() {
		return target;
	}
}
