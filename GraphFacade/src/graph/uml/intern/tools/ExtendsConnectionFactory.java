package graph.uml.intern.tools;

import graph.items.ConnectionFlavor;
import graph.model.connection.GraphConnection;
import graph.ui.Graph;
import graph.uml.intern.ExtendsConnection;

/**
 * A factory which will create new {@link ExtendsConnection}s.
 * @author Benjamin Sigg
 */
public class ExtendsConnectionFactory extends AbstractUmlConnectionFactory{
	private Graph graph;
	
	/**
	 * Creates a new factory.
	 * @param graph the graph to which new items will be added
	 */
	public ExtendsConnectionFactory( Graph graph ){
		this.graph = graph;
	}
	
	@Override
	public ConnectionFlavor getFlavor() {
		return ExtendsConnection.EXTENDS;
	}
	
	@Override
	public GraphConnection createConnection() {
		return new ExtendsConnection( graph );
	}
}
