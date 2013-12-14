package graph.uml.intern.tools;

import graph.items.ConnectionFlavor;
import graph.model.connection.GraphConnection;
import graph.ui.Graph;
import graph.uml.intern.ImplementsConnection;

/**
 * A factory that creates new {@link ImplementsConnection}s.
 * @author Benjamin Sigg
 */
public class ImplementsConnectionFactory extends AbstractUmlConnectionFactory{
	private Graph graph;
	
	/**
	 * Creates a new factory.
	 * @param graph the graph to which new items will be added
	 */
	public ImplementsConnectionFactory( Graph graph ){
		this.graph = graph;
	}
	
	@Override
	public ConnectionFlavor getFlavor() {
		return ImplementsConnection.IMPLEMENTS;
	}
	
	@Override
	public GraphConnection createConnection() {
		return new ImplementsConnection( graph );
	}
}
