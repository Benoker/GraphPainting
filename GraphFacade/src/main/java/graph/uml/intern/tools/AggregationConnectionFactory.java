package graph.uml.intern.tools;

import graph.items.ConnectionFlavor;
import graph.model.connection.GraphConnection;
import graph.uml.intern.AggregationConnection;

/**
 * A factory that creates new {@link AggregationConnection}s.
 * @author Benjamin Sigg
 */
public class AggregationConnectionFactory extends AbstractUmlConnectionFactory {
	@Override
	public ConnectionFlavor getFlavor() {
		return AggregationConnection.AGGREGATION;
	}

	@Override
	public GraphConnection createConnection() {
		return new AggregationConnection( getDiagram() );
	}
}
