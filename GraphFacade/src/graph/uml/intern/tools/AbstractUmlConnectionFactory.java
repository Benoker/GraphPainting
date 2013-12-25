package graph.uml.intern.tools;

import graph.uml.Connection;
import graph.uml.intern.AbstractConnection;
import graph.uml.intern.DefaultBox;
import graph.uml.intern.DefaultUmlDiagram;

/**
 * This factory creates new {@link AbstractConnection}s and connects them to the
 * {@link DefaultBox}es once they are shown on the graph.
 * @author Benjamin Sigg
 */
public abstract class AbstractUmlConnectionFactory implements UmlConnectionFactory {
	private DefaultUmlDiagram diagram;

	@Override
	public void finalize( Connection connection, DefaultBox<?> source, DefaultBox<?> target ) {
		AbstractConnection con = (AbstractConnection) connection;
		con.setSourceItem( source );
		con.setTargetItem( target );
	}

	public DefaultUmlDiagram getDiagram() {
		return diagram;
	}

	public void setDiagram( DefaultUmlDiagram diagram ) {
		this.diagram = diagram;
	}
}
