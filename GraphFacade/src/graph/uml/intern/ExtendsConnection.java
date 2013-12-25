package graph.uml.intern;

import graph.items.ConnectionFlavor;
import graph.items.PathedGraphConnection;
import graph.items.connection.CuttingEdgeLineConnection;
import graph.items.uml.FilledArrow;
import graph.model.GraphSite;
import graph.model.connection.ConnectionArray;
import graph.uml.Connection;
import graph.uml.ConnectionType;
import graph.uml.ItemKey;

/**
 * A connection describing inheritance.
 * @author Benjamin Sigg
 */
public class ExtendsConnection extends AbstractConnection implements Connection {
	public static ConnectionFlavor EXTENDS = new ConnectionFlavor( "extends" );
	private CuttingEdgeLineConnection line;

	public ExtendsConnection( DefaultUmlDiagram diagram, DefaultBox<?> sourceBox, ConnectionArray source, DefaultBox<?> targetBox, ConnectionArray target ) {
		super( diagram, sourceBox, targetBox, null );
		initLine();
		source.add( line.getSourceEndPoint() );
		target.add( line.getTargetEndPoint() );
	}

	public ExtendsConnection( DefaultUmlDiagram diagram ) {
		this( diagram, null );
	}

	public ExtendsConnection( DefaultUmlDiagram diagram, ItemKey<Connection> key ) {
		super( diagram, null, null, key );
		initLine();
	}

	private void initLine() {
		line = new CuttingEdgeLineConnection();
		addChild( line );

		FilledArrow arrow = new FilledArrow( line.getTargetEndPoint() );
		addChild( arrow );
	}

	@Override
	protected void removeFrom( GraphSite site ) {
		// ignore
	}

	@Override
	protected void addTo( GraphSite site ) {
		// ignore
	}

	@Override
	public PathedGraphConnection getGraphConnection() {
		return line;
	}

	@Override
	public ConnectionType getConnectionType() {
		return ConnectionType.INHERITANCE;
	}

	@Override
	public ConnectionFlavor getFlavor() {
		return EXTENDS;
	}
}
