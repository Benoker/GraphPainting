package graph.uml.intern.tools;

import graph.items.ConnectionFlavor;
import graph.model.connection.GraphConnection;
import graph.uml.intern.CompositionConnection;
import graph.uml.intern.DefaultUmlDiagram;

/**
 * A factory that creates new {@link CompositionConnection}s.
 * @author Benjamin Sigg
 */
public class CompositionConnectionFactory extends AbstractUmlConnectionFactory{
	private DefaultUmlDiagram diagram;
	
	/**
	 * Creates a new factory
	 * @param diagram the diagram to which new items will be added
	 */
	public CompositionConnectionFactory( DefaultUmlDiagram diagram ){
		this.diagram = diagram;
	}
	
	@Override
	public ConnectionFlavor getFlavor() {
		return CompositionConnection.COMPOSITION;
	}
	
	@Override
	public GraphConnection createConnection() {
		return new CompositionConnection( diagram );
	}
}
