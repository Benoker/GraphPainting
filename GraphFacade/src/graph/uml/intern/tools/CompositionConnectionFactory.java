package graph.uml.intern.tools;

import graph.items.ConnectionFlavor;
import graph.model.connection.GraphConnection;
import graph.ui.Graph;
import graph.uml.intern.CompositionConnection;

/**
 * A factory that creates new {@link CompositionConnection}s.
 * @author Benjamin Sigg
 */
public class CompositionConnectionFactory extends AbstractUmlConnectionFactory{
	private Graph graph;
	
	/**
	 * Creates a new factory
	 * @param graph the graph to which new items will be added
	 */
	public CompositionConnectionFactory( Graph graph ){
		this.graph = graph;
	}
	
	@Override
	public ConnectionFlavor getFlavor() {
		return CompositionConnection.COMPOSITION;
	}
	
	@Override
	public GraphConnection createConnection() {
		return new CompositionConnection( graph );
	}
}
