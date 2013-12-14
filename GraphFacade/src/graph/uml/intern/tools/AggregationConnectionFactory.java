package graph.uml.intern.tools;

import graph.items.ConnectionFlavor;
import graph.model.connection.GraphConnection;
import graph.ui.Graph;
import graph.uml.intern.AggregationConnection;

/**
 * A factory that creates new {@link AggregationConnection}s.
 * @author Benjamin Sigg
 */
public class AggregationConnectionFactory extends AbstractUmlConnectionFactory{
	private Graph graph;
	
	/**
	 * Creates a new factory.
	 * @param graph the graph to which new items will be added
	 */
	public AggregationConnectionFactory( Graph graph ){
		this.graph = graph;
	}
	
	@Override
	public ConnectionFlavor getFlavor() {
		return AggregationConnection.AGGREGATION;
	}
	
	@Override
	public GraphConnection createConnection() {
		return new AggregationConnection( graph );
	}
}
