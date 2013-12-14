package graph.uml.intern.tools;

import graph.items.ConnectionFlavor;
import graph.model.connection.GraphConnection;
import graph.uml.intern.DefaultUmlDiagram;
import graph.uml.intern.ExtendsConnection;

/**
 * A factory which will create new {@link ExtendsConnection}s.
 * @author Benjamin Sigg
 */
public class ExtendsConnectionFactory extends AbstractUmlConnectionFactory{
	private DefaultUmlDiagram diagram;
	
	/**
	 * Creates a new factory.
	 * @param diagram the diagram to which new items will be added
	 */
	public ExtendsConnectionFactory( DefaultUmlDiagram diagram ){
		this.diagram = diagram;
	}
	
	@Override
	public ConnectionFlavor getFlavor() {
		return ExtendsConnection.EXTENDS;
	}
	
	@Override
	public GraphConnection createConnection() {
		return new ExtendsConnection( diagram );
	}
}
