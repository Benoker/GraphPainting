package graph.uml.intern.tools;

import graph.items.ConnectionFlavor;
import graph.model.connection.GraphConnection;
import graph.uml.intern.AggregationConnection;
import graph.uml.intern.DefaultUmlDiagram;

/**
 * A factory that creates new {@link AggregationConnection}s.
 * @author Benjamin Sigg
 */
public class AggregationConnectionFactory extends AbstractUmlConnectionFactory{
	private DefaultUmlDiagram diagram;
	
	/**
	 * Creates a new factory.
	 * @param diagram the diagram to which new items will be added
	 */
	public AggregationConnectionFactory( DefaultUmlDiagram diagram ){
		this.diagram = diagram;
	}
	
	@Override
	public ConnectionFlavor getFlavor() {
		return AggregationConnection.AGGREGATION;
	}
	
	@Override
	public GraphConnection createConnection() {
		return new AggregationConnection( diagram );
	}
}
